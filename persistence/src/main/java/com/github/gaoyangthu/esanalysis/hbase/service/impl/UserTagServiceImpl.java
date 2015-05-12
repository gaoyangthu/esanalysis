package com.github.gaoyangthu.esanalysis.hbase.service.impl;

import com.github.gaoyangthu.core.hbase.HbaseTemplate;
import com.github.gaoyangthu.core.hbase.HbaseTemplateFactory;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserTagConst;
import com.github.gaoyangthu.esanalysis.hbase.service.UserTagService;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 上午9:52
 */
@Deprecated
public class UserTagServiceImpl implements UserTagService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTagServiceImpl.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 等价于now.toLocaleString()

	private HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

	public UserTagServiceImpl() {
	}

	public boolean addChannelTag(String bdUserUuid, String channelId,
			String channelCode, String channelName, Date channelTime) {
		boolean flag = false;

		String timeStr = sdf.format(channelTime);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserTagConst.USER_TAG);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			// channel_tag列族
			// channel_tag:channel_id
			put.add(Bytes.toBytes(UserTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserTagConst.CHANNEL_ID),
					Bytes.toBytes(channelId));
			// channel_tag:channel_code
			put.add(Bytes.toBytes(UserTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserTagConst.CHANNEL_CODE),
					Bytes.toBytes(channelCode));
			// channel_tag:channel_name
			put.add(Bytes.toBytes(UserTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserTagConst.CHANNEL_NAME),
					Bytes.toBytes(channelName));
			// channel_tag:channel_time
			put.add(Bytes.toBytes(UserTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserTagConst.CHANNEL_TIME),
					Bytes.toBytes(timeStr));

			userTagTable.put(put);

			flag = true;
		} catch (IOException e) {
			LOGGER.error("Add channel tag error", e);
		} finally {
			hbaseTemplate.releaseTable(UserTagConst.USER_TAG, userTagTable);
		}

		return flag;
	}

	public boolean addTradeTag(String bdUserUuid, String tradeId,
			String tradeName, String tradeAmount, Date channelTime) {
		boolean flag = false;

		String timeStr = sdf.format(channelTime);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserTagConst.USER_TAG);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			// trade_tag列族
			// trade_tag:trade_id
			put.add(Bytes.toBytes(UserTagConst.TRADE_TAG),
					Bytes.toBytes(UserTagConst.TRADE_ID),
					Bytes.toBytes(tradeId));
			// trade_tag:trade_name
			put.add(Bytes.toBytes(UserTagConst.TRADE_TAG),
					Bytes.toBytes(UserTagConst.TRADE_NAME),
					Bytes.toBytes(tradeName));
			// trade_tag:trade_amount
			put.add(Bytes.toBytes(UserTagConst.TRADE_TAG),
					Bytes.toBytes(UserTagConst.TRADE_AMOUNT),
					Bytes.toBytes(tradeAmount));
			// trade_tag:trade_time
			put.add(Bytes.toBytes(UserTagConst.TRADE_TAG),
					Bytes.toBytes(UserTagConst.TRADE_TIME),
					Bytes.toBytes(timeStr));

			userTagTable.put(put);

			flag = true;
		} catch (IOException e) {
			LOGGER.error("Add trade tag error", e);
		} finally {
			hbaseTemplate.releaseTable(UserTagConst.USER_TAG, userTagTable);
		}

		return flag;
	}

}
