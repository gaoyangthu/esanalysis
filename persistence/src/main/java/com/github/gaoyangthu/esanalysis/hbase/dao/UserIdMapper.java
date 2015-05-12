package com.github.gaoyangthu.esanalysis.hbase.dao;

import com.github.gaoyangthu.core.hbase.RowMapper;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserId;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserIdConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 上午11:17
 */
public class UserIdMapper implements RowMapper<UserId> {

	@Override
	public UserId mapRow(Result result, int rowNum) throws Exception {
		if (result == null) {
			return null;
		}

		String bdUserUuid = Bytes.toString(result.getRow());
		Map<String, String> ids = new HashMap<String, String>();
		for (KeyValue keyValue : result.raw()) {
			if (UserIdConst.ID.equals(Bytes.toString(keyValue.getFamily()))) {
				String family = Bytes.toString(keyValue.getQualifier());
				String value = Bytes.toString(keyValue.getValue());

				ids.put(family, value);
			}
		}

		UserId userId = null;
		if (StringUtils.isNotBlank(bdUserUuid) && (ids.size() > 0)) {
			userId = new UserId(bdUserUuid, ids);
		}

		return userId;
	}
}
