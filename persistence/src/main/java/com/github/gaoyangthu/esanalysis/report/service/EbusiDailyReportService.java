package com.github.gaoyangthu.esanalysis.report.service;

import com.github.gaoyangthu.esanalysis.report.bean.EbusiDailyReport;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/31
 * Time: 16:58
 */
public interface EbusiDailyReportService {

	/**
	 * 创建EbusiDailyReport
	 *
	 * @param ebusiDailyReport
	 * @return
	 */
	boolean create(EbusiDailyReport ebusiDailyReport);

	/**
	 * 根据主键查询EbusiDailyReport
	 *
	 * @param bdUserUuid
	 * @param date
	 * @return
	 */
	EbusiDailyReport get(String bdUserUuid, Date date);

	/**
	 * 更新EbusiDailyReport
	 *
	 * @param ebusiDailyReport
	 * @return
	 */
	boolean update(EbusiDailyReport ebusiDailyReport);

	/**
	 * 从日志更新EbusiDailyReport
	 *
	 * @param bdUserUuid
	 * @param date
	 * @param userChannelTag
	 * @param firstChannelVisit
	 * @param pv
	 * @return
	 */
	boolean updateByLog(String bdUserUuid, Date date, String userChannelTag, Date firstChannelVisit, Integer pv);

	/**
	 * 从数据更新EbusiDailyReport
	 *
	 * @param ebusiDailyReport
	 * @return
	 */
	boolean updateByDB(EbusiDailyReport ebusiDailyReport);
}
