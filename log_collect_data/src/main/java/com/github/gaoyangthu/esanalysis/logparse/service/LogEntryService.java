package com.github.gaoyangthu.esanalysis.logparse.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.gaoyangthu.core.util.TimeUtils;
import com.github.gaoyangthu.esanalysis.logparse.bean.LogEntry;
import com.github.gaoyangthu.esanalysis.meta.bean.ChannelRule;
import com.github.gaoyangthu.esanalysis.meta.service.UrlBehaviorRuleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gaoyangthu.esanalysis.hbase.service.UserBehaviorService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserChannelTagService;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserBehaviorServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserChannelTagServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserIdServiceImpl;
import com.github.gaoyangthu.esanalysis.meta.bean.UrlBehaviorRule;
import com.github.gaoyangthu.esanalysis.meta.service.ChannelRuleService;
import com.github.gaoyangthu.esanalysis.meta.service.impl.ChannelRuleServiceImpl;
import com.github.gaoyangthu.esanalysis.meta.service.impl.UrlBehaviorRuleServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-19
 * Time: 下午12:20
 */
public class LogEntryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogEntryService.class);

	private static DateFormat df = new SimpleDateFormat(
			"dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

	private ChannelRuleService channelRuleService;

	private UrlBehaviorRuleService urlBehaviorRuleService;

	private UserIdService userIdService;

	private UserChannelTagService userChannelTagService;

	private UserBehaviorService userBehaviorService;

	public LogEntryService() {
		channelRuleService = new ChannelRuleServiceImpl();
		urlBehaviorRuleService = new UrlBehaviorRuleServiceImpl();
		userIdService = new UserIdServiceImpl();
		userChannelTagService = new UserChannelTagServiceImpl();
		userBehaviorService = new UserBehaviorServiceImpl();
	}

	//TODO 添加本方法注解
	//TODO 引入多线程
	//CV_ID:X001
	public boolean processLogEntry(LogEntry logEntry) {
		boolean flag = false;

		// 处理日志的时间
		String timeStr = logEntry.getTime();
		Date time = TimeUtils.getCurrentDate();
		try {
			time = df.parse(timeStr);
		} catch (ParseException e) {
			LOGGER.warn("ParseException,", e);
		}

		// 获取uaId, cookieId, accountId
		String uaId = getUaId(logEntry.getIp(), logEntry.getUserAgent(), time);
		String cookieId = logEntry.getCookieId().equals("-") ? null
				: logEntry.getCookieId();
		String accountId = logEntry.getAccountId().equals("-") ? null
				: logEntry.getAccountId();

		// 获取bdUserUuid
		String bdUserUuid = userIdService.getBdUserUuid(uaId, cookieId,
				accountId);

		// 处理渠道标签
		flag = processChannel(bdUserUuid, time, logEntry);
		// 处理用户行为
//		flag = processUserBehavior(bdUserUuid, time, logEntry);

		return true;
	}

	/**
	 * 分析渠道标签
	 *
	 * @param bdUserUuid
	 * @param time
	 * @param logEntry
	 * @return
	 */
	private boolean processChannel(String bdUserUuid, Date time,
			LogEntry logEntry) {
		String url = logEntry.getUrl();
		String[] refs = url.split("\\?r=");

		if (refs.length == 1) {
			// 不存在渠道标签
			return true;
		}

		String channelCode = refs[1];
		ChannelRule channelRule = channelRuleService.findByChannelCode(channelCode);
		if (channelRule == null) {
			LOGGER.error("Couldn't find channel rule. channelCode={}",
					channelCode);
			return false;
		}

		boolean flag = userChannelTagService.addChannelTag(bdUserUuid,
				channelRule.getSerialId(), channelRule.getChannelCode(),
				channelRule.getChannelName(), time);
		// LOGGER.debug("Add user channel tag. channelCode={}, bdUserUuid={}",
		// channelCode, bdUserUuid);

		return flag;
	}

	/**
	 * 分析用户行为
	 *
	 * @param bdUserUuid
	 * @param time
	 * @param logEntry
	 * @return
	 */
	private boolean processUserBehavior(String bdUserUuid, Date time,
			LogEntry logEntry) {
		String url = logEntry.getUrl();

		// 排除静态文件
		//TODO  静态文件，存入 pg 规则库
		//CV_ID:X001
		if (StringUtils.startsWithAny(url, "/bbs/static",
				"/bbs/data/attachment", "/bbs/uc_server/data")
				|| StringUtils.endsWithAny(url, ".js", ".png", ".jpg", ".css")) {
			return true;
		}

		List<UrlBehaviorRule> urlBehaviorRules = urlBehaviorRuleService.findAll();
		// 依次匹配url
		for (UrlBehaviorRule urlBehaviorRule : urlBehaviorRules) {
			String urlRegex = urlBehaviorRule.getUrlRegex();
			Pattern pattern = Pattern.compile(urlRegex);
			try {
				Matcher matcher = pattern.matcher(url);
				if (matcher.find()) {
					boolean flag = userBehaviorService.addUserBehavior(
							bdUserUuid, url, urlBehaviorRule.getSerialId(),
							urlBehaviorRule.getUrlRegex(),
							urlBehaviorRule.getUserBehaviorId(), "",
							logEntry.getIp(), time,
							Integer.parseInt(logEntry.getStatus()),
							logEntry.getUserAgent());
					// LOGGER.debug(
					// "Add user behavior. urlRegex={}, url={}, bdUserUuid={}",
					// urlRegex, url, bdUserUuid);
				}
			} catch (StackOverflowError e) {
				LOGGER.warn("StackOverflowError, url={}, urlRegex={}", url,
						urlRegex);
			}
		}

		return true;
	}

	/**
	 * 生成uaId
	 *
	 * @param ip
	 * @param userAgent
	 * @param time
	 * @return
	 */
	private String getUaId(String ip, String userAgent, Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timeStr = sdf.format(time);
		String uaId = DigestUtils.md5Hex(ip + userAgent + timeStr);
		return uaId;
	}

}
