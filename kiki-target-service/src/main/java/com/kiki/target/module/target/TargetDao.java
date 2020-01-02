package com.kiki.target.module.target;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kiki.target.common.model.Target;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
public interface TargetDao extends JpaRepository<Target, String>,JpaSpecificationExecutor<Target>{

	/**
	 * Title:
	 * Description:
	 * @param targetId
	 * @param state
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	Target findByIdAndEnabled(String targetId, boolean state);



}
