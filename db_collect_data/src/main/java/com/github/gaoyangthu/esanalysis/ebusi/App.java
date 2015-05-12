package com.github.gaoyangthu.esanalysis.ebusi;

import com.github.gaoyangthu.core.util.TimeUtils;
import com.github.gaoyangthu.esanalysis.ebusi.impl.AccountAnalyserImpl;
import com.github.gaoyangthu.esanalysis.ebusi.impl.ESAnalyserImpl;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-17
 * Time: 下午4:31
 */
public class App {

	public static void main(String[] args) {

		Date endDate = TimeUtils.getTimeAtStartOfDay();
		Date beginDate = TimeUtils.plusDays(endDate, -50);

		AccountAnalyser accountAnalyser = new AccountAnalyserImpl();
		ESAnalyser esAnalyser = new ESAnalyserImpl();

		accountAnalyser.analyseAccount(beginDate, endDate);
		esAnalyser.analyse(beginDate, endDate);

	}

}
