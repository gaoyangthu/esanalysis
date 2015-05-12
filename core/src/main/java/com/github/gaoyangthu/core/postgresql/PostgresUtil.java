package com.github.gaoyangthu.core.postgresql;

import com.github.gaoyangthu.core.mybatis.MybatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Hello world!
 * 
 */
public class PostgresUtil {

	private static volatile SqlSessionFactory sessionFactory;

	static {
		sessionFactory = MybatisUtil.createSessionFactory("mybatis/postgresql_configuration.xml");
	}

	public static SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
