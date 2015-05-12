package com.github.gaoyangthu.esanalysis.ebusi.impl;

import com.github.gaoyangthu.esanalysis.ebusi.bean.MasterOrder;
import com.github.gaoyangthu.esanalysis.ebusi.constant.EbusiConst;
import com.github.gaoyangthu.esanalysis.ebusi.OrderAnalyser;
import com.github.gaoyangthu.esanalysis.ebusi.service.OrderService;
import com.github.gaoyangthu.esanalysis.ebusi.service.impl.OrderServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserStat;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserStatService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserIdServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserStatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:35
 */
@Deprecated
public class OrderAnalyserImpl implements OrderAnalyser {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAnalyserImpl.class);

	private OrderService orderService;

	private UserIdService userIdService;

	private UserStatService userStatService;

	public OrderAnalyserImpl() {
		orderService = new OrderServiceImpl();

		userIdService = new UserIdServiceImpl();
		userStatService = new UserStatServiceImpl();
	}

	@Override
	public boolean analyseOrder(Date beginDate, Date endDate) {
		boolean flag = true;

		List<MasterOrder> masterOrders = orderService.findByDate(beginDate, endDate);
		if(masterOrders == null) {
			return false;
		}

		for (MasterOrder masterOrder : masterOrders) {
			if (masterOrder != null) {
				boolean f = processOrder(masterOrder);
				if (!f) {
					LOGGER.error("Process order error. masterOrderId={}",
							masterOrder.getMasterOrderId());
				}
			}
		}

		return flag;
	}

	private boolean processOrder(MasterOrder masterOrder) {
		String accountId = masterOrder.getFkAccountId();
		BigDecimal totalPrice = masterOrder.getTotalPrice();
		Integer status = masterOrder.getStatus();

		String bdUserUuid = userIdService.getBdUserUuid(null, null, accountId);
		boolean isPaid = (status == EbusiConst.OrderStatus.PAIDED.getStatus() || status == EbusiConst.OrderStatus.FINISHED.getStatus());

		UserStat userStat = userStatService.updateOrder(bdUserUuid, totalPrice,
				isPaid);

		return (userStat != null);
	}
}
