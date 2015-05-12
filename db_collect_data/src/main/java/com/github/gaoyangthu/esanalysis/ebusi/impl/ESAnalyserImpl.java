package com.github.gaoyangthu.esanalysis.ebusi.impl;

import com.github.gaoyangthu.core.util.JsonUtils;
import com.github.gaoyangthu.core.util.TimeUtils;
import com.github.gaoyangthu.esanalysis.ebusi.ESAnalyser;
import com.github.gaoyangthu.esanalysis.ebusi.bean.AccountMeta;
import com.github.gaoyangthu.esanalysis.ebusi.bean.MasterOrder;
import com.github.gaoyangthu.esanalysis.ebusi.bean.Transaction;
import com.github.gaoyangthu.esanalysis.ebusi.constant.EbusiConst;
import com.github.gaoyangthu.esanalysis.ebusi.service.AccountService;
import com.github.gaoyangthu.esanalysis.ebusi.service.OrderService;
import com.github.gaoyangthu.esanalysis.ebusi.service.TransactionService;
import com.github.gaoyangthu.esanalysis.ebusi.service.impl.AccountServiceImpl;
import com.github.gaoyangthu.esanalysis.ebusi.service.impl.OrderServiceImpl;
import com.github.gaoyangthu.esanalysis.ebusi.service.impl.TransactionServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserChannelTag;
import com.github.gaoyangthu.esanalysis.hbase.service.UserChannelTagService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserChannelTagServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserIdServiceImpl;
import com.github.gaoyangthu.esanalysis.report.bean.EbusiDailyReport;
import com.github.gaoyangthu.esanalysis.report.service.EbusiDailyReportService;
import com.github.gaoyangthu.esanalysis.report.service.impl.EbusiDailyReportServiceImpl;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据统计类
 *
 * Author: gaoyangthu
 * Date: 2014/4/1
 * Time: 10:37
 */
public class ESAnalyserImpl implements ESAnalyser {

	private static final Logger LOGGER = LoggerFactory.getLogger(ESAnalyserImpl.class);
	/**
	 * 日统计表
	 * R: bdUserUuid
	 * C: Date(Day)
	 * V: EbusiDailyReport
	 */
	private Table<String, Date, EbusiDailyReport> table;

	private UserIdService userIdService;

	private UserChannelTagService userChannelTagService;

	private EbusiDailyReportService ebusiDailyReportService;

	private AccountService accountService;

	private OrderService orderService;

	private TransactionService transactionService;

	public ESAnalyserImpl() {
		userIdService = new UserIdServiceImpl();
		userChannelTagService = new UserChannelTagServiceImpl();
		ebusiDailyReportService = new EbusiDailyReportServiceImpl();
		accountService = new AccountServiceImpl();
		orderService = new OrderServiceImpl();
		transactionService = new TransactionServiceImpl();
	}

	/**
	 * 分析数据库，统计数据，输出至展示库。
	 * <li>分析订单库</li>
	 * <li>分析交易库</li>
	 *
	 * @param beginDate 开始时间
	 * @param endDate   结束时间
	 * @return
	 */
	@Override
	public boolean analyse(Date beginDate, Date endDate) {
		LOGGER.info("Starting analyse Ebusi database.");
		boolean flag = false;
		// 初始化table
		table = HashBasedTable.create();

		// 分析订单
		flag = analyseOrder(beginDate, endDate);
		if (!flag) {
			LOGGER.error("Analyse order error. beginDate={}, endDate={}",
					beginDate, endDate);
		}

		flag = analyseTrade(beginDate, endDate);
		if (!flag) {
			LOGGER.error("Analyse trade error. beginDate={}, endDate={}",
					beginDate, endDate);
		}

		flag = save();
		if (!flag) {
			LOGGER.error("save error.");
		}
		// 清空table
		table.clear();

		LOGGER.info("Finished analyse Ebusi database.");
		return flag;
	}

