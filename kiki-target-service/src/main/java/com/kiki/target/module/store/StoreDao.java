package com.kiki.target.module.store;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki.target.common.model.Store;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月4日
 */
public interface StoreDao extends JpaRepository<Store, String>{

	/**
	 * Title:
	 * Description:
	 * @param storeId
	 * @param b
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月4日
	 */
	Store findByIdAndEnabled(String storeId, boolean enabled);

}
