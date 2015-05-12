package com.github.gaoyangthu.core.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * 时间工具类
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午4:19
 */
public class TimeUtils {

	public static DateTime getDateTime() {
		DateTime dateTime = new DateTime();
		return dateTime;
	}

	public static Date getCurrentDate() {
		return getDateTime().toDate();
	}

	public static Date getTimeAtStartOfDay() {
		DateTime dateTime = getDateTime();
		DateTime startDay = dateTime.withTimeAtStartOfDay();
		return startDay.toDate();
	}

	public static Date getTimeAtStartOfDay(Date date) {
		DateTime dateTime = new DateTime(date);
		DateTime startDay = dateTime.withTimeAtStartOfDay();
		return startDay.toDate();
	}

	public static Date plusDays(Date date, int days) {
		DateTime dateTime = new DateTime(date);
		DateTime targetTime = dateTime.plusDays(days);
		return targetTime.toDate();
	}
}
