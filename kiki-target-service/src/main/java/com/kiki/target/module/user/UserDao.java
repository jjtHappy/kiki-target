package com.kiki.target.module.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki.target.common.model.User;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月28日
 */
public interface UserDao extends JpaRepository<User, String> {

	/**
	 * Title:
	 * Description:
	 * @param userId
	 * @param i
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	User findByIdAndEnabled(String userId, boolean state);

	/**
	 * Title:
	 * Description:
	 * @param account
	 * @param password
	 * @param b
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月29日
	 */
	User findByAccountAndPasswordAndEnabled(String account, String password, boolean b);

}
