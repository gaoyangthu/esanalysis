package com.github.gaoyangthu.esanalysis.report.bean;

import java.math.BigDecimal;
import java.util.Date;

public class EbusiDailyReport extends EbusiDailyReportKey {
	private String email;

	private String userName;

	private String userChannelTag;

	private Date firstChannelVisit;

	private Integer pv;

	private Integer registerCount = 0;

	private Integer totalOrderCount = 0;

	private BigDecimal totalOrderAmount = BigDecimal.ZERO;

	private Integer payedOrderCount = 0;

	private BigDecimal payedOrderAmount = BigDecimal.ZERO;

	private Integer orderTradeCount = 0;

	private BigDecimal orderCashAmount = BigDecimal.ZERO;

	private BigDecimal orderCreditAmount = BigDecimal.ZERO;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserChannelTag() {
		return userChannelTag;
	}

	public void setUserChannelTag(String userChannelTag) {
		this.userChannelTag = userChannelTag == null ? null
				: userChannelTag.trim();
	}

	public Date getFirstChannelVisit() {
		return firstChannelVisit;
	}

	public void setFirstChannelVisit(Date firstChannelVisit) {
		this.firstChannelVisit = firstChannelVisit;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
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

	public Integer getPayedOrderCount() {
		return payedOrderCount;
	}

	public void setPayedOrderCount(Integer payedOrderCount) {
		this.payedOrderCount = payedOrderCount;
	}

	public BigDecimal getPayedOrderAmount() {
		return payedOrderAmount;
	}

	public void setPayedOrderAmount(BigDecimal payedOrderAmount) {
		this.payedOrderAmount = payedOrderAmount;
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

	public BigDecimal getOrderCreditAmount() {
		return orderCreditAmount;
	}

	public void setOrderCreditAmount(BigDecimal orderCreditAmount) {
		this.orderCreditAmount = orderCreditAmount;
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

	public void setPayoutCount(Integer payoutCount) {
		this.payoutCount = payoutCount;
	}

	public BigDecimal getPayoutAmount() {
		return payoutAmount;
	}

	public void setPayoutAmount(BigDecimal payoutAmount) {
		this.payoutAmount = payoutAmount;
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
		final StringBuilder sb = new StringBuilder("EbusiDailyReport{");
		sb.append("bdUserUuid='").append(getBdUserUuid()).append('\'');
		sb.append(", date=").append(getDate());
		sb.append(", email='").append(email).append('\'');
		sb.append(", userName='").append(userName).append('\'');
		sb.append(", userChannelTag='").append(userChannelTag).append('\'');
		sb.append(", firstChannelVisit=").append(firstChannelVisit);
		sb.append(", pv=").append(pv);
		sb.append(", registerCount=").append(registerCount);
		sb.append(", totalOrderCount=").append(totalOrderCount);
		sb.append(", totalOrderAmount=").append(totalOrderAmount);
		sb.append(", payedOrderCount=").append(payedOrderCount);
		sb.append(", payedOrderAmount=").append(payedOrderAmount);
		sb.append(", orderTradeCount=").append(orderTradeCount);
		sb.append(", orderCashAmount=").append(orderCashAmount);
		sb.append(", orderCreditAmount=").append(orderCreditAmount);
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