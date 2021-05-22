package com.example.springbootaoptest.dao;

import com.example.springbootaoptest.entity.SysLog;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/14
 */
public interface SysLogDao {
    //接口中的方法默认都是public abstract
    void saveSysLog(SysLog sysLog);
}
