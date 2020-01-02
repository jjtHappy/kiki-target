package com.kiki.target.module.reviewed;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiki.target.common.model.Reviewed;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.model.User;
import com.kiki.target.common.response.ResultMapBuilder;
import com.kiki.target.common.vo.factory.PageVoUtil;
import com.kiki.target.common.vo.factory.VoListBuilder;
import com.kiki.target.module.target.TargetVoFactory;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年1月31日
 */
@Controller
@RequestMapping("/reviewed")
public class ReviewedController {
	@Autowired
	ReviewedService reviewedService;

	@RequestMapping("/findall")
	@ResponseBody
	public Map<String, Object> findall(Integer state, String comment, String supervisionId,Boolean enabled, Integer page, Integer size,
			String sort, @RequestAttribute("authUser") User loginUser,
			@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10, sort = {
					"createTime" }) Pageable pageable)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			IntrospectionException {
		if (StrUtil.isBlank(supervisionId))
			throw new IllegalArgumentException("supervisionId不能为空");
		Page<Reviewed> reviewds = reviewedService.findBy(state, comment,enabled, supervisionId, pageable, loginUser);
		return ResultMapBuilder.buildSuccessMap(
				VoListBuilder.builde(new ReviewedVoFactory(null), reviewds.getContent()), null,
				PageVoUtil.getVo(reviewds));
	}
	
	@GetMapping(value="/count")
	@ResponseBody
	public Map<String,Object> count(String targetId,Integer state,Boolean enabled,String comment, String supervisionId,@RequestAttribute("authUser")User loginUser){
		if (StrUtil.isBlank(supervisionId))
			throw new IllegalArgumentException("supervisionId不能为空");
		long count = reviewedService.countBy(state, comment,enabled, supervisionId, loginUser);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", count);
		return ResultMapBuilder.buildSuccessMap(map,null);
	}

	@RequestMapping(value="/verify",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verify(@RequestBody Map<String,Object> params,@RequestAttribute("authUser")User loginUser) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String reviewedId = (String) params.get("reviewedId");
		boolean reply = (boolean) params.get("reply");
		if (StrUtil.isBlank(reviewedId))
			throw new IllegalArgumentException("reviewedId不能为空");
		Reviewed review = reviewedService.verify(reviewedId,reply,loginUser);
		return ResultMapBuilder.buildSuccessMap(new ReviewedVoFactory(review).build(), null);
	}
}
