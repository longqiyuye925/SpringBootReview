package com.example.aopdesign.core;

import com.example.aopdesign.annotation.Aspect;
import com.example.aopdesign.annotation.Order;
import com.example.aopdesign.espect.AspectInfo;
import com.example.aopdesign.espect.DefaultAspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
public class AspectWeaver {
    private BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        //1 获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }
        //2 拼接AspectInfoList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        //3 遍历容器中的类
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class<?> targetClass : classSet) {
            //排除AspectClass自身
            if (targetClass.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            //4 粗筛符合条件的Aspect
            List<AspectInfo> roughMatchAspectList = collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
            //5 尝试进行Aspect的织入
            wrapIfNecessary(roughMatchAspectList,targetClass);
        }
    }

    private void wrapIfNecessary(List<AspectInfo> roughMatchAspectList, Class<?> targetClass) {
        if(ValidationUtil.isEmpty(roughMatchAspectList)){
            return;
        }
        AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,roughMatchAspectList);
        Object proxy = ProxyCreator.createProxy(targetClass,aspectListExecutor);
        beanContainer.addBean(targetClass,proxy);
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList) {
            //粗筛
            if (aspectInfo.getPointCutLocator().roughMatches(targetClass)) {
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                Order orderTag = aspectClass.getAnnotation(Order.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                PointCutLocator pointCutLocator = new PointCutLocator(aspectTag.pointcut());
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), defaultAspect, pointCutLocator);
                aspectInfoList.add(aspectInfo);
            }
        }
        return aspectInfoList;
    }

    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) && aspectClass.isAnnotationPresent(Order.class)
                && DefaultAspect.class.isAssignableFrom(aspectClass);
    }
}
