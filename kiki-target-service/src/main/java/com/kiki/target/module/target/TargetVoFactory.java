package com.kiki.target.module.target;

import com.kiki.target.common.model.Supervision;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.vo.factory.BaseFactory;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月28日
 */
public class TargetVoFactory extends BaseFactory<Target, TargetVo>{

	/**
	 * Title:
	 * Description:
	 * @param t
	 * @param clazz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	public TargetVoFactory(Target t) throws InstantiationException, IllegalAccessException {
		super(t, TargetVo.class);
	}

	/* （非 Javadoc）
	 * @see com.kiki.target.common.vo.factory.BaseFactory#doOtherThingForVo(com.kiki.target.common.vo.Vo)
	 */
	@Override
	protected void doOtherThingForVo(TargetVo vo) {
		Supervision supervision = this.getTb().getSupervision();
		vo.setSupervisionId(supervision.getUserBySuperintendentId().getId());
		vo.setUserId(supervision.getUserByUserId().getId());
		switch (this.getTb().getState()) {
		case 1:
			vo.setStateName("进行中");
			break;
		case 2:
			vo.setStateName("审核中");
			break;
		case 3:
			vo.setStateName("完成");
			break;
		case -1:
			vo.setStateName("超时");
			break;

		default:
			break;
		}
	}



}
