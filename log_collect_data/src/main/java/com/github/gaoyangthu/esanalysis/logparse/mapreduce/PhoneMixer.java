package com.github.gaoyangthu.esanalysis.logparse.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA
 * Project: esanalysis
 * Author: GaoYang
 * Date: 2014/7/28 0028
 */
public class PhoneMixer extends Configured implements Tool {
	public static class PhoneMixMapper extends Mapper<Object, Text, Text, Text> {
		@Override
		public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
			InputSplit inputSplit = context.getInputSplit();
			String fileName = ((FileSplit) inputSplit).getPath().getName();
			Text file = new Text();
			file.set(fileName);

			String log = value.toString();
			String str = "";

			String regexMDN = "(^|.*?\\|)((\\+86|86){0,1})([\\d]{11})\\|.*$";
			Pattern pattern = Pattern.compile(regexMDN);
			Matcher matcher = pattern.matcher(log);
			if (matcher.matches()) {
				String mdn = matcher.group(4);
				str = log.replace(mdn, mixMDN(mdn));
			}

			String regexIMSI = "(^|.*?\\|)((460)([\\d]{12}))\\|.*$";
			pattern = Pattern.compile(regexIMSI);
			matcher = pattern.matcher(log);
			if (matcher.matches()) {
				String imsi = matcher.group(2);
				if (str.equals("")) {
					str = log.replace(imsi, mixIMSI(imsi));
				} else {
					str = str.replace(imsi, mixIMSI(imsi));
				}
			}

			if (!str.equals("")) {
				Text result = new Text();
				result.set(str);
				context.write(file, result);
			}
		}
	}

	public static class PhoneMixReducer extends Reducer<Text, Text, Text, Text> {
		Text nt = new Text("");
		private MultipleOutputs<Text, Text> mos;

		@Override
		public void setup(Context context) throws IOException,InterruptedException {
			mos = new MultipleOutputs(context);
		}

		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
			for (Text result: values) {
				mos.write("output", result, nt, key.toString());
			}
		}

		@Override
		public void cleanup(Context context) throws IOException,InterruptedException {
			mos.close();
		}
	}

	private static String mixMDN(String mdn) {
		StringBuilder mixed = new StringBuilder("");
		mixed.append(mdn.substring(0, 3)).append(mdn.charAt(4)).append(mdn.charAt(10))
			.append(mdn.charAt(7)).append(mdn.charAt(9)).append(mdn.charAt(5))
			.append(mdn.charAt(3)).append(mdn.charAt(6)).append(mdn.charAt(8));
		return mixed.toString();
	}

	private static String mixIMSI(String imsi) {
		StringBuilder mixed = new StringBuilder("");
		mixed.append(imsi.substring(0, 7)).append(imsi.charAt(7)).append(imsi.charAt(10))
			.append(imsi.charAt(13)).append(imsi.charAt(9)).append(imsi.charAt(11))
			.append(imsi.charAt(14)).append(imsi.charAt(12)).append(imsi.charAt(8));
		return mixed.toString();
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		Job job = new Job(conf, "PhoneMix");
		job.setNumReduceTasks(30);
		job.setJarByClass(PhoneMixer.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(PhoneMixMapper.class);
		job.setReducerClass(PhoneMixReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		MultipleOutputs.addNamedOutput(job, "output", TextOutputFormat.class, Text.class, Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true)? 0 : 1);
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new PhoneMixer(), args);
		System.exit(ret);
	}
}
