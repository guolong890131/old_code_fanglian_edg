package com.funi.platform.edg.annotation;

import java.lang.reflect.Method;

import com.funi.platform.edg.bo.SystemLog;
import com.funi.platform.edg.service.SystemLogService;
import com.funi.platform.edg.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Package: [com.funi.platform.edg.annotation]
 * @Description: [一句话描述该类的功能]
 * @Author: [tangpeng]
 * @CreateDate: [2016/12/26 0026 11:11]
 * @UpdateUser: [tangpeng(如多次修改保留历史记录, 增加修改记录)]
 * @UpdateDate: [2016/12/26 0026 11:11，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录, 增加修改记录)]
 * @Version: [v1.0]
 */
@Aspect
@Component
public class SystemLogAspect implements Ordered {

    //注入Service用于把日志保存数据库
    @Resource
    private SystemLogService systemLogService;

    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);


    //Controller层切点
    @Pointcut("execution (* com.funi.platform.edg.controller..*.*(..))")
    public void controllerAspect() {
    }


    /**
     * 后置通知 用于拦截有@OperLog标签的方法，记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("@annotation(com.funi.platform.edg.annotation.OperLog)")
    public  void after(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户id
        if(null == session.getAttribute("uuid")){
            return;
        }
        String userid = session.getAttribute("uuid").toString();
        //请求的IP
        String ip = request.getRemoteAddr();
        try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(OperLog.class).operationType();
                        operationName = method.getAnnotation(OperLog.class).operationName();
                        break;
                    }
                }
            }
            //*========数据库日志=========*//
            SystemLog log = new SystemLog();
            log.setId(userid);
            log.setOperation(operationType+"-"+operationName);
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType+"-"+operationName);
            log.setRequestip(ip);
            //保存数据库i
            systemLogService.insert(log);
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("==执行controller后置操作日志记录异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning("@annotation(com.funi.platform.edg.annotation.OperLog)")
    public void afterReturn(JoinPoint joinPoint){
        System.out.println("=====已记录系统日志，日志切入点为：");
        if(logger.isInfoEnabled()){
            logger.info("afterReturn " + joinPoint);
        }
    }

    /**
     * 异常通知 用于拦截记录用户操作异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "@annotation(com.funi.platform.edg.annotation.OperLog)", throwing="e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户id
        if(null == session.getAttribute("uuid")){
            return;
        }
        String userid = session.getAttribute("uuid").toString();
        //请求的IP
        String ip = request.getRemoteAddr();

        String params = "";
        if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {
            for ( int i = 0; i < joinPoint.getArgs().length; i++) {
                params += StringUtils.getJsonStr(joinPoint.getArgs()[i]) + ";";
            }
        }
        try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(OperLog.class).operationType();
                        operationName = method.getAnnotation(OperLog.class).operationName();
                        break;
                    }
                }
            }
               /*==========数据库日志=========*/
            SystemLog log = new SystemLog();
            log.setId(userid);
            log.setOperation(operationType+"-"+operationName);
            log.setExceptioncode(e.getClass().getName());
            log.setExceptionDetail(e.getMessage());
            log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setRequestip(ip);
            //保存数据库
            systemLogService.insert(log);
        }  catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
         /*==========记录本地异常日志==========*/
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
