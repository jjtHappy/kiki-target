package com.kiki.target.module.exchange;

import com.kiki.target.common.model.Exchange;
import com.kiki.target.common.vo.factory.BaseFactory;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 */
public class ExchangeVoFactory extends BaseFactory<Exchange, ExchangeVo>{

	/**
	 * Title:
	 * Description:
	 * @param t
	 * @param clazz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	public ExchangeVoFactory(Exchange t)
			throws InstantiationException, IllegalAccessException {
		super(t, ExchangeVo.class);
	}

	/* （非 Javadoc）
	 * @see com.kiki.target.common.vo.factory.BaseFactory#doOtherThingForVo(com.kiki.target.common.vo.Vo)
	 */
	@Override
	protected void doOtherThingForVo(ExchangeVo vo) {
		vo.setSupervisionId(this.getTb().getSupervision().getId());
		vo.setPrizeId(this.getTb().getPrize().getId());
		vo.setPrizeName(this.getTb().getPrize().getName());
	}

}
