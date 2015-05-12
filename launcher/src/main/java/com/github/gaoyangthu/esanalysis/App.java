package com.github.gaoyangthu.esanalysis;

import com.github.gaoyangthu.core.schedule.ScheduleManager;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println("Starting Scheduler");

		ScheduleManager scheduleManager = ScheduleManager.getInstance();
		scheduleManager.start();

		System.out.println("Started Scheduler");

	}
}
