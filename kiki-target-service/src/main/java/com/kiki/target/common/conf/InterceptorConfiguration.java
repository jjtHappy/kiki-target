package com.kiki.target.common.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kiki.target.common.interceptor.LoginInterceptor;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	LoginInterceptor loginInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor);
		super.addInterceptors(registry);
	}
}
