package com.github.gaoyangthu.core.hbase;

import org.apache.hadoop.conf.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-11
 * Time: 下午2:31
 */
public class HbaseTemplateFactory {

	private static volatile HbaseTemplate hbaseTemplate;

	static {
		HbaseConfigurationFactory hbaseConfigurationFactory = new HbaseConfigurationFactory();
		hbaseConfigurationFactory.setZkQuorum("172.18.108.101,172.18.108.102,172.18.108.103");
		hbaseConfigurationFactory.setZkPort(2181);
		hbaseConfigurationFactory.afterPropertiesSet();
		Configuration configuration = hbaseConfigurationFactory.getObject();

		hbaseTemplate = new HbaseTemplate(configuration);
	}

	public static HbaseTemplate getHbaseTemplate() {
		return hbaseTemplate;
	}
}
