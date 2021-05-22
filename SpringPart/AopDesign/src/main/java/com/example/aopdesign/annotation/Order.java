package com.example.aopdesign.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:控制类的执行顺序，越小，优先级越高
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    int value();
}
