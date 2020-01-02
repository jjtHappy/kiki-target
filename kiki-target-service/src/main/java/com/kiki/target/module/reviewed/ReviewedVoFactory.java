package com.kiki.target.module.reviewed;

import com.kiki.target.common.model.Reviewed;
import com.kiki.target.common.vo.Vo;
import com.kiki.target.common.vo.factory.BaseFactory;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月31日
 */
public class ReviewedVoFactory extends BaseFactory<Reviewed,ReviewedVo> {

	/**
	 * Title:
	 * Description:
	 * @param t
	 * @param clazz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author jjtEatJava
	 * @date 2018年1月31日
	 */
	public ReviewedVoFactory(Reviewed t) throws InstantiationException, IllegalAccessException {
		super(t, ReviewedVo.class);
	}
	/* （非 Javadoc）
	 * @see com.kiki.target.common.vo.factory.BaseFactory#doOtherThingForVo(com.kiki.target.common.vo.Vo)
	 */
	@Override
	protected void doOtherThingForVo(ReviewedVo vo) {
		vo.setSupervisionId(this.getTb().getSupervision().getUserBySuperintendentId().getId());
		vo.setUserId(this.getTb().getSupervision().getUserByUserId().getId());
		
		vo.setTargetContent(this.getTb().getTarget().getContent());
		vo.setTargetDeadline(this.getTb().getTarget().getDeadline());
		vo.setTargetPunishment(this.getTb().getTarget().getPunishment());
		vo.setTargetReward(this.getTb().getTarget().getReward());
		vo.setTargetId(this.getTb().getTarget().getId());
		
	}

}
