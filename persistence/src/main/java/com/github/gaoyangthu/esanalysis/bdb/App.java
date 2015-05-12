package com.github.gaoyangthu.esanalysis.bdb;

import com.github.gaoyangthu.esanalysis.bdb.service.BDBService;
import com.github.gaoyangthu.esanalysis.bdb.service.impl.BDBServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/31
 * Time: 14:17
 */
public class App {

	public static void main( String[] args ) {
		BDBService bdbService = new BDBServiceImpl();
		bdbService.report();
	}
}
