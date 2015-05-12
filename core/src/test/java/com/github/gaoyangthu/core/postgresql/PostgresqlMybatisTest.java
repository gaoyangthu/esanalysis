package com.github.gaoyangthu.core.postgresql;

import com.github.gaoyangthu.core.postgresql.bean.ChannelRule;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import com.github.gaoyangthu.core.postgresql.dao.ChannelRuleMapper;

public class PostgresqlMybatisTest {

	@Test
	public void test(){
		SqlSession sqlSession = PostgresUtil.getSessionFactory().openSession();
		ChannelRuleMapper userMapper = sqlSession
				.getMapper(ChannelRuleMapper.class);
		ChannelRule user = userMapper.findById(2010100002);
		
		Assert.assertEquals("postgresql_MyBatis_err", user.getSerial_id() ,2010100002 );
	}
	
	
}
