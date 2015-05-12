package com.github.gaoyangthu.core.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午1:49
 */
public class MybatisUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MybatisUtil.class);

	public static SqlSessionFactory createSessionFactory(String resource) {
		SqlSessionFactory sessionFactory = null;
		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource));
		} catch (IOException e) {
			LOGGER.error("Creating SqlSessionFactory error.", e);
		}
		return sessionFactory;
	}

}
