package com.github.gaoyangthu.core.hbase.mapper;

import com.github.gaoyangthu.core.hbase.RowMapper;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-11
 * Time: 下午4:42
 */
public class PrintMapper implements RowMapper {

	@Override
	public Object mapRow(Result result, int rowNum) throws Exception {
		System.out.println("row:" + Bytes.toString(result.getRow()));
		for(KeyValue keyValue : result.raw()) {
			System.out.println("family:" + Bytes.toString(keyValue.getFamily()));
			System.out.println("Qualifier:" + Bytes.toString(keyValue.getQualifier()));
			System.out.println("value:" + Bytes.toString(keyValue.getValue()));
			System.out.println("timestamp:" + keyValue.getTimestamp());
		}
		System.out.println("Result:" + result);
		return result;
	}
}
