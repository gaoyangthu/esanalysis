package com.github.gaoyangthu.esanalysis.ebusi.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String transactionId;

    private Integer transactionType;

    private Integer status;

    private Date transactionTime;

    private String fkUserId;

    private String fkAccountId;

    private String fkSourceId;

    private BigDecimal amountcash;

    private BigDecimal amountcredit;

    private BigDecimal totalpaymentamount;

    private BigDecimal lastamountcash;

    private BigDecimal lastamountcredit;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId == null ? null : fkUserId.trim();
    }

    public String getFkAccountId() {
        return fkAccountId;
    }

    public void setFkAccountId(String fkAccountId) {
        this.fkAccountId = fkAccountId == null ? null : fkAccountId.trim();
    }

    public String getFkSourceId() {
        return fkSourceId;
    }

    public void setFkSourceId(String fkSourceId) {
        this.fkSourceId = fkSourceId == null ? null : fkSourceId.trim();
    }

    public BigDecimal getAmountcash() {
        return amountcash;
    }

    public void setAmountcash(BigDecimal amountcash) {
        this.amountcash = amountcash;
    }

    public BigDecimal getAmountcredit() {
        return amountcredit;
    }

    public void setAmountcredit(BigDecimal amountcredit) {
        this.amountcredit = amountcredit;
    }

    public BigDecimal getTotalpaymentamount() {
        return totalpaymentamount;
    }

    public void setTotalpaymentamount(BigDecimal totalpaymentamount) {
        this.totalpaymentamount = totalpaymentamount;
    }

    public BigDecimal getLastamountcash() {
        return lastamountcash;
    }

    public void setLastamountcash(BigDecimal lastamountcash) {
        this.lastamountcash = lastamountcash;
    }

    public BigDecimal getLastamountcredit() {
        return lastamountcredit;
    }

    public void setLastamountcredit(BigDecimal lastamountcredit) {
        this.lastamountcredit = lastamountcredit;
    }
}