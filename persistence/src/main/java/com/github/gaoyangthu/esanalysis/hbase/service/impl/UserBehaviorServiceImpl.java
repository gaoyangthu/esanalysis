package com.github.gaoyangthu.esanalysis.hbase.service.impl;

import com.github.gaoyangthu.core.hbase.HbaseTemplate;
import com.github.gaoyangthu.core.hbase.HbaseTemplateFactory;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserBehavior;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserBehaviorConst;
import com.github.gaoyangthu.esanalysis.hbase.dao.UserBehaviorMapper;
import com.github.gaoyangthu.esanalysis.hbase.service.UserBehaviorService;
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
 * Time: 下午4:00
 */
public class UserBehaviorServiceImpl implements UserBehaviorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserBehaviorServiceImpl.class);

	private HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

	/**
	 *
	 * @param bdUserUuid
	 * @param url
	 * @param urlRegexId
	 * @param urlRegex
	 * @param userBehaviorId
	 * @param userBehaviorName
	 * @param ip
	 * @param time
	 * @param code
	 * @param userAgent
	 * @return
	 */
	@Override
	public boolean addUserBehavior(String bdUserUuid, String url,
			Integer urlRegexId, String urlRegex, Integer userBehaviorId,
			String userBehaviorName, String ip, Date time, int code,
			String userAgent) {
		boolean flag = false;

		String timeStr = UserBehaviorConst.sdf.format(time);
		String rowKey = bdUserUuid + "_" + time.getTime();

		HTableInterface userTagTable = hbaseTemplate.getTable(UserBehaviorConst.USER_BEHAVIOR);
		try {
			Put put = new Put(Bytes.toBytes(rowKey));

			// behavior:bd_user_uuid
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.BD_USER_UUID),
					Bytes.toBytes(bdUserUuid));
			// behavior:url
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.URL), Bytes.toBytes(url));
			// behavior:url_regex_id
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.URL_REGEX_ID),
					Bytes.toBytes(urlRegexId));
			// behavior:url_regex
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.URL_REGEX),
					Bytes.toBytes(urlRegex));
			// behavior:user_behavior_id
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.USER_BEHAVIOR_ID),
					Bytes.toBytes(userBehaviorId));
			// behavior:user_behavior_name
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.USER_BEHAVIOR_NAME),
					Bytes.toBytes(userBehaviorName));
			// behavior:ip
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.IP), Bytes.toBytes(ip));
			// behavior:time
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.TIME),
					Bytes.toBytes(timeStr));
			// behavior:code
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.CODE), Bytes.toBytes(code));
			// behavior:user_agent
			put.add(Bytes.toBytes(UserBehaviorConst.BEHAVIOR),
					Bytes.toBytes(UserBehaviorConst.USER_AGENT),
					Bytes.toBytes(userAgent));

			userTagTable.put(put);

			flag = true;
		} catch (IOException e) {
			LOGGER.error("Add user behavior error", e);
		} finally {
			hbaseTemplate.releaseTable(UserBehaviorConst.USER_BEHAVIOR,
					userTagTable);
		}

		return flag;
	}

	/**
	 *
	 * @param bdUserUuid
	 * @return
	 */
	@Override
	public List<UserBehavior> findByBdUserId(String bdUserUuid) {
		String startRow = bdUserUuid + "_" + Long.MIN_VALUE;
		String endRow = bdUserUuid + "_" + Long.MAX_VALUE;

		Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(endRow));
		List<UserBehavior> userBehaviors = hbaseTemplate.find(
				UserBehaviorConst.USER_BEHAVIOR, scan, new UserBehaviorMapper());
		return userBehaviors;
	}

	/**
	 *
	 * @param urlRegexId
	 * @return
	 */
	@Override
	public List<UserBehavior> findByUrlRegexId(Integer urlRegexId) {
		return findByValue(UserBehaviorConst.BEHAVIOR,
				UserBehaviorConst.URL_REGEX_ID, String.valueOf(urlRegexId));
	}

	/**
	 *
	 * @param urlRegex
	 * @return
	 */
	@Override
	public List<UserBehavior> findByUrlRegex(String urlRegex) {
		return findByValue(UserBehaviorConst.BEHAVIOR,
				UserBehaviorConst.URL_REGEX, urlRegex);
	}

	/**
	 *
	 * @param userBehaviorId
	 * @return
	 */
	@Override
	public List<UserBehavior> findByUserBehaviorId(Integer userBehaviorId) {
		return findByValue(UserBehaviorConst.BEHAVIOR,
				UserBehaviorConst.USER_BEHAVIOR_ID, String.valueOf(userBehaviorId));
	}

	/**
	 *
	 * @param userBehavior
	 * @return
	 */
	@Override
	public List<UserBehavior> findByUserBehavior(String userBehavior) {
		return findByValue(UserBehaviorConst.BEHAVIOR,
				UserBehaviorConst.USER_BEHAVIOR, userBehavior);
	}

	/**
	 *
	 * @param family
	 * @param qualifier
	 * @param value
	 * @return
	 */
	@Override
	public List<UserBehavior> findByValue(String family, String qualifier,
			String value) {
		Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family),
				Bytes.toBytes(qualifier), CompareFilter.CompareOp.EQUAL,
				new SubstringComparator(value));

		Scan scan = new Scan();
		scan.setFilter(filter);
		List<UserBehavior> userBehaviors = hbaseTemplate.find(
				UserBehaviorConst.USER_BEHAVIOR, scan, new UserBehaviorMapper());
		return userBehaviors;
	}

	public boolean changeBdUser(String oldId, String newId) {
		boolean flag = false;
		// 获取oldId对应的用户行为
		List<UserBehavior> oldBehaviors = findByBdUserId(oldId);
		if ((oldBehaviors == null) || (oldBehaviors.size() == 0)) {
			return true;
		}

//		LOGGER.debug("Exchange user behaviors. oldId={}, newId={}", oldId, newId);
		// 使用newId添加对应的用户行为(可改为批量添加)
		for (UserBehavior behavior : oldBehaviors) {
			addUserBehavior(newId, behavior.getUrl(), behavior.getUrlRegexId(),
					behavior.getUrlRegex(), behavior.getUserBehaviorId(),
					behavior.getUserBehaviorName(), behavior.getIp(),
					behavior.getTime(), behavior.getCode(),
					behavior.getUserAgent());
		}

		// 删除oldId的用户行为
		HTableInterface userTagTable = hbaseTemplate.getTable(UserBehaviorConst.USER_BEHAVIOR);
		try {
			List<Delete> deletes = new ArrayList<Delete>(oldBehaviors.size());

			for (UserBehavior behavior : oldBehaviors) {
				Delete delete = new Delete(
						Bytes.toBytes(behavior.getBdUserUuidTimestamp()));
				deletes.add(delete);
			}

			userTagTable.delete(deletes);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("Delete user behavior error", e);
		} finally {
			hbaseTemplate.releaseTable(UserBehaviorConst.USER_BEHAVIOR,
					userTagTable);
		}
		return flag;
	}
}
