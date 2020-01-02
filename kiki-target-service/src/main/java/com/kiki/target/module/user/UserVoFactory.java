package com.kiki.target.module.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.kiki.target.common.model.User;
import com.kiki.target.common.util.SpringUtil;
import com.kiki.target.common.vo.factory.BaseFactory;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月29日
 */
public class UserVoFactory extends BaseFactory<User, UserVo>{
	
	private UserService userService = SpringUtil.getBean(UserService.class);
	/**
	 * Title:
	 * Description:
	 * @param t
	 * @param clazz
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @author jjtEatJava
	 * @date 2018年1月29日
	 */
	public UserVoFactory(User t) throws InstantiationException, IllegalAccessException {
		super(t, UserVo.class);
	}

	/* （非 Javadoc）
	 * @see com.kiki.target.common.vo.factory.BaseFactory#doOtherThingForVo(com.kiki.target.common.vo.Vo)
	 */
	@Override
	protected void doOtherThingForVo(UserVo vo) {
		vo.setAuthToken(userService.token(getTb()));
		vo.setSupervisionsIdForSuperintendentId(this.getTb().getSupervisionsForSuperintendentId().iterator().next().getId());
		vo.setSupervisionsIdForUserId(this.getTb().getSupervisionsForUserId().iterator().next().getId());
		vo.setSuperintendent(this.getTb().getSupervisionsForUserId().iterator().next().getUserBySuperintendentId().getName());
	}

}
