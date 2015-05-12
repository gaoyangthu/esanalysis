package com.github.gaoyangthu.esanalysis.hbase.constant;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 上午10:42
 */
public class UserStatConst {

	/**
	 * 表名 user_stat
	 */
	public static final String USER_STAT = "user_stat";

	/**
	 * 行键 bd_user_uuid
	 */
	public static final String BD_USER_UUID = "bd_user_uuid";

	/**
	 * 列族 statistic
	 */
	public static final String STATISTIC = "statistic";

	/**
	 * 列 statistic:total_order_count 总订单数
	 */
	public static final String TOTAL_ORDER_COUNT = "total_order_count";

	/**
	 * 列 statistic:total_order_amount 总订单金额
	 */
	public static final String TOTAL_ORDER_AMOUNT = "total_order_amount";

	/**
	 * 列 statistic:payed_order_count 支付订单数
	 */
	public static final String PAYED_ORDER_COUNT = "payed_order_count";

	/**
	 * 列 statistic:payed_order_amount 支付订单金额
	 */
	public static final String PAYED_ORDER_AMOUNT = "payed_order_amount";

	/**
	 * 列 statistic:order_trade_count 订单交易数
	 */
	public static final String ORDER_TRADE_COUNT = "order_trade_count";

	/**
	 * 列 statistic:order_cash_amount 订单现金金额
	 */
	public static final String ORDER_CASH_AMOUNT = "order_cash_amount";

	/**
	 * 列 statistic:cash_charge_count 现金充值数
	 */
	public static final String CASH_CHARGE_COUNT = "cash_charge_count";

	/**
	 * 列 statistic:cash_charge_amount 现金充值金额
	 */
	public static final String CASH_CHARGE_AMOUNT = "cash_charge_amount";

	/**
	 * 列 statistic:credit_charge_count 代金券充值数
	 */
	public static final String CREDIT_CHARGE_COUNT = "credit_charge_count";

	/**
	 * 列 statistic:credit_charge_amount 代金券充值金额
	 */
	public static final String CREDIT_CHARGE_AMOUNT = "credit_charge_amount";

	/**
	 * 列 statistic:payout_count 实时扣费数
	 */
	public static final String PAYOUT_COUNT = "payout_count";

	/**
	 * 列 statistic:payout_amount 实时扣费金额
	 */
	public static final String PAYOUT_AMOUNT = "payout_amount";

	/**
	 * 列 statistic:withdrawal_count 提现数
	 */
	public static final String WITHDRAWAL_COUNT = "withdrawal_count";

	/**
	 * 列 statistic:withdrawal_amount 提现金额
	 */
	public static final String WITHDRAWAL_AMOUNT = "withdrawal_amount";

	/**
	 * 列 statistic:reject_withdrawal_count 拒绝提现数
	 */
	public static final String REJECT_WITHDRAWAL_COUNT = "reject_withdrawal_count";

	/**
	 * 列 statistic:reject_withdrawal_amount 拒绝提现金额
	 */
	public static final String REJECT_WITHDRAWAL_AMOUNT = "reject_withdrawal_amount";

	/**
	 * 列 statistic:refund_count 退订数
	 */
	public static final String REFUND_COUNT = "refund_count";

	/**
	 * 列 statistic:refund_amount 退订金额
	 */
	public static final String REFUND_AMOUNT = "refund_amount";

	/**
	 * 列 statistic:busi_charge_count 对公充值数
	 */
	public static final String BUSI_CHARGE_COUNT = "busi_charge_count";

	/**
	 * 列 statistic:busi_charge_amount 对公充值金额
	 */
	public static final String BUSI_CHARGE_AMOUNT = "busi_charge_amount";

}
