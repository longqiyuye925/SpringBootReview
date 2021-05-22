package com.example.aopdesign.espect;

import java.lang.reflect.Method;

/**
 * @Description:定义框架支持的advice
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
public abstract class DefaultAspect {
    /**
     * 事前拦截
     *
     * @param targetclass
     * @param method
     * @param args
     * @throws Throwable
     */
    public void before(Class<?> targetclass, Method method, Object[] args) throws Throwable {

    }

    /**
     * 事后拦截
     *
     * @param targetclass
     * @param method
     * @param args
     * @param returnValue
     * @return
     * @throws Throwable
     */
    public Object afterReturning(Class<?> targetclass, Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }

    /**
     * 发生异常时候的处理
     *
     * @param targetclass
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public void afterThrowing(Class<?> targetclass, Method method, Object[] args) throws Throwable {

    }

    /**
     * 不管是否异常都执行的逻辑
     * @param targetClass
     * @param method
     * @param args
     * @param returnValue
     * @return
     * @throws Throwable
     */
    public Object after(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }
}
