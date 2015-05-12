package com.github.gaoyangthu.esanalysis.stat.service;

import com.github.gaoyangthu.esanalysis.hbase.bean.UserBehavior;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserChannelTag;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserStat;
import com.github.gaoyangthu.esanalysis.hbase.service.UserBehaviorService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserChannelTagService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserStatService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserBehaviorServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserChannelTagServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserStatServiceImpl;
import com.github.gaoyangthu.esanalysis.meta.bean.ChannelRule;
import com.github.gaoyangthu.esanalysis.meta.service.ChannelRuleService;
import com.github.gaoyangthu.esanalysis.meta.service.impl.ChannelRuleServiceImpl;
import com.github.gaoyangthu.esanalysis.stat.bean.ChannelStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/20
 * Time: 14:29
 */
public class ChannelStatService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelStatService.class);

	private volatile List<ChannelStat> channelStats;

	private ChannelRuleService channelRuleService;

	private UserChannelTagService userChannelTagService;

	private UserBehaviorService userBehaviorService;

	private UserStatService userStatService;

	public ChannelStatService() {
		channelStats = new ArrayList<ChannelStat>();

		channelRuleService = new ChannelRuleServiceImpl();

		userChannelTagService = new UserChannelTagServiceImpl();
		userBehaviorService = new UserBehaviorServiceImpl();
		userStatService = new UserStatServiceImpl();
	}

	public boolean stat() {
		List<ChannelRule> channelRules = channelRuleService.findAll();

		LOGGER.info("Start stat");
		for(ChannelRule channelRule : channelRules) {
			LOGGER.info("Stat channel. channelName={}", channelRule.getChannelName());
//			if("广告_落伍者".equals(channelRule.getChannelName())) {
//				LOGGER.info("Debug");
//			}
			// 构造渠道统计
			ChannelStat channelStat = new ChannelStat(channelRule.getSerialId(), channelRule.getChannelCode(), channelRule.getChannelName());

			// 统计渠道关联的用户
			Set<String> users = new HashSet<String>();
			List<UserChannelTag> userChannelTags = userChannelTagService.findByChannelCode(channelRule.getChannelCode());
			for(UserChannelTag userChannelTag : userChannelTags) {
				if(userChannelTag != null) {
					users.add(userChannelTag.getBdUserUuid());
				}
			}
			channelStat.setUsers(users);

			for(String bdUserUuid : users) {
				List<UserBehavior> userBehaviors = userBehaviorService.findByBdUserId(bdUserUuid);
				if((userBehaviors != null) && (userBehaviors.size() > 0)) {
					channelStat.setPv(channelStat.getPv() + userBehaviors.size());
					channelStat.setUv(channelStat.getUv() + 1);
				} else {
//					LOGGER.info("The user have not userBehaviors. bdUserUuid={}", bdUserUuid);
				}

				UserStat userStat = userStatService.getUserStat(bdUserUuid);
				if(userStat != null) {
					channelStat.setTotalOrderCount(channelStat.getTotalOrderCount()
							+ userStat.getTotalOrderCount());
					channelStat.setTotalOrderAmount(channelStat.getTotalOrderAmount().add(
							userStat.getTotalOrderAmount()));
					channelStat.setPayOrderCount(channelStat.getPayOrderCount()
							+ userStat.getPayOrderCount());
					channelStat.setPayOrderAmount(channelStat.getPayOrderAmount().add(
							userStat.getPayOrderAmount()));
					channelStat.setOrderTradeCount(channelStat.getOrderTradeCount()
							+ userStat.getOrderTradeCount());
					channelStat.setOrderCashAmount(channelStat.getOrderCashAmount().add(
							userStat.getOrderCashAmount()));
					channelStat.setCashChargeCount(channelStat.getCashChargeCount()
							+ userStat.getCashChargeCount());
					channelStat.setCashChargeAmount(channelStat.getCashChargeAmount().add(
							userStat.getCashChargeAmount()));
					channelStat.setCreditChargeCount(channelStat.getCreditChargeCount()
							+ userStat.getCreditChargeCount());
					channelStat.setCreditChargeAmount(channelStat.getCreditChargeAmount().add(
							userStat.getCreditChargeAmount()));
					channelStat.setPayoutCount(channelStat.getPayoutCount()
							+ userStat.getPayoutCount());
					channelStat.setPayoutAmount(channelStat.getPayoutAmount().add(
							userStat.getPayoutAmount()));
					channelStat.setWithdrawalCount(channelStat.getWithdrawalCount()
							+ userStat.getWithdrawalCount());
					channelStat.setWithdrawalAmount(channelStat.getWithdrawalAmount().add(
							userStat.getWithdrawalAmount()));
					channelStat.setRejectWithdrawalCount(channelStat.getRejectWithdrawalCount()
							+ userStat.getRejectWithdrawalCount());
					channelStat.setRejectWithdrawalAmount(channelStat.getRejectWithdrawalAmount().add(
							userStat.getRejectWithdrawalAmount()));
					channelStat.setRefundCount(channelStat.getRefundCount()
							+ userStat.getRefundCount());
					channelStat.setRefundAmount(channelStat.getRefundAmount().add(
							userStat.getRefundAmount()));
					channelStat.setBusiChargeCount(channelStat.getBusiChargeCount()
							+ userStat.getBusiChargeCount());
					channelStat.setBusiChargeAmount(channelStat.getBusiChargeAmount().add(
							userStat.getBusiChargeAmount()));
				}
			}

			channelStats.add(channelStat);
		}

		LOGGER.info("End stat");

		return true;
	}

	public boolean report() {

		StringBuilder sb = new StringBuilder();
		sb.append("channelId");
		sb.append(", channelCode");
		sb.append(", channelName");
		sb.append(", users");
		sb.append(", pv");
		sb.append(", uv");
		sb.append(", totalOrderCount");
		sb.append(", totalOrderAmount");
		sb.append(", payOrderCount");
		sb.append(", payOrderAmount");
		sb.append(", orderTradeCount");
		sb.append(", orderCashAmount");
		sb.append(", cashChargeCount");
		sb.append(", cashChargeAmount");
		sb.append(", creditChargeCount");
		sb.append(", creditChargeAmount");
		sb.append(", payoutCount");
		sb.append(", payoutAmount");
		sb.append(", withdrawalCount");
		sb.append(", withdrawalAmount");
		sb.append(", rejectWithdrawalCount");
		sb.append(", rejectWithdrawalAmount");
		sb.append(", refundCount");
		sb.append(", refundAmount");
		sb.append(", busiChargeCount");
		sb.append(", busiChargeAmount");

		System.out.println(sb.toString());

		for(ChannelStat channelStat : channelStats) {
			System.out.println(channelStat.toStatString());
		}
		return true;
	}

}
