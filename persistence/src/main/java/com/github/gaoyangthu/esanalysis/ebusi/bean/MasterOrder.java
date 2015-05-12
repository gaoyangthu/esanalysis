package com.github.gaoyangthu.esanalysis.ebusi.bean;

import java.math.BigDecimal;
import java.util.Date;

public class MasterOrder {
	private String masterOrderId;

	private String masterOrderNo;

	private String masterOrderOrigId;

	private Integer masterOrderType;

	private String fkContractId;

	private String fkAccountId;

	private String fkUserId;

	private String accountType;

	private BigDecimal totalPrice;

	private BigDecimal customPrice;

	private BigDecimal discountedPrice;

	private String payType;

	private String description;

	private Date createDate;

	private Date updateDate;

	private Integer status;

	private String remark;

	public String getMasterOrderId() {
		return masterOrderId;
	}

	public void setMasterOrderId(String masterOrderId) {
		this.masterOrderId = masterOrderId == null ? null
				: masterOrderId.trim();
	}

	public String getMasterOrderNo() {
		return masterOrderNo;
	}

	public void setMasterOrderNo(String masterOrderNo) {
		this.masterOrderNo = masterOrderNo == null ? null
				: masterOrderNo.trim();
	}

	public String getMasterOrderOrigId() {
		return masterOrderOrigId;
	}

	public void setMasterOrderOrigId(String masterOrderOrigId) {
		this.masterOrderOrigId = masterOrderOrigId == null ? null
				: masterOrderOrigId.trim();
	}

	public Integer getMasterOrderType() {
		return masterOrderType;
	}

	public void setMasterOrderType(Integer masterOrderType) {
		this.masterOrderType = masterOrderType;
	}

	public String getFkContractId() {
		return fkContractId;
	}

	public void setFkContractId(String fkContractId) {
		this.fkContractId = fkContractId == null ? null : fkContractId.trim();
	}

	public String getFkAccountId() {
		return fkAccountId;
	}

	public void setFkAccountId(String fkAccountId) {
		this.fkAccountId = fkAccountId == null ? null : fkAccountId.trim();
	}

	public String getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(String fkUserId) {
		this.fkUserId = fkUserId == null ? null : fkUserId.trim();
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType == null ? null : accountType.trim();
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getCustomPrice() {
		return customPrice;
	}

	public void setCustomPrice(BigDecimal customPrice) {
		this.customPrice = customPrice;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}