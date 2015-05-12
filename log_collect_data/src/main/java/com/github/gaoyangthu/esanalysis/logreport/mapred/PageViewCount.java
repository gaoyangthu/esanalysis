package com.github.gaoyangthu.esanalysis.logreport.mapred;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


import com.github.gaoyangthu.esanalysis.bdb.dao.BDBUserChannelTagReadDao;
import com.github.gaoyangthu.esanalysis.bdb.dao.BDBUserIndexReadDao;
import com.github.gaoyangthu.esanalysis.bdb.bean.BDBUserChannelTag;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gaoyangthu.esanalysis.bdb.dao.BDBUserChannelTagDao;
import com.github.gaoyangthu.esanalysis.bdb.dao.BDBUserIndexDao;

/**
 * Created by IntelliJ IDEA
 * Project: esanalysis
 * Author: GaoYang
 * Date: 2014/4/8 0008
 */
public class PageViewCount extends Configured implements Tool {
	/**
	 * The constant logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PageViewCount.class);

	/**
	 * The HDFS path of berkeley DB files
	 */
	private static final String HDFS_PATH = "hdfs://bdcluster/esanalysis/bdb/";

	/**
	 * Local cache file paths of distributed cache files
	 */
	//private static final String LOCAL_PATH = "/tmp/bdb/";


	/**
	 * Mapper output Object, including:
	 * bigdata user uuid
	 * user channel tag (id)
	 * first channel visit
	 */
	static class AccessRecord implements WritableComparable<AccessRecord> {
		String bdUserUuid;
		long date;
		int userChannelTag;
		long firstChannelVisit;

		public AccessRecord() {
		}

		public AccessRecord(String bdUserUuid, long date,
		                    int userChannelTag, long firstChannelVisit) {
			this.bdUserUuid = bdUserUuid;
			this.date = date;
			this.userChannelTag = userChannelTag;
			this.firstChannelVisit = firstChannelVisit;
		}

		/**
		 * Serialize the fields of this object to <code>out</code>.
		 *
		 * @param out <code>DataOuput</code> to serialize this object into.
		 * @throws java.io.IOException
		 */
		@Override
		public void write(DataOutput out) throws IOException {
			Text.writeString(out, bdUserUuid);
			out.writeLong(date);
			out.writeInt(userChannelTag);
			out.writeLong(firstChannelVisit);
		}

		/**
		 * Deserialize the fields of this object from <code>in</code>.
		 * <p/>
		 * <p>For efficiency, implementations should attempt to re-use storage in the
		 * existing object where possible.</p>
		 *
		 * @param in <code>DataInput</code> to deseriablize this object from.
		 * @throws java.io.IOException
		 */
		@Override
		public void readFields(DataInput in) throws IOException {
			this.bdUserUuid = Text.readString(in);
			this.date = in.readLong();
			this.userChannelTag = in.readInt();
			this.firstChannelVisit = in.readLong();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(bdUserUuid);
			sb.append(",");
			sb.append(date);
			sb.append(",");
			sb.append(userChannelTag);
			sb.append(",");
			sb.append(firstChannelVisit);
			return sb.toString();
		}

		/**
		 * Compares this object with the specified object for order.  Returns a
		 * negative integer, zero, or a positive integer as this object is less
		 * than, equal to, or greater than the specified object.
		 * <p/>
		 * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
		 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
		 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
		 * <tt>y.compareTo(x)</tt> throws an exception.)
		 * <p/>
		 * <p>The implementor must also ensure that the relation is transitive:
		 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
		 * <tt>x.compareTo(z)&gt;0</tt>.
		 * <p/>
		 * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
		 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
		 * all <tt>z</tt>.
		 * <p/>
		 * <p>It is strongly recommended, but <i>not</i> strictly required that
		 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
		 * class that implements the <tt>Comparable</tt> interface and violates
		 * this condition should clearly indicate this fact.  The recommended
		 * language is "Note: this class has a natural ordering that is
		 * inconsistent with equals."
		 * <p/>
		 * <p>In the foregoing description, the notation
		 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
		 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
		 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
		 * <i>expression</i> is negative, zero or positive.
		 *
		 * @param o the object to be compared.
		 * @return a negative integer, zero, or a positive integer as this object
		 * is less than, equal to, or greater than the specified object.
		 * @throws NullPointerException if the specified object is null
		 * @throws ClassCastException   if the specified object's type prevents it
		 *                              from being compared to this object.
		 */
		@Override
		public int compareTo(AccessRecord o) {
			return WritableComparator.compareBytes(this.toString().getBytes(),
					0, this.toString().length(), o.toString().getBytes(), 0,
					o.toString().length());
		}
	}

