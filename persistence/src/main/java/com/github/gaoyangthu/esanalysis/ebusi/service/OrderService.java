package com.github.gaoyangthu.esanalysis.ebusi.service;

import com.github.gaoyangthu.esanalysis.ebusi.bean.MasterOrder;

import java.util.Date;
import java.util.List;

/**
 * master_order表接口
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:34
 */
public interface OrderService {

	/**
	 * 根据时间查询订单
	 * 
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 订单列表
	 */
	List<MasterOrder> findByDate(Date beginDate, Date endDate);

}
