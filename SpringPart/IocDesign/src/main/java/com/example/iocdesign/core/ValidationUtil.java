package com.example.iocdesign.core;

import java.util.Collection;
import java.util.Map;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
public class ValidationUtil {
    /**
     * string是否为null或者“”
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj) {
        return (obj == null || "".equals(obj));
    }

    /**
     * array是否为null或者size为0
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * collection是否为null或者size为0
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Map是否为null或size为0
     * @param obj
     * @return
     */
    public static boolean isEmpty(Map<?,?> obj){return obj == null || obj.isEmpty(); }
}
