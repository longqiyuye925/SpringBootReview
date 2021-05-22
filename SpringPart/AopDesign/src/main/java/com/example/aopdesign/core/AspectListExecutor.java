package com.example.aopdesign.core;

import com.example.aopdesign.espect.AspectInfo;
import lombok.Getter;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.validation.ValidationUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description:像被代理类中加入横切逻辑，其中运用模板方法设计模式
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
public class AspectListExecutor implements MethodInterceptor {
    //被代理的类
    private Class<?> targetClss;
    @Getter
    private List<AspectInfo> aspectInfoList;

    public AspectListExecutor(Class<?> targetClss, List<AspectInfo> aspectInfoList) {
        this.targetClss = targetClss;
        aspectInfoList = sortedAspectInfoList(aspectInfoList);
        this.aspectInfoList = aspectInfoList;
    }

    /**
     * 按照order的值升序排序，确保，order值较小的aspect优先织入
     *
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortedAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {

            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        //精确晒出符合表达式的切面
        collectAccurateMatchedAspectList(method);
        //如果没有剩余切面，则直接生成代理对象
        if (ValidationUtil.isEmpty(aspectInfoList)) {
            returnValue = methodProxy.invokeSuper(obj, args);
        }
        //1 按照order的顺序升序执行完所有aspect的before犯法
        invokeBeforeAdvices(method, args);
        try {
            //2执行被代理类的方法（用代理对象和被代理方法参数）
            methodProxy.invokeSuper(obj, args);
            //3 如果被代理方法正常返回，则按照order的降序执行完所有aspect中的afterReturning
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Throwable throwable) {
            //4 如果被代理方法抛出异常，则按照order的顺序降序执行完所有Aspect的afterThrowing
            invokeAfterThrowingAdvices(method, args, throwable);
        }
        return returnValue;
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Throwable throwable) throws Throwable {
        for (int i = aspectInfoList.size() - 1; i >= 0; i--) {
            aspectInfoList.get(i).getDefaultAspect().afterThrowing(targetClss, method, args);
        }
    }

    @SneakyThrows
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for (int i = aspectInfoList.size() - 1; i >= 0; i--) {
            return result = aspectInfoList.get(i).getDefaultAspect().afterReturning(targetClss, method, args, returnValue);
        }
        return result;
    }

    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : aspectInfoList) {
            aspectInfo.getDefaultAspect().before(targetClss, method, args);
        }
    }

    private void collectAccurateMatchedAspectList(Method method) {
        if (ValidationUtil.isEmpty(aspectInfoList)) {
            return;
        }
        Iterator<AspectInfo> it = aspectInfoList.iterator();
        while (it.hasNext()) {
            AspectInfo aspectInfo = it.next();
            if (!aspectInfo.getPointCutLocator().accurateMatches(method)) {
                it.remove();
            }
        }
    }

}
