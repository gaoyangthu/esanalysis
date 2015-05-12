package com.github.gaoyangthu.esanalysis.ebusi.service.impl;

import com.github.gaoyangthu.core.mysql.EbusiUtil;
import com.github.gaoyangthu.esanalysis.ebusi.bean.MasterOrder;
import com.github.gaoyangthu.esanalysis.ebusi.dao.MasterOrderMapper;
import com.github.gaoyangthu.esanalysis.ebusi.service.OrderService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * master_order表实现
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:35
 */
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * 根据时间查询订单
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 订单列表
	 */
	@Override
	public List<MasterOrder> findByDate(Date beginDate, Date endDate) {
		List<MasterOrder> masterOrders = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			MasterOrderMapper masterOrderMapper = sqlSession.getMapper(MasterOrderMapper.class);
			masterOrders = masterOrderMapper.findByDate(params);
		} finally {
			sqlSession.close();
		}

		return masterOrders;
	}
}
