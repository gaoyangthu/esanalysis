package com.github.gaoyangthu.esanalysis.logreport.mapred;

import com.github.gaoyangthu.core.util.TimeUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA
 * Project: esanalysis
 * Author: GaoYang
 * Date: 2014/4/8 0008
 */
public class LogParser {


	/**
	 * The constant logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LogParser.class);

	/**
	 * The default log encoding.
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * The date format.
	 */
	private static DateFormat df = new SimpleDateFormat(
			"dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

	/**
	 * The regular expression string of log parsing.
	 * 
	 *  TODO 正则规则写在 pg:ebusi_channel->log_parse_rule 表里
	 */
	private String conversionPattern = "^([0-9.,\\s]+)\\s([\\w.-]+)\\s([\\w.-]+)\\s\\[([^\\[\\]]+)\\]\\s\"((?:[^\"]|\\\")+)\"\\s(\\d{3})\\s(\\d+|-)\\s\"((?:[^\"]|\\\")+)\"\\s\"((?:[^\"]|\\\")+)\"\\s\"([\\w.-]+)\"\\s\"([\\w.-]+)\"$";

	/**
	 * The regular expression of log parsing.
	 */
	private Pattern conversionRegex;

	/**
	 * The ua id, cookieId, accountId and channel code
	 */
	private String uaId;
	private String cookieId;
	private String accountId;
	private String channelCode;
	private Date visitTime;

	/**
	 * Getters and Setters
	 */

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCookieId() {
		return cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public String getUaId() {
		return uaId;
	}

	public void setUaId(String uaId) {
		this.uaId = uaId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	/**
	 * The default construction method.
	 */
	public LogParser() {
		init();
	}

	/**
	 * The construction method.
	 * @param conversionPattern
	 */
	public LogParser(String conversionPattern) {
		this.conversionPattern = conversionPattern;
		init();
	}

	/**
	 * The private initializing method.
	 */
	private void init() {
		conversionRegex = Pattern.compile(conversionPattern);
		uaId = null;
		cookieId = null;
		accountId = null;
		channelCode = null;
	}

	/**
	 * Parse the given access log with default encoding utf-8.
	 * @param accessLog
	 * @return
	 */
	public boolean parseLog(String accessLog) {
		return parseLog(accessLog, DEFAULT_ENCODING);
	}

	/**
	 * Parse the given access log with user chosen encoding.
	 * @param accessLog
	 * @param encoding
	 * @return
	 */
	public boolean parseLog(String accessLog, String encoding) {
		try {
			Matcher matcher = conversionRegex.matcher(accessLog);
			if (matcher.find()) {
				/**
				 * Get date and time of the access log
				 */
				String timeStr = matcher.group(4);
				Date time = TimeUtils.getCurrentDate();
				try {
					time = df.parse(timeStr);
				} catch (ParseException e) {
					LOGGER.warn("ParseException,", e);
				}
				setVisitTime(time);

				/**
				 * Get ua id, cookieId and accountId
				 */
				setUaId(generateUaId(matcher.group(1), matcher.group(9), time));
				setCookieId(matcher.group(10).equals("-") ? null
						: matcher.group(10));
				setAccountId(matcher.group(11).equals("-") ? null
						: matcher.group(11));

				/**
				 * Get channel from url
				 */
				String request = matcher.group(5);
				String[] reqs = request.split(("\\s"));
				String url = "";
				if (reqs.length > 1) {
					url = reqs[1];
				}
				String[] refs = url.split("\\?r=");
				if (refs.length > 1) {
					setChannelCode(refs[1]);
				}
			}
		} catch (StackOverflowError e) {
			LOGGER.error("StackOverflowError. line={}", accessLog);
		}
		return true;
	}

	/**
	 * Generate ua id from ip, user agent and access time
	 * @param ip
	 * @param userAgent
	 * @param time
	 * @return
	 */
	private String generateUaId(String ip, String userAgent, Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String timeStr = sdf.format(time);
		String uaId = DigestUtils.md5Hex(ip + userAgent + timeStr);
		return uaId;
	}
}