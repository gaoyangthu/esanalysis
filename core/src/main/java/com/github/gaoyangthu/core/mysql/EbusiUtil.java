package com.github.gaoyangthu.core.mysql;

import com.github.gaoyangthu.core.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Hello world!
 * 
 */
public class EbusiUtil {

	private static volatile SqlSessionFactory sessionFactory;

	static {
		sessionFactory = MybatisUtil.createSessionFactory("mybatis/ebusi_configuration.xml");
	}

	public static SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
