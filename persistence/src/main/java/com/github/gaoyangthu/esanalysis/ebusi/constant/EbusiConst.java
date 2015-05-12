package com.github.gaoyangthu.esanalysis.ebusi.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 下午3:45
 */
public class EbusiConst {

	public enum OrderStatus {
		// 错误
		ERROR(0),

		WAIT_PAY(1),

		PAIDED(2),

		FINISHED(3),

		CANCELD(4),

		FAILED(5),

		SUBMITTED(6),

		PAYING(7),

		// 待审核
		PENDING(8),

		// 审核通过
		AUDIT_PASSED(9),

		// 审核未通过
		AUDIT_NOT_PASSED(10),

		// 作废
		ABANDON(11),

		// 退订中
		RENFUNDING(12),

		WORK_ORDER_FINISHED(13),

		// 开通中
		OPENING(14),

		DELETED(9999);

		private int status;

		private static final Map<Integer, OrderStatus> STATUSENUMS = new HashMap<Integer, OrderStatus>();

		static {
			for (OrderStatus userOrderStatus : values()) {
				STATUSENUMS.put(userOrderStatus.getStatus(), userOrderStatus);
			}
		}

		OrderStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}

		public static OrderStatus fromStatus(Integer status) {
			return STATUSENUMS.get(status);
		}
	}

	public enum TransactionType {
		/**预付费订单*/
		ORDER(0),
		/**翼支付现金充值*/
		CASH_CHARGE(1),
		/**礼品券充值*/
		CREDIT_CHARGE(2),
		/** 实时支付*/
		REALTIME_PAYOUT(3),
		/** 提现冻结*/
		WITHDRAWAL(4),
		/** 拒绝提现*/
		REJECT_WITHDRAWAL(5),
		/** 退订*/
		REFUND(6),
		/**对公充值*/
		BUSINESS_CHARGE(7),
		/**提现审批通过*/
		AUDIT_WITHDRAWAL(8),
		/**待打款账本天翼支付提现*/
		OUTCOMING_WITHDRAWAL(9),
		/**待打款账本线下提现*/
		OUTCOMING_BUSINESS(10),
		/**后付费充值*/
		POSTPAYCASH_CHARGE(11),
		/**直销用户后付费订单*/
		POSTPAID_BALANCE(12),
		/**后付费代金卷充值*/
		POSTPAID_CREDIT(13),
		/**后付费销账*/
		POSTPAID_OFFS(14);

		private int type;

		private static final Map<Integer, TransactionType> TYPEENUMS = new HashMap<Integer, TransactionType>();

		static {
			for (TransactionType transactionType : values()) {
				TYPEENUMS.put(transactionType.getType(), transactionType);
			}
		}

		TransactionType(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}

		public static TransactionType fromType(Integer type) {
			return TYPEENUMS.get(type);
		}
	}

	public enum TradeType {
		ORDER(0),
		CASH_CHARGE(1),
		CREDIT_CHARGE(2),
		REALTIME_PAYOUT(3),
		WITHDRAWAL(4),
		REJECT_WITHDRAWAL(5),
		REFUND(6),
		BUSINESS_CHARGE(7);

		private int type;

		private static final Map<Integer, TradeType> TYPEENUMS = new HashMap<Integer, TradeType>();

		static {
			for (TradeType tradeType : values()) {
				TYPEENUMS.put(tradeType.getType(), tradeType);
			}
		}

		TradeType(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}

		public static TradeType fromType(Integer type) {
			return TYPEENUMS.get(type);
		}
	}
}
