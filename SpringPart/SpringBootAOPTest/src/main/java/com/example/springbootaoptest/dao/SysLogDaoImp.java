package com.example.springbootaoptest.dao;

import com.example.springbootaoptest.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/14
 */
@Repository
public class SysLogDaoImp implements SysLogDao{
    @Autowired //@Autowired是spring的注解，用来自动装配，把控制反转的类的对象注入到这里
    private JdbcTemplate jdbcTemplate;
    @Override
    public void saveSysLog(SysLog sysLog) {
        StringBuffer sql = new StringBuffer("insert into sys_log ");
        sql.append("(username,operation,time,method,params,ip,create_time) ");
        sql.append("values(:username,:operation,:time,:method,");
        sql.append(":params,:ip,:createTime)");

        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        npjt.update(sql.toString(), new BeanPropertySqlParameterSource(sysLog));
    }
}
