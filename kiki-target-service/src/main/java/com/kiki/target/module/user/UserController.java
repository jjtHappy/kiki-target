package com.kiki.target.module.user;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiki.target.common.model.User;
import com.kiki.target.common.response.ResultMapBuilder;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月29日
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(@RequestBody User user) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		validateLogin(user);
		User userDo = userService.login(user.getAccount(),user.getPassword());
		return ResultMapBuilder.buildSuccessMap(new UserVoFactory(userDo).build(), null);
	}
	
	@RequestMapping(value="/info",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> info(@RequestAttribute("authUser")User loginUser) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		User user  = userService.getPersistedUser(loginUser);
		return ResultMapBuilder.buildSuccessMap(new UserVoFactory(user).build(), null);
	}
	
	//监督的人的信息
	@RequestMapping(value="/supervised/info",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> superintendentInfo(@RequestAttribute("authUser")User loginUser) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		User user  = userService.getPersistedUser(loginUser);
		User supervisedUser = user.getSupervisionsForSuperintendentId().iterator().next().getUserByUserId();
		Map<String,Object> supervisedUserMap = new HashMap<String,Object>();
		supervisedUserMap.put("name", supervisedUser.getName());
		supervisedUserMap.put("amount",  supervisedUser.getAmount());
		return ResultMapBuilder.buildSuccessMap(supervisedUserMap, null);
	}
	
	
	/**
	 * Title:
	 * Description:
	 * @param user
	 * @author jjtEatJava
	 * @date 2018年1月29日
	 */
	private void validateLogin(User user) {
		if(StrUtil.isBlank(user.getAccount())) throw new IllegalArgumentException("帐号不能为空");
		if(StrUtil.isBlank(user.getPassword())) throw new IllegalArgumentException("密码不能为空");
	}
}