	/**
	 * Reducer output Object, including:
	 * bigdata user uuid
	 * user channel tag (id)
	 * first channel visit
	 * page view
	 */
	static class PageviewRecord implements Writable{
		String bdUserUuid;
		long date;
		int userChannelTag;
		long firstChannelVisit;
		int pageview;

		public PageviewRecord() {
		}


		public PageviewRecord(String bdUserUuid, long date,
		                      int userChannelTag, long firstChannelVisit, int pageview) {
			this.bdUserUuid = bdUserUuid;
			this.date = date;
			this.userChannelTag = userChannelTag;
			this.firstChannelVisit = firstChannelVisit;
			this.pageview = pageview;
		}

		public PageviewRecord(AccessRecord accessRecord, int pageview) {
			this.bdUserUuid = accessRecord.bdUserUuid;
			this.date = accessRecord.date;
			this.userChannelTag = accessRecord.userChannelTag;
			this.firstChannelVisit = accessRecord.firstChannelVisit;
			this.pageview = pageview;
		}

		/**
		 * Serialize the fields of this object to <code>out</code>.
		 *
		 * @param out <code>DataOuput</code> to serialize this object into.
		 * @throws java.io.IOException
		 */
		@Override
		public void write(DataOutput out) throws IOException {
			Text.writeString(out, bdUserUuid);
			out.writeLong(date);
			out.writeInt(userChannelTag);
			out.writeLong(firstChannelVisit);
			out.writeInt(pageview);
		}

		/**
		 * Deserialize the fields of this object from <code>in</code>.
		 * <p/>
		 * <p>For efficiency, implementations should attempt to re-use storage in the
		 * existing object where possible.</p>
		 *
		 * @param in <code>DataInput</code> to deseriablize this object from.
		 * @throws java.io.IOException
		 */
		@Override
		public void readFields(DataInput in) throws IOException {
			this.bdUserUuid = Text.readString(in);
			this.date = in.readLong();
			this.userChannelTag = in.readInt();
			this.firstChannelVisit = in.readLong();
			this.pageview = in.readInt();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(bdUserUuid);
			sb.append(",");
			sb.append(date);
			sb.append(",");
			sb.append(userChannelTag);
			sb.append(",");
			sb.append(String.valueOf(firstChannelVisit));
			sb.append(",");
			sb.append(String.valueOf(pageview));
			return sb.toString();
		}
	}

