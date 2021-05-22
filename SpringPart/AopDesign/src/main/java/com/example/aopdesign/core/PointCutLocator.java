package com.example.aopdesign.core;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
public class PointCutLocator {
    /**
     * Poincut解析器，直接给它赋值上AspectJ的所有表达式，以便支持对众多表达式的解析。
     */
    private PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
            PointcutParser.getAllSupportedPointcutPrimitives()
    );
    private PointcutExpression pointcutExpression;

    /**
     * 表达式解析器
     *
     * @param expression
     */
    public PointCutLocator(String expression) {
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 判断传入的对象是不是Aspect的目标代理类，即匹配PointCut表达式（粗筛选）
     *
     * @param targetClass
     * @return
     */
    public boolean roughMatches(Class<?> targetClass) {
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     * 判断传入的Method对象是否是Aspect的目标dialing方法，即匹配PointCut表达式（精筛选）
     * @param method
     * @return
     */
    public boolean accurateMatches(Method method) {
        ShadowMatch shadowMatch = pointcutExpression.matchesAdviceExecution(method);
        if (shadowMatch.alwaysMatches()) {
            return true;
        } else {
            return false;
        }
    }
}
