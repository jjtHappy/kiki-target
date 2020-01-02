package com.kiki.target.service;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.kiki.target.common.model.User;
import com.kiki.target.module.user.UserService;


/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
public class TestUserService {
	@Test
	public void createToken() {
		User user = new User();
		user.setId("5af35205-3e84-42e3-bd92-fb9f58d3dfa5");
		UserService userService = new UserService();
		System.out.println(userService.token(user));
		
	}
	
	@Test
	public void getUserByToken() {
		String token = "AfVRr3JdT4iREqk3Vz+uK198pPSC2vOUyhEi62Sk3XkKc99U1U3Sm6mkwYc78g8K";
		UserService userService = new UserService();
		User user = userService.getUserByToken(token);
		Assert.assertNotNull(user);
		System.out.println(user.getId());
	}
	
	@Test
	public void createUUID() {
		System.out.println(UUID.randomUUID().toString());
	}
	
	@Test
	public void testIgnore() {
		String pattern = "/test/";
		String pattern2 = "/user/login";
		Assert.assertTrue("/test".matches(pattern));
		Assert.assertTrue("/user/login".matches(pattern2));
	}
}
