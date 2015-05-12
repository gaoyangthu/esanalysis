package com.github.gaoyangthu.esanalysis.hbase.service.impl;

import com.github.gaoyangthu.core.hbase.HbaseTemplate;
import com.github.gaoyangthu.core.hbase.HbaseTemplateFactory;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserStat;
import com.github.gaoyangthu.esanalysis.hbase.constant.UserStatConst;
import com.github.gaoyangthu.esanalysis.hbase.dao.UserStatMapper;
import com.github.gaoyangthu.esanalysis.hbase.service.UserStatService;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 下午2:20
 */
@Deprecated
public class UserStatServiceImpl implements UserStatService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserStatServiceImpl.class);

	private HbaseTemplate hbaseTemplate = HbaseTemplateFactory.getHbaseTemplate();

	/**
	 * 根据bdUserUuid查询UserStat
	 *
	 * @param bdUserUuid
	 * @return
	 */
	@Override
	public UserStat getUserStat(String bdUserUuid) {
		if (StringUtils.isBlank(bdUserUuid)) {
			return null;
		}
		UserStat userStat = hbaseTemplate.get(UserStatConst.USER_STAT,
				bdUserUuid, new UserStatMapper());
		return userStat;
	}

	@Override
	public UserStat getOrCreateUserStat(String bdUserUuid) {
		UserStat userStat = getUserStat(bdUserUuid);
		if (userStat == null) {
			userStat = new UserStat(bdUserUuid);
		}
		return userStat;
	}

	@Override
	public List<UserStat> findAll() {
		Scan scan = new Scan();
		List<UserStat> userStats = hbaseTemplate.find(UserStatConst.USER_STAT, scan, new UserStatMapper());
		return userStats;
	}

	public boolean updateUserStat(UserStat userStat) {
		boolean flag = false;
		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(userStat.getBdUserUuid()));

			// statistic:total_order_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.TOTAL_ORDER_COUNT),
					Bytes.toBytes(userStat.getTotalOrderCount()));
			// statistic:total_order_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.TOTAL_ORDER_AMOUNT),
					Bytes.toBytes(userStat.getTotalOrderAmount()));
			// statistic:payed_order_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.PAYED_ORDER_COUNT),
					Bytes.toBytes(userStat.getPayOrderCount()));
			// statistic:payed_order_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.PAYED_ORDER_AMOUNT),
					Bytes.toBytes(userStat.getPayOrderAmount()));
			// statistic:order_trade_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.ORDER_TRADE_COUNT),
					Bytes.toBytes(userStat.getOrderTradeCount()));
			// statistic:order_cash_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.ORDER_CASH_AMOUNT),
					Bytes.toBytes(userStat.getOrderCashAmount()));
			// statistic:cash_charge_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CASH_CHARGE_COUNT),
					Bytes.toBytes(userStat.getCashChargeCount()));
			// statistic:cash_charge_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CASH_CHARGE_AMOUNT),
					Bytes.toBytes(userStat.getCashChargeAmount()));
			// statistic:credit_charge_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CREDIT_CHARGE_COUNT),
					Bytes.toBytes(userStat.getCreditChargeCount()));
			// statistic:credit_charge_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CREDIT_CHARGE_AMOUNT),
					Bytes.toBytes(userStat.getCreditChargeAmount()));
			// statistic:payout_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.PAYOUT_COUNT),
					Bytes.toBytes(userStat.getPayoutCount()));
			// statistic:payout_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.PAYOUT_AMOUNT),
					Bytes.toBytes(userStat.getPayoutAmount()));
			// statistic:withdrawal_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.WITHDRAWAL_COUNT),
					Bytes.toBytes(userStat.getWithdrawalCount()));
			// statistic:withdrawal_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.WITHDRAWAL_AMOUNT),
					Bytes.toBytes(userStat.getWithdrawalAmount()));
			// statistic:reject_withdrawal_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REJECT_WITHDRAWAL_COUNT),
					Bytes.toBytes(userStat.getRejectWithdrawalCount()));
			// statistic:reject_withdrawal_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REJECT_WITHDRAWAL_AMOUNT),
					Bytes.toBytes(userStat.getRejectWithdrawalAmount()));
			// statistic:refund_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REFUND_COUNT),
					Bytes.toBytes(userStat.getRefundCount()));
			// statistic:refund_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REFUND_AMOUNT),
					Bytes.toBytes(userStat.getRefundAmount()));
			// statistic:busi_charge_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.BUSI_CHARGE_COUNT),
					Bytes.toBytes(userStat.getBusiChargeCount()));
			// statistic:busi_charge_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.BUSI_CHARGE_AMOUNT),
					Bytes.toBytes(userStat.getBusiChargeAmount()));

			userTagTable.put(put);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("Update order error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}
		return flag;
	}

	/**
	 * 更新用户的订单统计
	 *
	 * @param bdUserUuid
	 * @param totalPrice
	 * @param isPaid
	 * @return
	 */
	@Override
	public UserStat updateOrder(String bdUserUuid, BigDecimal totalPrice,
			boolean isPaid) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setTotalOrderCount(userStat.getTotalOrderCount() + 1);
			userStat.setTotalOrderAmount(userStat.getTotalOrderAmount().add(
					totalPrice));
			// statistic:total_order_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.TOTAL_ORDER_COUNT),
					Bytes.toBytes(userStat.getTotalOrderCount()));
			// statistic:total_order_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.TOTAL_ORDER_AMOUNT),
					Bytes.toBytes(userStat.getTotalOrderAmount()));

			if (isPaid) {
				userStat.setPayOrderCount(userStat.getPayOrderCount() + 1);
				userStat.setPayOrderAmount(userStat.getPayOrderAmount().add(
						totalPrice));
				// statistic:payed_order_count
				put.add(Bytes.toBytes(UserStatConst.STATISTIC),
						Bytes.toBytes(UserStatConst.PAYED_ORDER_COUNT),
						Bytes.toBytes(userStat.getPayOrderCount()));
				// statistic:payed_order_amount
				put.add(Bytes.toBytes(UserStatConst.STATISTIC),
						Bytes.toBytes(UserStatConst.PAYED_ORDER_AMOUNT),
						Bytes.toBytes(userStat.getPayOrderAmount()));
			}

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update order error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的订单支付统计
	 *
	 * @param bdUserUuid
	 * @param cashAmount
	 * @return
	 */
	@Override
	public UserStat updateOrderPay(String bdUserUuid, BigDecimal cashAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setOrderTradeCount(userStat.getOrderTradeCount() + 1);
			userStat.setOrderCashAmount(userStat.getOrderCashAmount().add(
					cashAmount));
			// statistic:order_trade_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.ORDER_TRADE_COUNT),
					Bytes.toBytes(userStat.getOrderTradeCount()));
			// statistic:order_cash_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.ORDER_CASH_AMOUNT),
					Bytes.toBytes(userStat.getOrderCashAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update order pay error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的现金充值统计
	 *
	 * @param bdUserUuid
	 * @param cashAmount
	 * @return
	 */
	@Override
	public UserStat updateCashCharge(String bdUserUuid, BigDecimal cashAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setCashChargeCount(userStat.getCashChargeCount() + 1);
			userStat.setCashChargeAmount(userStat.getCashChargeAmount().add(
					cashAmount));
			// statistic:cash_charge_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CASH_CHARGE_COUNT),
					Bytes.toBytes(userStat.getCashChargeCount()));
			// statistic:cash_charge_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CASH_CHARGE_AMOUNT),
					Bytes.toBytes(userStat.getCashChargeAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update cash charge error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的代金券充值统计
	 *
	 * @param bdUserUuid
	 * @param CreditAmount
	 * @return
	 */
	@Override
	public UserStat updateCreditCharge(String bdUserUuid,
			BigDecimal CreditAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setCreditChargeCount(userStat.getCreditChargeCount() + 1);
			userStat.setCreditChargeAmount(userStat.getCreditChargeAmount().add(
					CreditAmount));
			// statistic:credit_charge_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CREDIT_CHARGE_COUNT),
					Bytes.toBytes(userStat.getCreditChargeCount()));
			// statistic:credit_charge_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.CREDIT_CHARGE_AMOUNT),
					Bytes.toBytes(userStat.getCreditChargeAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update credit charge error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的实时扣费统计
	 *
	 * @param bdUserUuid
	 * @param payoutAmount
	 * @return
	 */
	@Override
	public UserStat updatePayout(String bdUserUuid, BigDecimal payoutAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setPayoutCount(userStat.getPayoutCount() + 1);
			userStat.setPayoutAmount(userStat.getPayoutAmount().add(
					payoutAmount));
			// statistic:payout_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.PAYOUT_COUNT),
					Bytes.toBytes(userStat.getPayoutCount()));
			// statistic:payout_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.PAYOUT_AMOUNT),
					Bytes.toBytes(userStat.getPayoutAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update payout error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的提现统计
	 *
	 * @param bdUserUuid
	 * @param withdrawalAmount
	 * @return
	 */
	@Override
	public UserStat updateWithdrawal(String bdUserUuid,
			BigDecimal withdrawalAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setWithdrawalCount(userStat.getWithdrawalCount() + 1);
			userStat.setWithdrawalAmount(userStat.getWithdrawalAmount().add(
					withdrawalAmount));
			// statistic:withdrawal_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.WITHDRAWAL_COUNT),
					Bytes.toBytes(userStat.getWithdrawalCount()));
			// statistic:withdrawal_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.WITHDRAWAL_AMOUNT),
					Bytes.toBytes(userStat.getWithdrawalAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update withdrawal error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的拒绝提现统计
	 *
	 * @param bdUserUuid
	 * @param rejectWithdrawalAmount
	 * @return
	 */
	@Override
	public UserStat updateRejectWithdrawal(String bdUserUuid,
			BigDecimal rejectWithdrawalAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setRejectWithdrawalCount(userStat.getRejectWithdrawalCount() + 1);
			userStat.setRejectWithdrawalAmount(userStat.getRejectWithdrawalAmount().add(
					rejectWithdrawalAmount));
			// statistic:reject_withdrawal_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REJECT_WITHDRAWAL_COUNT),
					Bytes.toBytes(userStat.getRejectWithdrawalCount()));
			// statistic:reject_withdrawal_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REJECT_WITHDRAWAL_AMOUNT),
					Bytes.toBytes(userStat.getRejectWithdrawalAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update reject withdrawal error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的退订统计
	 *
	 * @param bdUserUuid
	 * @param refundAmount
	 * @return
	 */
	@Override
	public UserStat updateRefund(String bdUserUuid, BigDecimal refundAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setRefundCount(userStat.getRefundCount() + 1);
			userStat.setRefundAmount(userStat.getRefundAmount().add(
					refundAmount));
			// statistic:refund_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REFUND_COUNT),
					Bytes.toBytes(userStat.getRefundCount()));
			// statistic:refund_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.REFUND_AMOUNT),
					Bytes.toBytes(userStat.getRefundAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update payout error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 * 更新用户的对公充值统计
	 *
	 * @param bdUserUuid
	 * @param busiChargeAmount
	 * @return
	 */
	@Override
	public UserStat updateBusiCharge(String bdUserUuid,
			BigDecimal busiChargeAmount) {
		UserStat userStat = getOrCreateUserStat(bdUserUuid);

		HTableInterface userTagTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
			Put put = new Put(Bytes.toBytes(bdUserUuid));

			userStat.setBusiChargeCount(userStat.getBusiChargeCount() + 1);
			userStat.setBusiChargeAmount(userStat.getBusiChargeAmount().add(
					busiChargeAmount));
			// statistic:busi_charge_count
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.BUSI_CHARGE_COUNT),
					Bytes.toBytes(userStat.getBusiChargeCount()));
			// statistic:busi_charge_amount
			put.add(Bytes.toBytes(UserStatConst.STATISTIC),
					Bytes.toBytes(UserStatConst.BUSI_CHARGE_AMOUNT),
					Bytes.toBytes(userStat.getBusiChargeAmount()));

			userTagTable.put(put);
		} catch (IOException e) {
			LOGGER.error("Update busi charge error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT, userTagTable);
		}

		return userStat;
	}

	/**
	 *
	 * @param oldId
	 * @param newId
	 * @return
	 */
	@Override
	public boolean changeBdUser(String oldId, String newId) {
		boolean flag = false;

		UserStat oldStat = getUserStat(oldId);
		if (oldStat == null) {
			// 不存在之前的用户统计,不用更新
			return true;
		}

		LOGGER.debug("Exchange user stat. oldId={}, newId={}", oldId, newId);

		UserStat newStat = getOrCreateUserStat(newId);
		newStat.setTotalOrderCount(newStat.getTotalOrderCount()
				+ oldStat.getTotalOrderCount());
		newStat.setTotalOrderAmount(newStat.getTotalOrderAmount().add(
				oldStat.getTotalOrderAmount()));
		newStat.setPayOrderCount(newStat.getPayOrderCount()
				+ oldStat.getPayOrderCount());
		newStat.setPayOrderAmount(newStat.getPayOrderAmount().add(
				oldStat.getPayOrderAmount()));
		newStat.setOrderTradeCount(newStat.getOrderTradeCount()
				+ oldStat.getOrderTradeCount());
		newStat.setOrderCashAmount(newStat.getOrderCashAmount().add(
				oldStat.getOrderCashAmount()));
		newStat.setCashChargeCount(newStat.getCashChargeCount()
				+ oldStat.getCashChargeCount());
		newStat.setCashChargeAmount(newStat.getCashChargeAmount().add(
				oldStat.getCashChargeAmount()));
		newStat.setCreditChargeCount(newStat.getCreditChargeCount()
				+ oldStat.getCreditChargeCount());
		newStat.setCreditChargeAmount(newStat.getCreditChargeAmount().add(
				oldStat.getCreditChargeAmount()));
		newStat.setPayoutCount(newStat.getPayoutCount()
				+ oldStat.getPayoutCount());
		newStat.setPayoutAmount(newStat.getPayoutAmount().add(
				oldStat.getPayoutAmount()));
		newStat.setWithdrawalCount(newStat.getWithdrawalCount()
				+ oldStat.getWithdrawalCount());
		newStat.setWithdrawalAmount(newStat.getWithdrawalAmount().add(
				oldStat.getWithdrawalAmount()));
		newStat.setRejectWithdrawalCount(newStat.getRejectWithdrawalCount()
				+ oldStat.getRejectWithdrawalCount());
		newStat.setRejectWithdrawalAmount(newStat.getRejectWithdrawalAmount().add(
				oldStat.getRejectWithdrawalAmount()));
		newStat.setRefundCount(newStat.getRefundCount()
				+ oldStat.getRefundCount());
		newStat.setRefundAmount(newStat.getRefundAmount().add(
				oldStat.getRefundAmount()));
		newStat.setBusiChargeCount(newStat.getBusiChargeCount()
				+ oldStat.getBusiChargeCount());
		newStat.setBusiChargeAmount(newStat.getBusiChargeAmount().add(
				oldStat.getBusiChargeAmount()));

		flag = updateUserStat(newStat);

		// 删除oldId的统计数据
		HTableInterface userStatTable = hbaseTemplate.getTable(UserStatConst.USER_STAT);
		try {
				Delete delete = new Delete(
						Bytes.toBytes(oldId));

			userStatTable.delete(delete);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("Delete channel tag error", e);
		} finally {
			hbaseTemplate.releaseTable(UserStatConst.USER_STAT,
					userStatTable);
		}

		return flag;
	}
}
