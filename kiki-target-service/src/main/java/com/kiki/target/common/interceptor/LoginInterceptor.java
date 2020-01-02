package com.kiki.target.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.kiki.target.common.model.User;
import com.kiki.target.common.response.ResultMapBuilder;
import com.kiki.target.module.user.UserService;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{
	@Value("${security.secure.request.ignore}")
	private String IGNORE_PATH="";
	
	@Autowired
	UserService userService;
	@Value("${security.secure.request.header}")
	private String AUTH_HEADER="KIKI_AUTH_TOKEN";

	/* （非 Javadoc）
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
		if(isIgnore(path)) return true;
		String token = request.getHeader(AUTH_HEADER);
		User user = userService.getUserByToken(token);
		if(user==null) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().append(JSON.toJSONString(ResultMapBuilder.buildErrorMap("尚未登录",401)));
			return false;
		}
		request.setAttribute("authUser", user);
		return true;
	}

	/**
	 * Title:
	 * Description:
	 * @param path
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	private boolean isIgnore(String path) {
		String[] patterns = IGNORE_PATH.split("\\|");
		for(String pattern:patterns) {
			//把*字符代替成正则表达式任意字符
			pattern=pattern.replace("*","[\\s\\S]*");
			if(path.matches(pattern)) return true;
		}
		return false;
	}

	/* （非 Javadoc）
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/* （非 Javadoc）
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
