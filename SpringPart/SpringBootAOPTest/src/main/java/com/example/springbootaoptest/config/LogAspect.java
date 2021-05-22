package com.example.springbootaoptest.config;

import com.example.springbootaoptest.dao.SysLogDao;
import com.example.springbootaoptest.entity.SysLog;
import com.example.springbootaoptest.inteface.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Description: 定义一个类，使用@aspect使其成为切面，切点为@Log注解标注的方法，使用@Around环绕通知
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/14
 */
@Aspect
@Component
public class LogAspect {
    @Autowired SysLogDao sysLogDao;

  @Pointcut("@annotation(com.example.springbootaoptest.inteface.Log)")
  public void pointcut(){};
  //环绕通知：前置+目标方法执行+后置
  @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
      Object result = null;
      //获取当前系统时间

      long beginTime = System.currentTimeMillis();
      try {
          // 执行方法
          result = point.proceed();
      } catch (Throwable e) {
          e.printStackTrace();
      }
      // 执行时长(毫秒)
      long time = System.currentTimeMillis() - beginTime;
      // 保存日志，这里相当于是加了后置，在执行完方法后记录的日志

      saveLog(point, time);
      return result;
  }
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        //getSignature可以返回：修饰符+包名+组件名（类名）+方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }
        //获取切入点所属对象的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取切入点的方法名
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }
        // 获取request
        //HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        //sysLog.setIp(IPUtils.getIpAddr(request));
        // 模拟一个用户名
        sysLog.setUsername("mrbird");
        sysLog.setTime((int) time);
        sysLog.setCreateTime(new Date());
        // 保存系统日志
        sysLogDao.saveSysLog(sysLog);
    }
}
