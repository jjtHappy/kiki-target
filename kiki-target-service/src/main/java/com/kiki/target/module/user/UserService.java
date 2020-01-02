package com.kiki.target.module.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kiki.target.common.model.Supervision;
import com.kiki.target.common.model.User;
import com.xiaoleilu.hutool.crypto.CryptoException;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.crypto.symmetric.DES;
import com.xiaoleilu.hutool.date.DateField;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.lang.Base64;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Service
public class UserService {
	@Value("${security.secure.key}")
	private   String SECURE_KEY="kikitarget"; 
	
	@Autowired
	private UserDao userDao;
	/**
	 * 
	 * Title:根据一个用户对象获取一个token
	 * Description:
	 * @param user
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月27日
	 */
	public String token(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append(user.getId());
		sb.append("|");
		sb.append(DateUtil.format(new Date(), "yyyy-MM-dd"));
		try {
			return Base64.encode(SecureUtil.des(SECURE_KEY.getBytes()).encrypt(sb.toString().getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public User getUserByToken(String token) {
		if(StrUtil.isBlank(token)) return null;
		try {
			String str = new String(SecureUtil.des(SECURE_KEY.getBytes()).decrypt(Base64.decode(token)),"utf-8");
			String[] strs = str.split("\\|");
			String userId=strs[0];
			String date = strs[1];
			//过期判断
			//注意，这里与hutool的逻辑正好相反，比当前时间晚是不过期
			if(!DateUtil.isExpired(DateUtil.parse(date, "yyyy-MM-dd"), DateField.HOUR_OF_DAY, 72, new Date())) return null;
			User user = this.findByIdAndEnabled(userId,true);
			return user;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * Title:
	 * Description:
	 * @param userId
	 * @param i
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月27日
	 */
	public  User findByIdAndEnabled(String userId, boolean state) {
		return userDao.findByIdAndEnabled(userId,state);
	}

	/**
	 * Title:
	 * Description:
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	public User getPersistedUser(User loginUser) {
		return this.findByIdAndEnabled(loginUser.getId(), true);
	}

	/**
	 * Title:
	 * Description:
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	@Transactional
	public void testTransactional() {
		User user = new User();
		this.save(user);
		throw new IllegalArgumentException("参数错误");
	}

	/**
	 * Title:
	 * Description:
	 * @param user
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	public void save(User user) {
		userDao.save(user);
	}

	/**
	 * Title:
	 * Description:
	 * @param account
	 * @param password
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月29日
	 */
	public User login(String account, String password) {
		User user = userDao.findByAccountAndPasswordAndEnabled(account,password,true);
		if(user==null)
			throw new IllegalArgumentException("账户或密码不正确");
		return user;
	}
}
