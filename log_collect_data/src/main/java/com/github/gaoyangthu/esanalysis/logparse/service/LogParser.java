package com.github.gaoyangthu.esanalysis.logparse.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.gaoyangthu.esanalysis.logparse.bean.LogEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-19
 * Time: 上午11:00
 */
public class LogParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogParser.class);

	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 日志解析正则
	 * 
	 * TODO 解析正则方，统一放入
	 * CV_ID:X001
	 * 	1. PG/ebusi_channel/log_parse_rule
	 * 	2. 建议新生成一个配置文件，清晰规则依赖配置文件指定   log_parse_rule Rule_ID
	 */
	private String conversionPattern = "^([0-9.,\\s]+)\\s([\\w.-]+)\\s([\\w.-]+)\\s\\[([^\\[\\]]+)\\]\\s\"((?:[^\"]|\\\")+)\"\\s(\\d{3})\\s(\\d+|-)\\s\"((?:[^\"]|\\\")+)\"\\s\"((?:[^\"]|\\\")+)\"\\s\"([\\w.-]+)\"\\s\"([\\w.-]+)\"$";

	private Pattern conversionRegex;

	private LogEntryService logEntryService;

	public LogParser() {
		init();
	}

	public LogParser(String conversionPattern) {
		this.conversionPattern = conversionPattern;
		init();
	}

	private void init() {
		conversionRegex = Pattern.compile(conversionPattern);
		logEntryService = new LogEntryService();
	}

	public boolean parseLog(String fileName) {
		return parseLog(fileName, DEFAULT_ENCODING);
	}

	public boolean parseLog(String fileName, String encoding) {
		return parseLog(new File(fileName), encoding);
	}

	public boolean parseLog(File file) {
		return parseLog(file, DEFAULT_ENCODING);
	}

	public boolean parseLog(File file, String encoding) {
		InputStream input = null;
		try {
			input = FileUtils.openInputStream(file);
			return parseLog(input, encoding);
		} catch (IOException e) {
			LOGGER.error("IOException,", e);
		}
		return false;
	}

	public boolean parseLog(InputStream input) {
		return parseLog(input, DEFAULT_ENCODING);
	}

	/**
	 * 解析日志
	 *
	 * @param input 输入流
	 * @param encoding 字符编码
	 * @return
	 */
	public boolean parseLog(InputStream input, String encoding) {
		try {
			LineIterator iter = IOUtils.lineIterator(input, encoding);
			try {
				while (iter.hasNext()) {
					// 按行读取日志
					String line = iter.nextLine();
					try {
						// 正则匹配
						Matcher matcher = conversionRegex.matcher(line);
						//TODO 需要一个计数器，无匹配占需每日收集起来 
						//CV_ID:001
						if (matcher.find()) {
							LogEntry logEntry = new LogEntry();
							logEntry.setIp(matcher.group(1));
							logEntry.setIdentity(matcher.group(2));
							logEntry.setUserId(matcher.group(3));
							logEntry.setTime(matcher.group(4));
							String request = matcher.group(5);
							String[] reqs = request.split(("\\s"));
							if (reqs.length > 1) {
								logEntry.setUrl(reqs[1]);
							}
							logEntry.setStatus(matcher.group(6));
							logEntry.setLength(matcher.group(7));
							logEntry.setReferrer(matcher.group(8));
							logEntry.setUserAgent(matcher.group(9));
							logEntry.setCookieId(matcher.group(10));
							logEntry.setAccountId(matcher.group(11));
							
							logEntryService.processLogEntry(logEntry);
						} else {
							LOGGER.debug("Miss matche: line={}", line);
						}
					} catch (StackOverflowError e) {
						LOGGER.error("StackOverflowError. line={}", line);
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception,", e);
			} finally {
				iter.close();
			}
		} catch (IOException e) {
			LOGGER.error("IOException,", e);
		}

		return true;
	}
}
