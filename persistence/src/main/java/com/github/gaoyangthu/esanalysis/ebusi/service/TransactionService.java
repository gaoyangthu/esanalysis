package com.github.gaoyangthu.esanalysis.ebusi.service;

import java.util.Date;
import java.util.List;

import com.github.gaoyangthu.esanalysis.ebusi.bean.Transaction;

/**
 * trade表接口
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:41
 */
public interface TransactionService {

	/**
	 * 根据主订单ID查询交易
	 *
	 * @param sourceId 主订单ID
	 * @return
	 */
	Transaction findOrderTransaction(String sourceId);

	/**
	 * 根据时间查询交易
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 交易列表
	 */
	List<Transaction> findByDate(Date beginDate, Date endDate);

}