	/**
	 * Mapper parse every logs and check berkeley DB
	 * and pair for each access record.
	 */
	public static class PageViewCountMapper extends MapReduceBase implements
			Mapper<Object, Text, AccessRecord, IntWritable> {
		/**
		 * A counter of page view record
		 */
		private static final IntWritable one = new IntWritable(1);

		/**
		 * Local cache files path of distributed cache files
		 */
		private Path[] localFiles;

		/**
		 * BDB user index data access object
		 */
		private BDBUserIndexReadDao bdbUserIndexDao;

		/**
		 * BDB user channel data access object
		 */
		private BDBUserChannelTagReadDao bdbUserChannelTagDao;

		/**
		 * Configure the distributed cache
		 * @param job
		 */
		@Override
		public void configure(JobConf job) {
			try {
				LOGGER.info("Configuration the job");

				//通过 main > config.set  LOCAL_PATH
				String LOCAL_PATH = job.get("LOCAL_PATH");

				// 聚拢bdb文件: 只允许第一个jvm文件拷贝后自己再使用，其他jvm是等待拷贝结束后使用（设备级锁）
				// 用料, lock 、compile 两个文件
				String lock_file = LOCAL_PATH+"/lock";
				String compile_file = LOCAL_PATH+"/compile";

				/**
				 * Get local cache file paths of distributed cache files
				 */
				localFiles = DistributedCache.getLocalCacheFiles(job);

				LOGGER.info("Distributed dache files number: "
						+ localFiles.length);

				/**
				 * Make a specified local path
				 */
				File tmpCachePath = new File(LOCAL_PATH);
				tmpCachePath.mkdirs();
				LOGGER.info("Make a specified local directory");

				// 多进程共同使用，聚拢bdb文件，后使用
				File compileFile = new File(compile_file);
				if(!compileFile.exists()){
					File file = new File(lock_file);
					FileOutputStream fis = new FileOutputStream(file);
					FileChannel fc = fis.getChannel();
					FileLock flock = fc.lock();

					try {
						if( !compileFile.exists() ){
							for (Path path : localFiles) {
								LOGGER.info("Temporary cache file: " + path.toString());
								File srcFile = new File(path.toString());
								File dstFile = new File(LOCAL_PATH, path.getName());
								FileUtils.copyFile(srcFile, dstFile);
							}
							compileFile.createNewFile();
							LOGGER.info("Bdb-copy compile");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						flock.release();
						flock.close();
						fc.close();
						fis.close();

						LOGGER.info("release lock bdb-copy directory");
					}
				}

				/**
				 * Put local cache files into a specified local directory
				 */


				/**
				 * Initialize Berkeley DB DAO
				 */
				bdbUserIndexDao = BDBUserIndexReadDao.getInstance();
				bdbUserChannelTagDao = BDBUserChannelTagReadDao.getInstance();
				BDBUserIndexDao.setPath(LOCAL_PATH);
				BDBUserChannelTagDao.setPath(LOCAL_PATH);
				LOGGER.info("Initialize Berkeley DB DAO");
			} catch (Exception e) {
				LOGGER.error("Exception:", e);
			}
		}

		@Override
		public void map(Object key, Text value,
		                OutputCollector<AccessRecord, IntWritable> output,
		                Reporter reporter) throws IOException {

			// 添加计数器,  count.rowNum ；为查询 部分 map慢
			Counter ct = reporter.getCounter("count", "rowNum");
			ct.increment(1);

			/**
			 * Get one access log
			 */
			String accessLog = value.toString();

			/**
			 * Parse log to get ua id, cookieId, accountId and channel code
			 */
			LogParser logParser = new LogParser();
			logParser.parseLog(accessLog);
			String uaId = logParser.getUaId();
			String cookieId = logParser.getCookieId();
			String accountId = logParser.getAccountId();
			String channelCode = logParser.getChannelCode();
			Date visitTime = logParser.getVisitTime();

			/**
			 * Get bigdata user uuid from berkeley DB
			 */
			String bdUserUuid = null;
			if (StringUtils.isNotBlank(accountId)) {
				bdUserUuid = bdbUserIndexDao.get(accountId);
			} else if (StringUtils.isNotBlank(cookieId)) {
				bdUserUuid = bdbUserIndexDao.get(cookieId);
			} else if (StringUtils.isNoneBlank(uaId)) {
				bdUserUuid = bdbUserIndexDao.get(uaId);
			}

			/**
			 * Get user channel tags from berkeley DB
			 */
			if(StringUtils.isNotBlank(bdUserUuid)) {
				// 匹配到的行数
				Counter ctm = reporter.getCounter("count", "matchNum");
				ctm.increment(1);

				Set<BDBUserChannelTag> bdbUserChannelTags = bdbUserChannelTagDao.get(bdUserUuid);

				if (bdbUserChannelTags != null && bdbUserChannelTags.size() > 0) {
					/**
					 * Calculate the time and channel when and how users first visit
					 */
					int userChannelTag = 0;
					Date firstChannelVisit = new Date(Long.MAX_VALUE);
					if (StringUtils.isNotBlank(channelCode)) {
						firstChannelVisit = visitTime;
						Iterator<BDBUserChannelTag> it = bdbUserChannelTags.iterator();
						while (it.hasNext()) {
							BDBUserChannelTag tmpChannelTag = it.next();
							if (channelCode.equals(tmpChannelTag.getChannelCode())) {
								if (visitTime.after(tmpChannelTag.getChannelTime())) {
									visitTime = tmpChannelTag.getChannelTime();
								}
								if (visitTime.before(firstChannelVisit)) {
									firstChannelVisit = visitTime;
									userChannelTag = tmpChannelTag.getChannelId();
								}
							} else {
								if (firstChannelVisit.after(tmpChannelTag.getChannelTime())) {
									firstChannelVisit = tmpChannelTag.getChannelTime();
									userChannelTag = tmpChannelTag.getChannelId();
								}
							}
						}
					} else {
						Iterator<BDBUserChannelTag> it = bdbUserChannelTags.iterator();
						while (it.hasNext()) {
							BDBUserChannelTag tmpChannelTag = it.next();
							if (firstChannelVisit.after(tmpChannelTag.getChannelTime())) {
								firstChannelVisit = tmpChannelTag.getChannelTime();
								userChannelTag = tmpChannelTag.getChannelId();
							}
						}
					}

					/**
					 * Set output of the mapper
					 */
					AccessRecord accessRecord = new AccessRecord(bdUserUuid,
							visitTime.getTime(), userChannelTag, firstChannelVisit.getTime());
					output.collect(accessRecord, one);
				}
			}

		}
	}

	/**
	 * Reducer sums up the pageviews and emits a PageviewRecord,
	 * which will correspond to one tuple in the db.
	 */
	public static class PageViewCountReducer extends MapReduceBase implements
			Reducer<AccessRecord, IntWritable, PageviewRecord, NullWritable> {
		NullWritable n = NullWritable.get();

		@Override
		public void reduce(AccessRecord key, Iterator<IntWritable> values,
		                   OutputCollector<PageviewRecord, NullWritable> output,
		                   Reporter reporter) throws IOException {
			int sum = 0;
			/**
			 * Count the sum of pageviews
			 */
			while (values.hasNext()) {
				sum += values.next().get();
			}

			/**
			 * Set output of the reducer
			 */
			output.collect(new PageviewRecord(key, sum), n);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		LOGGER.info("MapReduce begins to run.");

		/**
		 * Set a job
		 */
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

		conf.set("LOCAL_PATH", "/tmp/bdb2/"+otherArgs[1].split("//")[otherArgs[1].split("//").length-1]);
		conf.set("io.sort.mb", "250");
		conf.set("mapred.child.java.opts", "-Xmx2046m");
		if (otherArgs.length != 2) {
			System.err.println("Usage: PageViewCount <in> <out>");
			System.exit(2);
		}
		JobConf job = new JobConf(conf, PageViewCount.class);
		job.setJobName("Count Pageviews of URLs");
		job.setJarByClass(PageViewCount.class);

		LOGGER.info("Set job completed.");

		/**
		 * Set distributed cache files path to the job
		 */
		FileSystem fs = FileSystem.get(conf);
		Path p = new Path(HDFS_PATH);
		if (!fs.exists(p)) {
			fs.mkdirs(p);
		}
		RemoteIterator<LocatedFileStatus> rit = fs.listFiles(p, true);
		while (rit.hasNext()) {
			Path f = rit.next().getPath();
			if (fs.isFile(f)) {
				LOGGER.info("Distributed cache file: " + f.toString());
				DistributedCache.addCacheFile(f.toUri(), job);
			}
		}
		LOGGER.info("Set distributed cache files completed.");

		/**
		 * set the class of mapper and reducer
		 */
		job.setMapperClass(PageViewCountMapper.class);
		job.setReducerClass(PageViewCountReducer.class);

		LOGGER.info("Set class of mapper and reducer completed.");

		/**
		 * Set input files path of the mapper
		 */
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));

		LOGGER.info("Set input completed.");

		/**
		 * Set the parameters of the output database
		 */
		// DBConfiguration.configureDB(job, DRIVER_CLASS, DB_URL, USERNAME,
		// PASSWORD);
		// DBOutputFormat.setOutput(job, TABLE_NAME, FIELD_NAMES);

		/**
		 * Set output files path of the mapper
		 */
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		LOGGER.info("Set output completed.");

		/**
		 * Set output class of mapper and reducer
		 */
		job.setMapOutputKeyClass(AccessRecord.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(PageviewRecord.class);
		job.setOutputValueClass(NullWritable.class);

		LOGGER.info("Set output class of mapper and reducer completed.");

		LOGGER.info("Job begins to run.");
		JobClient.runJob(job);
		LOGGER.info("Job ends running.");

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new PageViewCount(), args);

		/**
		 * Parse a output file of reducer to update the database
		 */
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(args[1]);
		FileStatus[] fileStatus = fs.listStatus(path);
		for (FileStatus status : fileStatus) {
			if (status.isFile()) {
				if (status.getPath().getName().startsWith("part")) {
					FileParser.parseOutput(status.getPath());
				}
			}
		}

		System.exit(ret);
	}
}
