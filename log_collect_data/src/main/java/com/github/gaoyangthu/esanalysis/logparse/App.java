package com.github.gaoyangthu.esanalysis.logparse;

import java.io.IOException;

import com.github.gaoyangthu.esanalysis.logparse.service.LogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-19
 * Time: 下午5:23
 * 
 * 
 * Codereview: liuky
 * Time: 2014-04-02 16:35
 * CV_ID : x001
 */
public class App {

	/*
	 * TODO hdfs 目录结构整理 CV_ID:X001 这个 下的结构，整理下 建议每天一个目录 当天数据，也新生成目录方里
	 */
	private static String LOG_PATH = "hdfs://bdcluster/esanalysis/nginx/log_201405";

	public static void main(String[] args) {

		if(args.length > 0) {
			LOG_PATH = args[0];
		}

		Configuration conf = new Configuration();
		FileSystem fileSystem = null;
		try {
			fileSystem = FileSystem.get(conf);

			Path path = new Path(LOG_PATH);
			FileStatus[] fileStatuses = fileSystem.listStatus(path);
			for (FileStatus fileStatus : fileStatuses) {
				if (fileStatus.isFile()) {
					Path filePath = fileStatus.getPath();
					System.out.println(filePath.getName());
					FSDataInputStream inputStream = fileSystem.open(filePath);
					LogParser logParser = new LogParser();
					logParser.parseLog(inputStream);
					inputStream.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
