package com.github.gaoyangthu.esanalysis.hbase.dao;

import com.github.gaoyangthu.core.hbase.RowMapper;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserIndex;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserIdConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 下午7:59
 */
public class UserIndexMapper implements RowMapper<UserIndex> {

	@Override
	public UserIndex mapRow(Result result, int rowNum) throws Exception {
//		if(rowNum % 100 == 0) {
//			System.out.println("rowNum" + rowNum);
//		}

		if (result == null) {
			return null;
		}

		String id = Bytes.toString(result.getRow());
		String bdUserUuid = null;
		Long timestamp = null;
		KeyValue keyValue = result.getColumnLatest(Bytes.toBytes(UserIdConst.BD_USER_UUID), Bytes.toBytes(UserIdConst.BD_USER_UUID));
		if(keyValue != null) {
			bdUserUuid = Bytes.toString(keyValue.getValue());
			timestamp = keyValue.getTimestamp();
		}

//		for (KeyValue keyValue : result.raw()) {
//			if (UserIdConst.BD_USER_UUID.equals(Bytes.toString(keyValue.getFamily()))
//					&& UserIdConst.BD_USER_UUID.equals(Bytes.toString(keyValue.getQualifier()))) {
//				bdUserUuid = Bytes.toString(keyValue.getValue());
//				timestamp = keyValue.getTimestamp();
//			}
//		}

		UserIndex userIndex = null;
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(bdUserUuid)) {
			userIndex = new UserIndex(id, bdUserUuid, timestamp);
		}

		return userIndex;
	}

}
