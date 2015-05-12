package com.github.gaoyangthu.core.hbase;

import com.github.gaoyangthu.core.hbase.mapper.PrintMapper;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-11
 * Time: 下午4:43
 */
public class HbaseFindSample {

	public static void main(String[] args) {
		HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

		hbaseTemplate.find("testtable", "colfam1", new PrintMapper());

	}

}
