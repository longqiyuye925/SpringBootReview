package com.example.iocdesign.core;

import com.example.iocdesign.Util.ClassUtil;
import com.example.iocdesign.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/25
 */
@Slf4j
public class DependencyInjector {
    private BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doIoc() {
        //1.遍历Bean容器中所有的class对象
        if (ValidationUtil.isEmpty(beanContainer.getClasses())) {
            log.warn("empty classset in BeanContainer");
            return;
        }
        for (Class<?> clazz : beanContainer.getClasses()) {
            //2.遍历class对象中所有的成员变量
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                //跳出当前循环
                continue;
            }
            for (Field field : fields) {
                //3.找出被Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();
                    //4 获取这些成员变量的类型
                    Class<?> fieldClass = field.getType();
                    //5.获取这些成员变量的类型在容器里对应的实例
                    Object fieldValue = getFieldInstanct(fieldClass, autowiredValue);
                    if (fieldValue == null) {
                        throw new RuntimeException("unable to inject relevant");
                    } else {
                        //6.通过反射将对应的成员变量实例注入到成员变量所在的类的实例里面
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }
            }

        }
    }

    /**
     * 根据Class在beanContainer里面获取其实例或者实现类
     *
     * @param fieldClass
     * @param autowiredValue
     * @return
     */
    private Object getFieldInstanct(Class<?> fieldClass, String autowiredValue) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null) {
            return fieldValue;
        } else {
            Class<?> implementedClass = getImplementClass(fieldClass, autowiredValue);
            if (implementedClass != null) {
                return beanContainer.getBean(implementedClass);
            } else {
                return null;
            }
        }
    }

    /**
     * 获取接口的实现类
     *
     * @param fieldClass
     * @param autowiredValue
     * @return
     */
    private Class<?> getImplementClass(Class<?> fieldClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassesBySupper(fieldClass);
        if (!ValidationUtil.isEmpty(classSet)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {
                if (classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    throw new RuntimeException("multiple implemented classes for" + fieldClass.getName() + "pleanse set @autowired's value to pick one");
                }
            } else {
                for (Class<?> clazz : classSet) {
                    if (autowiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
