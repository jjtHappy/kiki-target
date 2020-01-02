/**
 * 
 */
package com.kiki.target.common.vo.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;




/**
 * @author jiangjintai
 * @param <V>
 *
 */
public  class BaseVoUtil {
	
	public static final Logger logger = Logger.getLogger(BaseVoUtil.class);
	
	public static <T,V>  V getVo(T tb,V vo) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//1.获取vo的全部属性值
		Field[]  fields = vo.getClass().getDeclaredFields();//获取所有域名
		
		
		//2.获取tb的全部属性名
		Field[] fieldsTb = tb.getClass().getDeclaredFields();
		
		List<String> fieldNameList = new LinkedList<String>();
		for(Field field : fieldsTb){//把tb里面所以属性藏起来
			fieldNameList.add(field.getName());
		}

		
		for(Field field : fields){//遍历vo里面所有字段
			//获取vo里面的写方法
			PropertyDescriptor voPropDesc=new PropertyDescriptor(field.getName(),vo.getClass());
	        Method methodWrite =voPropDesc.getWriteMethod();
	        //获取tb里面的读方法
	        if(fieldNameList.remove(field.getName())){
	        	logger.debug(field.getName());
		        PropertyDescriptor tbPropDesc=new PropertyDescriptor(field.getName(),tb.getClass());
		        Method methodRead =tbPropDesc.getReadMethod();
		        methodWrite.invoke(vo,methodRead.invoke(tb));
		        logger.debug(field.getName()+" success");
	        }
	        
		}
		return vo;
	}
}
