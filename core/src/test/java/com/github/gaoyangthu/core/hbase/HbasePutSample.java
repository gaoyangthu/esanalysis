package com.github.gaoyangthu.core.hbase;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-11
 * Time: 下午2:48
 */
public class HbasePutSample {

	public static void main(String[] args) {
		HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

		HTableInterface hTable = hbaseTemplate.getTable("testtable");
		Put put = new Put(Bytes.toBytes("row1"));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val1"));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"), Bytes.toBytes("val2"));

		try {
			hTable.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		}

		hbaseTemplate.releaseTable("testtable", hTable);
	}

}
