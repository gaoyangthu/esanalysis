package com.github.gaoyangthu.esanalysis.hbase.dao;

import com.github.gaoyangthu.core.hbase.RowMapper;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserStat;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserStatConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 上午11:28
 */
public class UserStatMapper implements RowMapper<UserStat> {
	@Override
	public UserStat mapRow(Result result, int rowNum) throws Exception {
		if (result == null) {
			return null;
		}

		String bdUserUuid = Bytes.toString(result.getRow());
		if (StringUtils.isBlank(bdUserUuid)) {
			return null;
		}

		UserStat userStat = new UserStat(bdUserUuid);
		for (KeyValue keyValue : result.raw()) {
			if (UserStatConst.STATISTIC.equals(Bytes.toString(keyValue.getFamily()))) {
				String column = Bytes.toString(keyValue.getQualifier());
				if (UserStatConst.TOTAL_ORDER_COUNT.equals(column)) {
					userStat.setTotalOrderCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.TOTAL_ORDER_AMOUNT.equals(column)) {
					userStat.setTotalOrderAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.PAYED_ORDER_COUNT.equals(column)) {
					userStat.setPayOrderCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.PAYED_ORDER_AMOUNT.equals(column)) {
					userStat.setPayOrderAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.ORDER_TRADE_COUNT.equals(column)) {
					userStat.setOrderTradeCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.ORDER_CASH_AMOUNT.equals(column)) {
					userStat.setOrderCashAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.CASH_CHARGE_COUNT.equals(column)) {
					userStat.setCashChargeCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.CASH_CHARGE_AMOUNT.equals(column)) {
					userStat.setCashChargeAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.CREDIT_CHARGE_COUNT.equals(column)) {
					userStat.setCreditChargeCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.CREDIT_CHARGE_AMOUNT.equals(column)) {
					userStat.setCreditChargeAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.PAYOUT_COUNT.equals(column)) {
					userStat.setPayoutCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.PAYOUT_AMOUNT.equals(column)) {
					userStat.setPayoutAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.WITHDRAWAL_COUNT.equals(column)) {
					userStat.setWithdrawalCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.WITHDRAWAL_AMOUNT.equals(column)) {
					userStat.setWithdrawalAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.REJECT_WITHDRAWAL_COUNT.equals(column)) {
					userStat.setRejectWithdrawalCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.REJECT_WITHDRAWAL_AMOUNT.equals(column)) {
					userStat.setRejectWithdrawalAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.REFUND_COUNT.equals(column)) {
					userStat.setRefundCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.REFUND_AMOUNT.equals(column)) {
					userStat.setRefundAmount(Bytes.toBigDecimal(keyValue.getValue()));
				} else if (UserStatConst.BUSI_CHARGE_COUNT.equals(column)) {
					userStat.setBusiChargeCount(Bytes.toInt(keyValue.getValue()));
				} else if (UserStatConst.BUSI_CHARGE_AMOUNT.equals(column)) {
					userStat.setBusiChargeAmount(Bytes.toBigDecimal(keyValue.getValue()));
				}
			}
		}
		return userStat;
	}
}
