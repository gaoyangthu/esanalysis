package com.github.gaoyangthu.esanalysis.hbase.dao;

import com.github.gaoyangthu.core.hbase.RowMapper;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserTag;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-14
 * Time: 上午10:00
 */
@Deprecated
public class UserTagMapper implements RowMapper<UserTag> {
	@Override
	public UserTag mapRow(Result result, int rowNum) throws Exception {
		if (result == null) {
			return null;
		}

		String bdUserUuid = Bytes.toString(result.getRow());

		List<UserTag.ChannelTag> channelTags = new ArrayList<UserTag.ChannelTag>();
		List<UserTag.TradeTag> tradeTags = new ArrayList<UserTag.TradeTag>();

		NavigableMap<byte[], NavigableMap<byte[], byte[]>> map = result.getNoVersionMap();

		UserTag userTag = null;
		if (StringUtils.isNotBlank(bdUserUuid)) {
			userTag = new UserTag(bdUserUuid, channelTags, tradeTags);
		}
		return userTag;
	}
}
