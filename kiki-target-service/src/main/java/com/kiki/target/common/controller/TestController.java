package com.kiki.target.common.controller;


import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiki.target.common.response.ResultMapBuilder;
import com.kiki.target.module.target.TargetService;
import com.kiki.target.module.user.UserService;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	UserService userService;
	@Autowired
	TargetService tarService;
	
	@RequestMapping("/sayHello")
	@ResponseBody
	public Map<String,Object> sayHello(){
		return ResultMapBuilder.buildSuccessMap("hello");
	}
	
	@RequestMapping("/error")
	@ResponseBody
	public Map<String,Object> error(){
		throw new IllegalArgumentException("参数错误");
	}
	
	@RequestMapping("/date")
	@ResponseBody
	public Map<String,Object> date(Date date){
		return ResultMapBuilder.buildSuccessMap(date.getTime()+""+"asdda");
	}
	
	@RequestMapping("/transactional")
	public Map<String,Object> transactional(){
		userService.testTransactional();
		return  null;
	}
	
	@RequestMapping("/expireCheck")
	@ResponseBody
	public Map<String,Object> expireCheck(){
		int[] expire = tarService.expireCheck();
		return ResultMapBuilder.buildSuccessMap("修改了"+expire+"过期信息");
		
	}
}
