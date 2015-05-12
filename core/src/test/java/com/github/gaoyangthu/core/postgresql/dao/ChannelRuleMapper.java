package com.github.gaoyangthu.core.postgresql.dao;

import com.github.gaoyangthu.core.postgresql.bean.ChannelRule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface ChannelRuleMapper {  
	
    @Select("select serial_id from channel_rule where serial_id=#{id}")
	public ChannelRule findById(@Param("id")Integer Id);
    
    
}  

