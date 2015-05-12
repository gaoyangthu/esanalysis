package com.github.gaoyangthu.esanalysis.ebusi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gaoyangthu.esanalysis.ebusi.bean.Transaction;
import com.github.gaoyangthu.esanalysis.ebusi.dao.TransactionMapper;
import com.github.gaoyangthu.esanalysis.ebusi.service.TransactionService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gaoyangthu.core.mysql.EbusiUtil;

/**
 * master_order表实现
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:42
 */
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * 根据主订单ID查询交易
	 *
	 * @param sourceId 主订单ID
	 * @return
	 */
	@Override
	public Transaction findOrderTransaction(String sourceId) {
		Transaction orderTransaction = null;

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			TransactionMapper transactionMapper = sqlSession.getMapper(TransactionMapper.class);
			orderTransaction = transactionMapper.findOrderTransaction(sourceId);
		} finally {
			sqlSession.close();
		}

		return orderTransaction;
	}

	/**
	 * 根据时间查询交易
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 交易列表
	 */
	@Override
	public List<Transaction> findByDate(Date beginDate, Date endDate) {
		List<Transaction> transactions = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			TransactionMapper transactionMapper = sqlSession.getMapper(TransactionMapper.class);
			transactions = transactionMapper.findByDate(params);
		} finally {
			sqlSession.close();
		}

		return transactions;
	}
}
