package com.github.gaoyangthu.esanalysis.bdb.service.impl;

import com.github.gaoyangthu.esanalysis.bdb.bean.BDBUserChannelTag;
import com.github.gaoyangthu.esanalysis.bdb.dao.BDBUserChannelTagDao;
import com.github.gaoyangthu.esanalysis.bdb.dao.BDBUserIndexDao;
import com.github.gaoyangthu.esanalysis.bdb.service.BDBService;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserChannelTag;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserId;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserIndex;
import com.github.gaoyangthu.esanalysis.hbase.service.UserChannelTagService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserChannelTagServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserIdServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * BerkeleyDB服务
 * <li>生成BDB数据</li>
 * <li>发布BDB到HDFS</li>
 *
 * Author: gaoyangthu
 * Date: 2014/3/31
 * Time: 11:42
 */
public class BDBServiceImpl implements BDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BDBServiceImpl.class);

	private String localPath = "bdb";

	private String hdfsPath = "hdfs://bdcluster/esanalysis/bdb/";

	private UserIdService userIdService;

	private UserChannelTagService userChannelTagService;

	private BDBUserIndexDao bdbUserIndexDao;

	private BDBUserChannelTagDao bdbUserChannelTagDao;

	public BDBServiceImpl() {
		userIdService = new UserIdServiceImpl();
		userChannelTagService = new UserChannelTagServiceImpl();

		File file = new File(localPath);
		try {
			FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			LOGGER.error("IOException", e);
		}
		if (!file.exists()) {
			file.mkdirs();
		}

		BDBUserIndexDao.setPath(localPath);
		BDBUserChannelTagDao.setPath(localPath);

		bdbUserIndexDao = BDBUserIndexDao.getInstance();
		bdbUserChannelTagDao = BDBUserChannelTagDao.getInstance();
	}

	/**
	 * 从HBase中读取用户标识索引和用户标签，输出到BDB
	 *
	 * TODO 在注解里，稍微详细描述下  hbase > [user,channel_tag,time] > bdb 
	 * 
	 * @return
	 */
	@Override
	public boolean stat() {
		LOGGER.info("Starting stat hbase to bdb.");
		// 写入user_index
		LOGGER.info("Starting stat user_index to bdb.");
		List<UserIndex> userIndexes = userIdService.findAllUserIndex();
		for (UserIndex userIndex : userIndexes) {
			if (userIndex != null) {
//				LOGGER.debug("user_index: id={}, bdUserUuid={}", userIndex.getId(), userIndex.getBdUserUuid());
				bdbUserIndexDao.create(userIndex.getId(),
                        userIndex.getBdUserUuid());
			}
		}

		// 写入user_channel_tag
		LOGGER.info("Starting stat user_channel_tag to bdb.");
		List<UserId> userIds = userIdService.findAllUserId();
		for (UserId userId : userIds) {
			if (userId != null) {
				String bdUserUuid = userId.getBdUserUuid();
				
				Map<Integer, BDBUserChannelTag> bdbUserChannelTagMap = new HashMap<Integer, BDBUserChannelTag>();
				List<UserChannelTag> userChannelTags = userChannelTagService.findByBdUserId(bdUserUuid);
				for (UserChannelTag userChannelTag : userChannelTags) {
					if (userChannelTag != null) {
						Integer channelId = userChannelTag.getChannelId();
						String channelCode = userChannelTag.getChannelCode();
						String channelName = userChannelTag.getChannelName();
						Date channelTime = userChannelTag.getChannelTime();

						BDBUserChannelTag bdbUserChannelTag = new BDBUserChannelTag(
								channelId, channelCode, channelName,
								channelTime);

						bdbUserChannelTagMap.put(channelId, bdbUserChannelTag);
					}
				}

				Set<BDBUserChannelTag> bdbUserChannelTags = new HashSet<BDBUserChannelTag>(
						bdbUserChannelTagMap.values());

				bdbUserChannelTagDao.create(bdUserUuid, bdbUserChannelTags);
			}
		}

		BDBUserIndexDao.close();
		BDBUserChannelTagDao.close();

		LOGGER.info("End stat user_index and user_channel_tag to bdb.");
		return true;
	}

	/**
	 * 将BDB发布到HDFS中
	 *
	 * @return
	 */
	@Override
	public boolean deploy() {

//		//将本地bdb文件打包成zip压缩文件
//		String srcfile = localPath + ".zip";
//		ZipUtils.zip(srcfile, localPath);

		boolean flag = false;
		Configuration conf = new Configuration();
		FileSystem fileSystem = null;
		try {
			// 本地目录
			Path[] srcPath = null;
			File localFile = new File(localPath);
			if (localFile.exists() && localFile.isDirectory()) {
				File[] files = localFile.listFiles();
				if (files != null) {
					srcPath = new Path[files.length];
					for (int i = 0; i < files.length; i++) {
						Path path = new Path(files[i].getAbsolutePath());
						srcPath[i] = path;
					}
				}
			}
//			File localFile = new File(srcfile);
//			Path srcPath = new Path(localFile.getAbsolutePath());

			// 远程目录
			Path dstPath = new Path(hdfsPath);
			fileSystem = FileSystem.get(conf);

			if (fileSystem.exists(dstPath)) {
				fileSystem.delete(dstPath, true);
			}

			if (!fileSystem.exists(dstPath)) {
				fileSystem.mkdirs(dstPath);
			}

			// 从本地拷贝
			fileSystem.copyFromLocalFile(false, true, srcPath, dstPath);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("IOException", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * @return
	 */
	@Override
	public boolean report() {
		boolean flag = false;

		flag = stat();
		if (!flag) {
			LOGGER.error("bdb stat error");
			return false;
		}
		flag = deploy();
		if (!flag) {
			LOGGER.error("bdb deploy error");
		}

		flag = true;
		return flag;
	}
}
