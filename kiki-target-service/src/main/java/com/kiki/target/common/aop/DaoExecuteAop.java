package com.kiki.target.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年5月5日
 */
@Aspect
@Component
public class DaoExecuteAop {
	
	private Logger logger = LoggerFactory.getLogger(DaoExecuteAop.class);
	  /**
     * 前置通知：目标方法执行之前执行以下方法体的内容 
     * @param jp
     */
    @Before("execution(* com.kiki.target.module..*Dao.*(..))")
    public void beforeMethod(JoinPoint jp){
        String methodName = jp.getSignature().getName();
        logger.info("【dao层前置通知】  【" + jp.getThis().getClass().getSimpleName()+"."+methodName + "】 begins with ");
        Object[] params =  jp.getArgs();
        for(Object object:params) {
        	logger.info(object.toString());
        }
    }
}
