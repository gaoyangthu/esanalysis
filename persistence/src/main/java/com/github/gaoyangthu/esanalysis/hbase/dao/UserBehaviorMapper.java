package com.github.gaoyangthu.esanalysis.hbase.dao;

import com.github.gaoyangthu.core.hbase.RowMapper;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserBehavior;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserBehaviorConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 下午3:41
 */
public class UserBehaviorMapper implements RowMapper<UserBehavior> {
	@Override
	public UserBehavior mapRow(Result result, int rowNum) throws Exception {
		if (result == null) {
			return null;
		}

		String bdUserUuidTimestamp = Bytes.toString(result.getRow());
		if (StringUtils.isBlank(bdUserUuidTimestamp)) {
			return null;
		}
		UserBehavior userBehavior = new UserBehavior(bdUserUuidTimestamp);
		for (KeyValue keyValue : result.raw()) {
			if (UserBehaviorConst.BEHAVIOR.equals(Bytes.toString(keyValue.getFamily()))) {
				String column = Bytes.toString(keyValue.getQualifier());
				String value = Bytes.toString(keyValue.getValue());
				if (UserBehaviorConst.BD_USER_UUID.equals(column)) {
					userBehavior.setBdUserUuid(value);
				} else if (UserBehaviorConst.URL.equals(column)) {
					userBehavior.setUrl(value);
				} else if (UserBehaviorConst.URL_REGEX_ID.equals(column)) {
					userBehavior.setUrlRegexId(Bytes.toInt(keyValue.getValue()));
				} else if (UserBehaviorConst.URL_REGEX.equals(column)) {
					userBehavior.setUrlRegex(value);
				} else if (UserBehaviorConst.USER_BEHAVIOR_ID.equals(column)) {
					userBehavior.setUserBehaviorId(Bytes.toInt(keyValue.getValue()));
				} else if (UserBehaviorConst.USER_BEHAVIOR_NAME.equals(column)) {
					userBehavior.setUserBehaviorName(value);
				} else if (UserBehaviorConst.IP.equals(column)) {
					userBehavior.setIp(value);
				} else if (UserBehaviorConst.TIME.equals(column)) {
					Date time = UserBehaviorConst.sdf.parse(value);
					userBehavior.setTime(time);
				} else if (UserBehaviorConst.CODE.equals(column)) {
					int code = Bytes.toInt(keyValue.getValue());
					userBehavior.setCode(code);
				} else if (UserBehaviorConst.USER_AGENT.equals(column)) {
					userBehavior.setUserAgent(value);
				}
			}
		}

		return userBehavior;
	}
}
