package com.kiki.target.module.reviewed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kiki.target.common.model.Reviewed;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月28日
 */
public interface ReviewedDao extends JpaRepository<Reviewed, String>,JpaSpecificationExecutor<Reviewed>{

	/**
	 * Title:
	 * Description:
	 * @param reviewedId
	 * @param enabled
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	Reviewed findByIdAndEnabled(String reviewedId, boolean enabled);

}
