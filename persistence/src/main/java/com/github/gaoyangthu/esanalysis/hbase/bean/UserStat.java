package com.github.gaoyangthu.esanalysis.hbase.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 上午11:18
 */
public class UserStat implements Serializable {

	private String bdUserUuid;

	private Integer totalOrderCount = 0;

	private BigDecimal totalOrderAmount = BigDecimal.ZERO;

	private Integer payOrderCount = 0;

	private BigDecimal payOrderAmount = BigDecimal.ZERO;

	private Integer orderTradeCount = 0;

	private BigDecimal orderCashAmount = BigDecimal.ZERO;

	private Integer cashChargeCount = 0;

	private BigDecimal cashChargeAmount = BigDecimal.ZERO;

	private Integer creditChargeCount = 0;

	private BigDecimal creditChargeAmount = BigDecimal.ZERO;

	private Integer payoutCount = 0;

	private BigDecimal payoutAmount = BigDecimal.ZERO;

	private Integer withdrawalCount = 0;

	private BigDecimal withdrawalAmount = BigDecimal.ZERO;

	private Integer rejectWithdrawalCount = 0;

	private BigDecimal rejectWithdrawalAmount = BigDecimal.ZERO;

	private Integer refundCount = 0;

	private BigDecimal refundAmount = BigDecimal.ZERO;

	private Integer busiChargeCount = 0;

	private BigDecimal busiChargeAmount = BigDecimal.ZERO;

	public UserStat() {
	}

	public UserStat(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public String getBdUserUuid() {
		return bdUserUuid;
	}

	public void setBdUserUuid(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public BigDecimal getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public Integer getPayOrderCount() {
		return payOrderCount;
	}

	public void setPayOrderCount(Integer payOrderCount) {
		this.payOrderCount = payOrderCount;
	}

	public BigDecimal getPayOrderAmount() {
		return payOrderAmount;
	}

	public void setPayOrderAmount(BigDecimal payOrderAmount) {
		this.payOrderAmount = payOrderAmount;
	}

	public Integer getOrderTradeCount() {
		return orderTradeCount;
	}

	public void setOrderTradeCount(Integer orderTradeCount) {
		this.orderTradeCount = orderTradeCount;
	}

	public BigDecimal getOrderCashAmount() {
		return orderCashAmount;
	}

	public void setOrderCashAmount(BigDecimal orderCashAmount) {
		this.orderCashAmount = orderCashAmount;
	}

	public Integer getCashChargeCount() {
		return cashChargeCount;
	}

	public void setCashChargeCount(Integer cashChargeCount) {
		this.cashChargeCount = cashChargeCount;
	}

	public BigDecimal getCashChargeAmount() {
		return cashChargeAmount;
	}

	public void setCashChargeAmount(BigDecimal cashChargeAmount) {
		this.cashChargeAmount = cashChargeAmount;
	}

	public Integer getCreditChargeCount() {
		return creditChargeCount;
	}

	public void setCreditChargeCount(Integer creditChargeCount) {
		this.creditChargeCount = creditChargeCount;
	}

	public BigDecimal getCreditChargeAmount() {
		return creditChargeAmount;
	}

	public void setCreditChargeAmount(BigDecimal creditChargeAmount) {
		this.creditChargeAmount = creditChargeAmount;
	}

	public Integer getPayoutCount() {
		return payoutCount;
	}

	public void setPayoutCount(Integer payOutCount) {
		this.payoutCount = payOutCount;
	}

	public BigDecimal getPayoutAmount() {
		return payoutAmount;
	}

	public void setPayoutAmount(BigDecimal payOutAmount) {
		this.payoutAmount = payOutAmount;
	}

	public Integer getWithdrawalCount() {
		return withdrawalCount;
	}

	public void setWithdrawalCount(Integer withdrawalCount) {
		this.withdrawalCount = withdrawalCount;
	}

	public BigDecimal getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public Integer getRejectWithdrawalCount() {
		return rejectWithdrawalCount;
	}

	public void setRejectWithdrawalCount(Integer rejectWithdrawalCount) {
		this.rejectWithdrawalCount = rejectWithdrawalCount;
	}

	public BigDecimal getRejectWithdrawalAmount() {
		return rejectWithdrawalAmount;
	}

	public void setRejectWithdrawalAmount(BigDecimal rejectWithdrawalAmount) {
		this.rejectWithdrawalAmount = rejectWithdrawalAmount;
	}

	public Integer getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getBusiChargeCount() {
		return busiChargeCount;
	}

	public void setBusiChargeCount(Integer busiChargeCount) {
		this.busiChargeCount = busiChargeCount;
	}

	public BigDecimal getBusiChargeAmount() {
		return busiChargeAmount;
	}

	public void setBusiChargeAmount(BigDecimal busiChargeAmount) {
		this.busiChargeAmount = busiChargeAmount;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserStat{");
		sb.append("bdUserUuid='").append(bdUserUuid).append('\'');
		sb.append(", totalOrderCount=").append(totalOrderCount);
		sb.append(", totalOrderAmount=").append(totalOrderAmount);
		sb.append(", payOrderCount=").append(payOrderCount);
		sb.append(", payOrderAmount=").append(payOrderAmount);
		sb.append(", orderTradeCount=").append(orderTradeCount);
		sb.append(", orderCashAmount=").append(orderCashAmount);
		sb.append(", cashChargeCount=").append(cashChargeCount);
		sb.append(", cashChargeAmount=").append(cashChargeAmount);
		sb.append(", creditChargeCount=").append(creditChargeCount);
		sb.append(", creditChargeAmount=").append(creditChargeAmount);
		sb.append(", payoutCount=").append(payoutCount);
		sb.append(", payoutAmount=").append(payoutAmount);
		sb.append(", withdrawalCount=").append(withdrawalCount);
		sb.append(", withdrawalAmount=").append(withdrawalAmount);
		sb.append(", rejectWithdrawalCount=").append(rejectWithdrawalCount);
		sb.append(", rejectWithdrawalAmount=").append(rejectWithdrawalAmount);
		sb.append(", refundCount=").append(refundCount);
		sb.append(", refundAmount=").append(refundAmount);
		sb.append(", busiChargeCount=").append(busiChargeCount);
		sb.append(", busiChargeAmount=").append(busiChargeAmount);
		sb.append('}');
		return sb.toString();
	}
}
