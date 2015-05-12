package com.github.gaoyangthu.esanalysis;

import com.github.gaoyangthu.esanalysis.meta.service.impl.ChannelRuleServiceImpl;
import com.github.gaoyangthu.esanalysis.meta.service.impl.UrlBehaviorRuleServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 更新元数据库的内存缓存
 *
 * Author: gaoyangthu
 * Date: 2014/4/8
 * Time: 9:53
 */
public class MetaJob implements Job {
	/**
	 * 更新元数据库的内存缓存
	 *
	 * @param context
	 * @throws org.quartz.JobExecutionException if there is an exception while executing the job.
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ChannelRuleServiceImpl.reload();
		UrlBehaviorRuleServiceImpl.reload();
	}
}