	/**
	 * 分析订单
	 *
	 * @param beginDate 开始时间
	 * @param endDate   结束时间
	 * @return
	 */
	private boolean analyseOrder(Date beginDate, Date endDate) {
		List<MasterOrder> masterOrders = orderService.findByDate(beginDate,
				endDate);
		if (masterOrders == null) {
			return false;
		}

		for (MasterOrder masterOrder : masterOrders) {
			if (masterOrder != null) {
				LOGGER.debug(masterOrder.getMasterOrderId());
				boolean f = processOrder(masterOrder);
				if (!f) {
					LOGGER.error("Process order error. masterOrderId={}",
							masterOrder.getMasterOrderId());
				}
			}
		}
		return true;
	}

	private boolean processOrder(MasterOrder masterOrder) {
		int orderType = masterOrder.getMasterOrderType();
		// 退订订单
		if(orderType == 5 || orderType == 10) {
			return true;
		}

		boolean flag = false;
		String accountId = masterOrder.getFkAccountId();
		Date date = masterOrder.getUpdateDate();
		BigDecimal totalPrice = masterOrder.getTotalPrice();
		Integer status = masterOrder.getStatus();
		boolean isPaid = (status == EbusiConst.OrderStatus.FINISHED.getStatus());

		EbusiDailyReport report = getEbusiDailyReport(accountId, date);
		if (report == null) {
			return false;
		}

		report.setTotalOrderCount(report.getTotalOrderCount() + 1);
		report.setTotalOrderAmount(report.getTotalOrderAmount().add(totalPrice));
		if (isPaid) {
			report.setPayedOrderCount(report.getPayedOrderCount() + 1);
			report.setPayedOrderAmount(report.getPayedOrderAmount().add(
					totalPrice));

			Transaction orderTransaction = transactionService.findOrderTransaction(masterOrder.getMasterOrderId());
			if(orderTransaction != null) {
				report.setOrderTradeCount(report.getOrderTradeCount() + 1);
				report.setOrderCashAmount(report.getOrderCashAmount().add(orderTransaction.getAmountcash()));
				report.setOrderCreditAmount(report.getCreditChargeAmount().add(
						orderTransaction.getAmountcredit()));
			}
		}
		flag = putEbusiDailyReport(report);

		return flag;
	}

	/**
	 * 分析交易
	 *
	 * @param beginDate 开始时间
	 * @param endDate   结束时间
	 * @return
	 */
	private boolean analyseTrade(Date beginDate, Date endDate) {
		boolean flag = false;

		List<Transaction> transactions = transactionService.findByDate(beginDate, endDate);
		if(transactions == null) {
			return false;
		}

		for (Transaction transaction : transactions) {
			if (transaction != null) {
				boolean f = processTransaction(transaction);
				if (!f) {
					LOGGER.error("Process transaction error. transactionId={}",
							transaction.getTransactionId());
				}
			}
		}
		flag = true;

		return flag;
	}

	private boolean processTransaction(Transaction transaction) {
		boolean flag = false;

		String accountId = transaction.getFkAccountId();
		Date date = transaction.getTransactionTime();
		EbusiDailyReport report = getEbusiDailyReport(accountId, date);

		if (report == null) {
			return false;
		}

		Integer type = transaction.getTransactionType();
		EbusiConst.TransactionType tradeType = EbusiConst.TransactionType.fromType(type);

		flag = true;
		switch (tradeType) {
			case ORDER:
				break;
			case CASH_CHARGE:
				report.setCashChargeCount(report.getCashChargeCount() + 1);
				report.setCashChargeAmount(report.getCashChargeAmount().add(transaction.getAmountcash()));
				break;
			case CREDIT_CHARGE:
				report.setCreditChargeCount(report.getCreditChargeCount() + 1);
				report.setCreditChargeAmount(report.getCreditChargeAmount().add(
						transaction.getAmountcredit()));
				break;
			case REALTIME_PAYOUT:
				report.setPayoutCount(report.getPayoutCount() + 1);
				report.setPayoutAmount(report.getPayoutAmount().add(transaction.getAmountcash()));
				break;
			case WITHDRAWAL:
				report.setWithdrawalCount(report.getWithdrawalCount() + 1);
				report.setWithdrawalAmount(report.getWithdrawalAmount().add(transaction.getAmountcash()));
				break;
			case REJECT_WITHDRAWAL:
				report.setRejectWithdrawalCount(report.getRejectWithdrawalCount() + 1);
				report.setRejectWithdrawalAmount(report.getRejectWithdrawalAmount().add(
						transaction.getAmountcash()));
				break;
			case REFUND:
				report.setRefundCount(report.getRefundCount() + 1);
				report.setRefundAmount(report.getRefundAmount().add(transaction.getAmountcash()));
				break;
			case BUSINESS_CHARGE:
				report.setBusiChargeCount(report.getBusiChargeCount() + 1);
				report.setBusiChargeAmount(report.getBusiChargeAmount().add(transaction.getAmountcash()));
				break;
			default:
				flag = false;
				break;
		}
		if (!flag) {
			LOGGER.warn("Process transaction error. accountId={}, date={}, id={}",
					accountId, date, transaction.getTransactionId());
		}

		flag = putEbusiDailyReport(report);
		return flag;
	}


