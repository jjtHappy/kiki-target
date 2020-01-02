package com.kiki.target.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kiki.target.common.response.ResultMapBuilder;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Aspect
@Component
public class ControllerExceptionAop {
	private Logger logger = LoggerFactory.getLogger(ControllerExceptionAop.class);
	/** 
	 * 环绕通知： 
	 *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。 
	 *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型 
	 */  
	@Around("execution(* com.kiki.target.module..*Controller.*(..))")  
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){  
	    try {  
	        Object obj = proceedingJoinPoint.proceed();  
	        return obj;  
	    } catch (IllegalArgumentException e) {  
	    	logger.debug("error argument " +e.getMessage());
	    	return ResultMapBuilder.buildErrorMap(e.getMessage());
	    } catch (Exception e) {
	    	logger.error("error",e);
	    	return ResultMapBuilder.buildErrorMap(e.getMessage());
		} catch (Throwable e) {
			logger.error("error",e);
	    	return ResultMapBuilder.buildErrorMap(e.getMessage());
		} 
	}  
}
