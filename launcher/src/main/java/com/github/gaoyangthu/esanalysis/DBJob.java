package com.github.gaoyangthu.esanalysis;

import com.github.gaoyangthu.core.util.TimeUtils;
import com.github.gaoyangthu.esanalysis.ebusi.AccountAnalyser;
import com.github.gaoyangthu.esanalysis.ebusi.ESAnalyser;
import com.github.gaoyangthu.esanalysis.ebusi.impl.AccountAnalyserImpl;
import com.github.gaoyangthu.esanalysis.ebusi.impl.ESAnalyserImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 数据库定时任务
 *
 * Author: gaoyangthu
 * Date: 2014/3/27
 * Time: 14:04
 */
public class DBJob implements Job {
	/**
	 * 读取一天前的数据库,导入到展示库中
	 *
	 * @param context
	 * @throws org.quartz.JobExecutionException if there is an exception while executing the job.
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date endDate = TimeUtils.getTimeAtStartOfDay();
		Date beginDate = TimeUtils.plusDays(endDate, -1);

		AccountAnalyser accountAnalyser = new AccountAnalyserImpl();
		ESAnalyser esAnalyser = new ESAnalyserImpl();

		accountAnalyser.analyseAccount(beginDate, endDate);
		esAnalyser.analyse(beginDate, endDate);
	}
}
