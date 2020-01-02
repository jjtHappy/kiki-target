/**
 * 
 */
package com.kiki.target.common.vo.factory;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.kiki.target.common.vo.Vo;

/**
 * @author jiangjintai
 *
 */
public class VoListBuilder{
	public static <T extends Object,V extends Vo>  List<V> builde(BaseFactory<T, V> f,List<T> list) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		List<V> vos = new ArrayList<V>(list.size());
		for(T t:list){
			f.setTb(t);
			vos.add(f.build());
		}
		return vos;
	}
}
