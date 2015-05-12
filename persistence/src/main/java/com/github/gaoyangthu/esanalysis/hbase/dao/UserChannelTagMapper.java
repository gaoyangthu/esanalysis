package com.github.gaoyangthu.esanalysis.hbase.dao;

import com.github.gaoyangthu.core.hbase.RowMapper;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserChannelTag;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserChannelTagConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 上午10:36
 */
public class UserChannelTagMapper implements RowMapper<UserChannelTag> {
	@Override
	public UserChannelTag mapRow(Result result, int rowNum) throws Exception {
		if (result == null) {
			return null;
		}

		String bdUserUuidTimestamp = Bytes.toString(result.getRow());
		if (StringUtils.isBlank(bdUserUuidTimestamp)) {
			return null;
		}

		UserChannelTag userChannelTag = new UserChannelTag(bdUserUuidTimestamp);
		for (KeyValue keyValue : result.raw()) {
			if (UserChannelTagConst.CHANNEL_TAG.equals(Bytes.toString(keyValue.getFamily()))) {
				String column = Bytes.toString(keyValue.getQualifier());
				String value = Bytes.toString(keyValue.getValue());
				if (UserChannelTagConst.BD_USER_UUID.equals(column)) {
					userChannelTag.setBdUserUuid(value);
				} else if (UserChannelTagConst.CHANNEL_ID.equals(column)) {
					userChannelTag.setChannelId(Bytes.toInt(keyValue.getValue()));
				} else if (UserChannelTagConst.CHANNEL_CODE.equals(column)) {
					userChannelTag.setChannelCode(value);
				} else if (UserChannelTagConst.CHANNEL_NAME.equals(column)) {
					userChannelTag.setChannelName(value);
				} else if (UserChannelTagConst.CHANNEL_TIME.equals(column)) {
					Date time = UserChannelTagConst.sdf.parse(value);
					userChannelTag.setChannelTime(time);
				}
			}
		}

		return userChannelTag;
	}
}
