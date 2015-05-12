package com.github.gaoyangthu.esanalysis;

import com.github.gaoyangthu.esanalysis.stat.service.ChannelStatService;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

//		UserStatService userStatService = new UserStatServiceImpl();
//		List<UserStat> userStats = userStatService.findAll();
//		for(UserStat userStat : userStats) {
//			System.out.println(userStat);
//		}

		ChannelStatService channelStatService = new ChannelStatService();
		channelStatService.stat();
		channelStatService.report();
	}

}
