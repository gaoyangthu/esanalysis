package com.github.gaoyangthu.esanalysis.bdb.service;

/**
 * BerkeleyDB服务
 * <li>生成BDB数据</li>
 * <li>发布BDB到HDFS</li>
 *
 * Author: gaoyangthu
 * Date: 2014/3/31
 * Time: 11:34
 */
public interface BDBService {
	/**
	 * 从HBase中读取用户标识索引和用户标签，输出到BDB
	 *
	 * @return
	 */
	boolean stat();

	/**
	 * 将BDB发布到HDFS中
	 *
	 * @return
	 */
	boolean deploy();

	/**
	 * 先生成BDB数据，然后发布BDB到HDFS
	 *
	 * @return
	 */
	boolean report();
}
