package com.github.gaoyangthu.esanalysis.hbase.service.impl;

import com.github.gaoyangthu.core.hbase.HbaseTemplate;
import com.github.gaoyangthu.core.hbase.HbaseTemplateFactory;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserChannelTag;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserChannelTagConst;
import com.github.gaoyangthu.esanalysis.hbase.dao.UserChannelTagMapper;
import com.github.gaoyangthu.esanalysis.hbase.service.UserChannelTagService;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 上午11:23
 */
public class UserChannelTagServiceImpl implements UserChannelTagService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserChannelTagServiceImpl.class);

	private HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

	/**
	 * 添加渠道标签
	 *
	 * @param bdUserUuid
	 * @param channelId
	 * @param channelCode
	 * @param channelName
	 * @param channelTime
	 * @return
	 */
	@Override
	public boolean addChannelTag(String bdUserUuid, Integer channelId,
			String channelCode, String channelName, Date channelTime) {
		boolean flag = false;

		String timeStr = UserChannelTagConst.sdf.format(channelTime);
		String rowKey = bdUserUuid + "_" + channelTime.getTime();

		HTableInterface userTagTable = hbaseTemplate.getTable(UserChannelTagConst.USER_CHANNEL_TAG);
		try {
			Put put = new Put(Bytes.toBytes(rowKey));

			// channel_tag:bd_user_uuid
			put.add(Bytes.toBytes(UserChannelTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserChannelTagConst.BD_USER_UUID),
					Bytes.toBytes(bdUserUuid));
			// channel_tag:channel_id
			put.add(Bytes.toBytes(UserChannelTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserChannelTagConst.CHANNEL_ID),
					Bytes.toBytes(channelId));
			// channel_tag:channel_code
			put.add(Bytes.toBytes(UserChannelTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserChannelTagConst.CHANNEL_CODE),
					Bytes.toBytes(channelCode));
			// channel_tag:channel_name
			put.add(Bytes.toBytes(UserChannelTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserChannelTagConst.CHANNEL_NAME),
					Bytes.toBytes(channelName));
			// channel_tag:channel_time
			put.add(Bytes.toBytes(UserChannelTagConst.CHANNEL_TAG),
					Bytes.toBytes(UserChannelTagConst.CHANNEL_TIME),
					Bytes.toBytes(timeStr));

			userTagTable.put(put);

			flag = true;
		} catch (IOException e) {
			LOGGER.error("Add channel tag error", e);
		} finally {
			hbaseTemplate.releaseTable(UserChannelTagConst.USER_CHANNEL_TAG,
					userTagTable);
		}

		return flag;
	}

	/**
	 * 查询所有用户渠道标签
	 *
	 * @return
	 */
	@Override
	public List<UserChannelTag> findAll() {
		Scan scan = new Scan();
		List<UserChannelTag> userChannelTags = hbaseTemplate.find(
				UserChannelTagConst.USER_CHANNEL_TAG, scan,
				new UserChannelTagMapper());
		return userChannelTags;
	}

	/**
	 * 根据用户的bdUserUuid查找渠道标签列表
	 *
	 * TODO GOOD!本实现方法实现的 scan 查询方面，在注解上希望能展开
	 *      结构+方法:  scan 的  key_+  min > max 
	 *      接口使用:  hbase API > find(RowMapper)  
	 *	目的是希望，其他同学也能快速学习上这些方法特性，谢谢了
	 *  
	 * @param bdUserUuid
	 * @return
	 */
	@Override
	public List<UserChannelTag> findByBdUserId(String bdUserUuid) {
		String startRow = bdUserUuid + "_" + Long.MIN_VALUE;
		String endRow = bdUserUuid + "_" + Long.MAX_VALUE;

		Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(endRow));
		List<UserChannelTag> userChannelTags = hbaseTemplate.find(
				UserChannelTagConst.USER_CHANNEL_TAG, scan,
				new UserChannelTagMapper());
		return userChannelTags;
	}

	/**
	 * 根据渠道码查询用户渠道标签
	 *
	 * @param channelCode
	 * @return
	 */
	@Override
	public List<UserChannelTag> findByChannelCode(String channelCode) {
		return findByValue(UserChannelTagConst.CHANNEL_TAG,
				UserChannelTagConst.CHANNEL_CODE, channelCode);
	}

	/**
	 * 根据渠道名称查询用户渠道标签
	 *
	 * @param channelName
	 * @return
	 */
	@Override
	public List<UserChannelTag> findByChannelName(String channelName) {
		return findByValue(UserChannelTagConst.CHANNEL_TAG,
				UserChannelTagConst.CHANNEL_NAME, channelName);
	}

	/**
	 * 根据值查询用户渠道标签
	 *
	 * @param family
	 * @param qualifier
	 * @param value
	 * @return
	 */
	@Override
	public List<UserChannelTag> findByValue(String family, String qualifier,
			String value) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family),
				Bytes.toBytes(qualifier), CompareFilter.CompareOp.EQUAL,
				new SubstringComparator(value));

		Scan scan = new Scan();
		scan.setFilter(filter);
		List<UserChannelTag> userChannelTags = hbaseTemplate.find(
				UserChannelTagConst.USER_CHANNEL_TAG, scan,
				new UserChannelTagMapper());
		return userChannelTags;
	}

	/**
	 * 更新用户ID
	 *
	 * @param oldId
	 * @param newId
	 * @return
	 */
	@Override
	public boolean changeBdUser(String oldId, String newId) {
		boolean flag = false;

		// 查找oldId对应的渠道标签
		List<UserChannelTag> oldTags = findByBdUserId(oldId);
		if ((oldTags == null) || (oldTags.size() == 0)) {
			return true;
		}

		LOGGER.debug("Exchange user channel tags. oldId={}, newId={}", oldId, newId);
		// 使用newId挨个添加对应的渠道标签(可优化为批量添加)
		for (UserChannelTag userChannelTag : oldTags) {
			addChannelTag(newId, userChannelTag.getChannelId(),
					userChannelTag.getChannelCode(),
					userChannelTag.getChannelName(),
					userChannelTag.getChannelTime());
		}
		// 删除oldId的渠道标签
		HTableInterface userTagTable = hbaseTemplate.getTable(UserChannelTagConst.USER_CHANNEL_TAG);
		try {
			List<Delete> deletes = new ArrayList<Delete>(oldTags.size());

			for (UserChannelTag userChannelTag : oldTags) {
				Delete delete = new Delete(
						Bytes.toBytes(userChannelTag.getBdUserUuidTimestamp()));
				deletes.add(delete);
			}

			userTagTable.delete(deletes);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("Delete channel tag error", e);
		} finally {
			hbaseTemplate.releaseTable(UserChannelTagConst.USER_CHANNEL_TAG,
					userTagTable);
		}

		return flag;
	}
}
