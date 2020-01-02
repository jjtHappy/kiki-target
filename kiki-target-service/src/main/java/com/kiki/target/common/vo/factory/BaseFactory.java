/**
 * 
 */
package com.kiki.target.common.vo.factory;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import com.kiki.target.common.vo.Vo;





/**
 * @author jiangjintai
 *
 */
public abstract class BaseFactory<T extends Object,V extends Vo>{
	private T tb;
	private V vo;
	private Class<V> clazz;
	
	public BaseFactory(T t ,Class<V> clazz) throws InstantiationException, IllegalAccessException {
		this.clazz=clazz;
		this.tb=t;
	}
	
	public V build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		vo = clazz.newInstance();
		vo = BaseVoUtil.getVo(tb, vo);
		doOtherThingForVo(vo);
		return vo;
	}

	/**
	 * jiangjintai
	 * 2016年8月14日
	 * @param vo
	 */
	protected abstract void doOtherThingForVo(V vo);
	
	protected T getTb(){
		return this.tb;
	}
	
	public void setTb(T t){
		this.tb=t;
	}
}
