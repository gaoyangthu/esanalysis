package com.github.gaoyangthu.esanalysis.ebusi.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.gaoyangthu.esanalysis.ebusi.TradeAnalyser;
import com.github.gaoyangthu.esanalysis.ebusi.bean.Trade;
import com.github.gaoyangthu.esanalysis.ebusi.constant.EbusiConst;
import com.github.gaoyangthu.esanalysis.ebusi.service.TradeService;
import com.github.gaoyangthu.esanalysis.ebusi.service.impl.TradeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gaoyangthu.core.util.JsonUtils;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserStat;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserStatService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserIdServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserStatServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:42
 */
@Deprecated
public class TradeAnalyserImpl implements TradeAnalyser {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAnalyserImpl.class);

	private TradeService tradeService;

	private UserIdService userIdService;

	private UserStatService userStatService;

	public TradeAnalyserImpl() {
		tradeService = new TradeServiceImpl();

		userIdService = new UserIdServiceImpl();
		userStatService = new UserStatServiceImpl();
	}

	@Override
	public boolean analyseTrade(Date beginDate, Date endDate) {
		boolean flag = false;

		List<Trade> trades = tradeService.findByDate(beginDate, endDate);
		if(trades == null) {
			return false;
		}

		for (Trade trade : trades) {
			if (trade != null) {
				boolean f = processTrade(trade);
				if (!f) {
					LOGGER.error("Process trade error. tradeId={}",
							trade.getTradeId());
				}
			}
		}

		return flag;
	}

	private boolean processTrade(Trade trade) {
		boolean flag = false;

		String accountId = trade.getFkAccountId();

		String bdUserUuid = userIdService.getBdUserUuid(null, null, accountId);

		Integer type = trade.getTradeType();
		EbusiConst.TradeType tradeType = EbusiConst.TradeType.fromType(type);

		switch (tradeType) {
		case ORDER:
			flag = processOrderTrade(bdUserUuid, trade.getExtraInfo());
			break;
		case CASH_CHARGE:
			flag = processCashChargeTrade(bdUserUuid, trade.getExtraInfo());
			break;
		case CREDIT_CHARGE:
			flag = processCreditChargeTrade(bdUserUuid, trade.getExtraInfo());
			break;
		case REALTIME_PAYOUT:
			flag = processPayoutTrade(bdUserUuid, trade.getExtraInfo());
			break;
		case WITHDRAWAL:
			flag = processWithdrawalTrade(bdUserUuid, trade.getExtraInfo());
			break;
		case REJECT_WITHDRAWAL:
			flag = processRejectWithdrawalTrade(bdUserUuid,
					trade.getExtraInfo());
			break;
		case REFUND:
			flag = processRefundTrade(bdUserUuid, trade.getExtraInfo());
			break;
		case BUSINESS_CHARGE:
			flag = processBusiChargeTrade(bdUserUuid, trade.getExtraInfo());
			break;
		default:
			flag = false;
			break;
		}

		return flag;
	}

	private boolean processOrderTrade(String bdUserUuid, String extraInfo) {
		BigDecimal cashAmount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amountCash")) {
				String amountStr = infos.get("amountCash").toString();
				cashAmount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get cashAmount Error", e);
		}

		UserStat userStat = userStatService.updateOrderPay(bdUserUuid,
				cashAmount);

		return (userStat != null);
	}

	private boolean processCashChargeTrade(String bdUserUuid, String extraInfo) {
		BigDecimal cashAmount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);

			if (infos.containsKey("amount")) {
				String amountStr = infos.get("amount").toString();
				cashAmount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get cashAmount Error", e);
		}

		UserStat userStat = userStatService.updateCashCharge(bdUserUuid,
				cashAmount);

		return (userStat != null);
	}

	private boolean processCreditChargeTrade(String bdUserUuid, String extraInfo) {
		BigDecimal creditAmount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amount")) {
				String amountStr = infos.get("amount").toString();
				creditAmount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get creditAmount Error", e);
		}

		UserStat userStat = userStatService.updateCreditCharge(bdUserUuid,
				creditAmount);

		return (userStat != null);
	}

	private boolean processPayoutTrade(String bdUserUuid, String extraInfo) {
		BigDecimal cashAmount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("cashAmount")) {
				String amountStr = infos.get("cashAmount").toString();
				cashAmount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get cashAmount Error", e);
		}

		UserStat userStat = userStatService.updatePayout(bdUserUuid, cashAmount);

		return (userStat != null);
	}

	private boolean processWithdrawalTrade(String bdUserUuid, String extraInfo) {
		BigDecimal amount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amount")) {
				String amountStr = infos.get("amount").toString();
				amount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get amount Error", e);
		}

		UserStat userStat = userStatService.updateWithdrawal(bdUserUuid, amount);

		return (userStat != null);
	}

	private boolean processRejectWithdrawalTrade(String bdUserUuid,
			String extraInfo) {
		BigDecimal amount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amount")) {
				String amountStr = infos.get("amount").toString();
				amount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get amount Error", e);
		}

		UserStat userStat = userStatService.updateRejectWithdrawal(bdUserUuid,
				amount);

		return (userStat != null);
	}

	private boolean processRefundTrade(String bdUserUuid, String extraInfo) {
		BigDecimal amount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amount")) {
				String amountStr = infos.get("amount").toString();
				amount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get amount Error", e);
		}

		UserStat userStat = userStatService.updateRefund(bdUserUuid, amount);

		return (userStat != null);
	}

	private boolean processBusiChargeTrade(String bdUserUuid, String extraInfo) {
		BigDecimal amount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amount")) {
				String amountStr = infos.get("amount").toString();
				amount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get amount Error", e);
		}

		UserStat userStat = userStatService.updateBusiCharge(bdUserUuid, amount);

		return (userStat != null);
	}
}
