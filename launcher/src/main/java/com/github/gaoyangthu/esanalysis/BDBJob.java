package com.github.gaoyangthu.esanalysis;

import com.github.gaoyangthu.esanalysis.bdb.service.BDBService;
import com.github.gaoyangthu.esanalysis.bdb.service.impl.BDBServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * BerkeleyDB 定时任务
 *
 * Author: gaoyangthu
 * Date: 2014/4/2
 * Time: 15:05
 */
public class BDBJob implements Job {
	/**
	 * 定时生成bdb数据库,并上传到hdfs
	 *
	 * @param context
	 * @throws org.quartz.JobExecutionException if there is an exception while executing the job.
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		BDBService bdbService = new BDBServiceImpl();
		bdbService.report();
	}
}
