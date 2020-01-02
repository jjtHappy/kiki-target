package com.kiki.target.common.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.kiki.target.common.response.ResultMapBuilder;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月27日
 */
//@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobelErrorController implements ErrorController{
	

	/* （非 Javadoc）
	 * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping
    @ResponseBody
    public Map<String,Object> doHandleError(HttpServletRequest request) {
		Object object = request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");
		if(object instanceof MethodArgumentTypeMismatchException) //参数类型错误
			return ResultMapBuilder.buildErrorMap(((MethodArgumentTypeMismatchException)object).getMessage());
		return ResultMapBuilder.buildErrorMap(object==null?"服务器异常":object.toString());
    }
}
