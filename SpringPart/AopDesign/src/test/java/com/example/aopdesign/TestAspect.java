package com.example.aopdesign;

import com.example.aopdesign.annotation.Aspect;
import com.example.aopdesign.annotation.Order;
import com.example.aopdesign.espect.DefaultAspect;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/22
 */
@Aspect(pointcut = "within(com.example.aopdesign.*)")
@Order(1)
public class TestAspect extends DefaultAspect {
    @Override
    public void before(Class<?> targetclass, Method method, Object[] args) throws Throwable {
        System.out.println("before执行");
        super.before(targetclass, method, args);
    }
}
