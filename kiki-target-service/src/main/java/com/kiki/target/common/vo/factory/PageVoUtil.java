/**
 * 
 */
package com.kiki.target.common.vo.factory;

import java.awt.print.Pageable;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;

import com.kiki.target.common.vo.PageVo;

/**
 * @author jiangjintai
 *
 */
public class PageVoUtil {
	public static <T> PageVo getVo(Page<T> page){
		PageVo pageVo = new PageVo();
		pageVo.setNumber(page.getNumber());
		pageVo.setTotalPages(page.getTotalPages());
		pageVo.setTotalElements(page.getTotalElements());
		return pageVo;
	}
}
