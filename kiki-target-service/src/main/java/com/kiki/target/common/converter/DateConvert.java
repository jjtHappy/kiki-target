package com.kiki.target.common.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Component("dataConvert")
public class DateConvert implements Converter<String,Date>{

	/* （非 Javadoc）
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public Date convert(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			throw new IllegalArgumentException("时间格式错误："+source);
		}
	}

}
