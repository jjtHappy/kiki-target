package com.kiki.target.module.prize;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kiki.target.common.model.Prize;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月5日
 */
public interface PrizeDao extends JpaRepository<Prize, String>,JpaSpecificationExecutor<Prize>{

	/**
	 * Title:
	 * Description:
	 * @param prizeId
	 * @param enabled
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	Prize findByIdAndEnabled(String prizeId, boolean enabled);

}
