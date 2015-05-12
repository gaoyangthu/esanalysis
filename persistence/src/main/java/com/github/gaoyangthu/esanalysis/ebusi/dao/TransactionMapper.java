package com.github.gaoyangthu.esanalysis.ebusi.dao;

import com.github.gaoyangthu.esanalysis.ebusi.bean.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionMapper {
	int deleteByPrimaryKey(String transactionId);

	int insert(Transaction record);

	int insertSelective(Transaction record);

	Transaction selectByPrimaryKey(String transactionId);

	int updateByPrimaryKeySelective(Transaction record);

	int updateByPrimaryKey(Transaction record);

	Transaction findOrderTransaction(String sourceId);

	List<Transaction> findByDate(Map<String, Object> params);
}