	private boolean processOrderTrade(EbusiDailyReport report, String extraInfo) {
		BigDecimal cashAmount = BigDecimal.ZERO;
		BigDecimal creditAmount = BigDecimal.ZERO;
		try {
			Map<String, Object> infos = JsonUtils.parseMap(extraInfo);
			if (infos.containsKey("amountCash")) {
				String amountStr = infos.get("amountCash").toString();
				cashAmount = new BigDecimal(amountStr);
			}
			if (infos.containsKey("creditCash")) {
				String amountStr = infos.get("creditCash").toString();
				creditAmount = new BigDecimal(amountStr);
			}
		} catch (IOException e) {
			LOGGER.warn("Get order amount Error", e);
		}

		report.setOrderTradeCount(report.getOrderTradeCount() + 1);
		report.setOrderCashAmount(report.getOrderCashAmount().add(cashAmount));
		report.setOrderCreditAmount(report.getCreditChargeAmount().add(
				creditAmount));

		return true;
	}

	/**
	 * 保存结果到展示库
	 *
	 * @return
	 */
	private boolean save() {
		boolean flag = false;
		if (table != null) {
			Collection<EbusiDailyReport> reports = table.values();
			for (EbusiDailyReport report : reports) {
				boolean f = ebusiDailyReportService.updateByDB(report);
				if (!f) {
					LOGGER.error(
							"Save ebusiDailyReport error. bdUserUuid={}, date={}",
							report.getBdUserUuid(), report.getDate());
				}
			}
			flag = true;
		}
		return flag;
	}

	private EbusiDailyReport getEbusiDailyReport(String accountId, Date date) {
		if (StringUtils.isBlank(accountId) || date == null) {
			LOGGER.error("The accountId|date is null.");
			return null;
		}
		EbusiDailyReport ebusiDailyReport = null;

		String bdUserUuid = userIdService.getBdUserUuid(null, null, accountId);
		Date day = TimeUtils.getTimeAtStartOfDay(date);
		if ((table != null) && (table.contains(bdUserUuid, day))) {
			ebusiDailyReport = table.get(bdUserUuid, day);
		} else {
			ebusiDailyReport = new EbusiDailyReport();
			ebusiDailyReport.setBdUserUuid(bdUserUuid);
			ebusiDailyReport.setDate(day);
			// 根据account_id查询MySQL的account_meta获取用户信息.
			AccountMeta account = accountService.getAccount(accountId);
			if(account != null) {
				ebusiDailyReport.setEmail(account.getLoginEmail());
				ebusiDailyReport.setUserName(account.getLoginName());
				ebusiDailyReport.setRegisterCount(1);
				List<UserChannelTag> userChannelTags = userChannelTagService.findByBdUserId(bdUserUuid);
				if((userChannelTags != null) && (userChannelTags.size() > 0)) {
					int channelId = userChannelTags.get(userChannelTags.size() - 1).getChannelId();
					ebusiDailyReport.setUserChannelTag(String.valueOf(channelId));
				}
			}
		}
		return ebusiDailyReport;
	}

	private boolean putEbusiDailyReport(EbusiDailyReport ebusiDailyReport) {
		if (ebusiDailyReport == null) {
			return false;
		}

		table.put(ebusiDailyReport.getBdUserUuid(),
				ebusiDailyReport.getDate(), ebusiDailyReport);
		return true;
	}
}
