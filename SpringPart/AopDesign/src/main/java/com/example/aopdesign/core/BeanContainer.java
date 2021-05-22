package com.example.aopdesign.core;

import com.example.aopdesign.annotation.Aspect;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 根据注解筛选出bean的class集合
     *
     * @param aspectClass
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation(Class<Aspect> aspectClass) {
        //1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            return null;
        }
        //2 通过注解筛选被注解标记的class对象，并添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if (clazz.isAnnotationPresent(aspectClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    public Object getBean(Class<?> aspectClass) {
        return beanMap.get(aspectClass);
    }

    public Object addBean(Class<?> targetClass, Object proxy) {
        return beanMap.put(targetClass,proxy);
    }

    /**
     * @Description:使用内部枚举类单列模式模式创建bean容器对象
     * @return: null
     */
    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }
}
