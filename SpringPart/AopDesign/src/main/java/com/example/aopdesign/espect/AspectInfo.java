package com.example.aopdesign.espect;

import com.example.aopdesign.core.PointCutLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/21
 */
@AllArgsConstructor
@Getter
public class AspectInfo {
    private int orderIndex;
    private DefaultAspect defaultAspect;
    //pointcut解析器
    private PointCutLocator pointCutLocator;
}
