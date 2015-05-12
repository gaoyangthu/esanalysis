package com.github.gaoyangthu.core.schedule;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 定时任务管理器
 *
 * Author: gaoyangthu
 * Date: 2014/3/27
 * Time: 11:04
 */
public class ScheduleManager implements Closeable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleManager.class);

	private volatile SchedulerFactory schedulerFactory;

	private volatile Scheduler scheduler;

	private static volatile ScheduleManager scheduleManager = null;

	private ScheduleManager() {
		schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			LOGGER.error("Create scheduler Exception", e);
		}
	}

	public static ScheduleManager getInstance() {
		if(scheduleManager == null) {
			synchronized (ScheduleManager.class) {
				if(scheduleManager == null) {
					scheduleManager = new ScheduleManager();
				}
			}
		}
		return scheduleManager;
	}

	public boolean start() {
		boolean flag = false;
		try {
			scheduler.start();
			flag = true;
		} catch (SchedulerException e) {
			LOGGER.error("Start scheduler Exception", e);
		}
		return flag;
	}

	public boolean standby() {
		boolean flag = false;
		try {
			scheduler.standby();
			flag = true;
		} catch (SchedulerException e) {
			LOGGER.error("Standby scheduler Exception", e);
		}
		return flag;
	}

	public boolean shutdown() {
		boolean flag = false;
		try {
			scheduler.shutdown();
			flag = true;
		} catch (SchedulerException e) {
			LOGGER.error("Shutdown scheduler Exception", e);
		}
		return flag;
	}

	public boolean shutdown(boolean waitForJobsToComplete) {
		boolean flag = false;
		try {
			scheduler.shutdown(waitForJobsToComplete);
			flag = true;
		} catch (SchedulerException e) {
			LOGGER.error("Shutdown scheduler Exception", e);
		}
		return flag;
	}

	/**
	 * Closes this stream and releases any system resources associated
	 * with it. If the stream is already closed then invoking this
	 * method has no effect.
	 *
	 * @throws java.io.IOException if an I/O error occurs
	 */
	@Override
	public void close() throws IOException {
		shutdown();
	}
}
