package com.kiki.target.module.prize;

import com.kiki.target.common.model.Prize;
import com.kiki.target.common.vo.factory.BaseFactory;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月5日
 */
public class PrizeVoFactory extends BaseFactory<Prize, PrizeVo>{

	/**
	 * Title:
	 * Description:
	 * @param t
	 * @param clazz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author jjtEatJava
	 * @date 2018年2月5日
	 */
	public PrizeVoFactory(Prize t) throws InstantiationException, IllegalAccessException {
		super(t, PrizeVo.class);
	}

	/* （非 Javadoc）
	 * @see com.kiki.target.common.vo.factory.BaseFactory#doOtherThingForVo(com.kiki.target.common.vo.Vo)
	 */
	@Override
	protected void doOtherThingForVo(PrizeVo vo) {
		vo.setStoreId(this.getTb().getStore().getId());
		vo.setSupervisionId(this.getTb().getSupervision().getId());
	}

}
