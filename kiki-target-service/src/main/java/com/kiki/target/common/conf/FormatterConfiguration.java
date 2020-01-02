package com.kiki.target.common.conf;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Configuration
public class FormatterConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	Converter<String, Date> dateConvert;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(dateConvert);
		super.addFormatters(registry);
	}
}
