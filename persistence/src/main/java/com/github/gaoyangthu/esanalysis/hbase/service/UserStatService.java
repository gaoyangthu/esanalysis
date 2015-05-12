package com.github.gaoyangthu.esanalysis.hbase.service;

import com.github.gaoyangthu.esanalysis.hbase.bean.UserStat;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 下午1:53
 */
@Deprecated
public interface UserStatService {

	/**
	 * 根据bdUserUuid查询UserStat
	 *
	 * @param bdUserUuid
	 * @return
	 */
	UserStat getUserStat(String bdUserUuid);

	UserStat getOrCreateUserStat(String bdUserUuid);

	/**
	 * 查询所有用户统计
	 *
	 * @return
	 */
	List<UserStat> findAll();

	/**
	 * 更新用户的订单统计
	 *
	 * @param bdUserUuid
	 * @param totalPrice
	 * @param isPaid
	 * @return
	 */
	UserStat updateOrder(String bdUserUuid, BigDecimal totalPrice,
			boolean isPaid);

	/**
	 * 更新用户的订单支付统计
	 *
	 * @param bdUserUuid
	 * @param cashAmount
	 * @return
	 */
	UserStat updateOrderPay(String bdUserUuid, BigDecimal cashAmount);

	/**
	 * 更新用户的现金充值统计
	 *
	 * @param bdUserUuid
	 * @param cashAmount
	 * @return
	 */
	UserStat updateCashCharge(String bdUserUuid, BigDecimal cashAmount);

	/**
	 * 更新用户的代金券充值统计
	 *
	 * @param bdUserUuid
	 * @param CreditAmount
	 * @return
	 */
	UserStat updateCreditCharge(String bdUserUuid, BigDecimal CreditAmount);

	/**
	 * 更新用户的实时扣费统计
	 *
	 * @param bdUserUuid
	 * @param payoutAmount
	 * @return
	 */
	UserStat updatePayout(String bdUserUuid, BigDecimal payoutAmount);

	/**
	 * 更新用户的提现统计
	 *
	 * @param bdUserUuid
	 * @param withdrawalAmount
	 * @return
	 */
	UserStat updateWithdrawal(String bdUserUuid, BigDecimal withdrawalAmount);

	/**
	 * 更新用户的拒绝提现统计
	 *
	 * @param bdUserUuid
	 * @param rejectWithdrawalAmount
	 * @return
	 */
	UserStat updateRejectWithdrawal(String bdUserUuid,
			BigDecimal rejectWithdrawalAmount);

	/**
	 * 更新用户的退订统计
	 *
	 * @param bdUserUuid
	 * @param refundAmount
	 * @return
	 */
	UserStat updateRefund(String bdUserUuid, BigDecimal refundAmount);

	/**
	 * 更新用户的对公充值统计
	 *
	 * @param bdUserUuid
	 * @param busiChargeAmount
	 * @return
	 */
	UserStat updateBusiCharge(String bdUserUuid, BigDecimal busiChargeAmount);

	/**
	 * 更新用户ID
	 *
	 * @param oldId
	 * @param newId
	 * @return
	 */
	boolean changeBdUser(String oldId, String newId);
}
