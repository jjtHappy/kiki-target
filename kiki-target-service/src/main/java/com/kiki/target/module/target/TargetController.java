package com.kiki.target.module.target;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiki.target.common.model.Target;
import com.kiki.target.common.model.User;
import com.kiki.target.common.response.ResultMapBuilder;
import com.kiki.target.common.vo.factory.PageVoUtil;
import com.kiki.target.common.vo.factory.VoListBuilder;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Controller
@RequestMapping("/target")
public class TargetController {
	@Autowired
	TargetService targetService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@RequestBody Target target, @RequestAttribute("authUser") User loginUser)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			IntrospectionException {
		validateAdd(target);
		Target pTarget = targetService.add(target.getContent(), target.getDeadline(), target.getPunishment(),
				target.getReward(), loginUser);
		return ResultMapBuilder.buildSuccessMap(new TargetVoFactory(pTarget).build(), "添加成功");
	}

	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> complete(@RequestBody Map<String,String> params, @RequestAttribute("authUser") User loginUser)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			IntrospectionException {
		validateComplete(params.get("targetId"),params.get("comment"));
		Target target = targetService.complete(params.get("targetId"),params.get("comment"),loginUser);
		return ResultMapBuilder.buildSuccessMap(new TargetVoFactory(target).build(), "提交审核成功");
	}
	
	@RequestMapping(value="/findall",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findalll(
			String supervisionId,
			String content,
			Date deadline,
			Integer reward,
			Integer punishment,
			Boolean enabled,
			Integer state,
			 Integer page,
			Integer size,
			 String sort,
			 @RequestAttribute("authUser")User loginUser,
			@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10, sort = {
					"createTime" }) Pageable pageable
			) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		if(StrUtil.isBlank(supervisionId)) throw new IllegalArgumentException("supervisionId不能为空");
		Page<Target> targets = targetService.findBy(supervisionId,content,deadline,reward,punishment,state,enabled,pageable,loginUser);
		return ResultMapBuilder.buildSuccessMap(VoListBuilder.builde(new TargetVoFactory(null), targets.getContent()), null,PageVoUtil.getVo(targets));
	}

	/**
	 * Title: Description:
	 * 
	 * @param targetId
	 * @param comment
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	private void validateComplete(String targetId, String comment) {
		if (StrUtil.isBlank(targetId))
			throw new IllegalArgumentException("targetId不能为空");
		if (StrUtil.isBlank(comment))
			throw new IllegalArgumentException("内容不能为空");
	}

	/**
	 * Title: Description:
	 * 
	 * @param target
	 * @author jjtEatJava
	 * @date 2018年1月27日
	 */
	private void validateAdd(Target target) {
		validateTarget(target);
	}

	/**
	 * Title: Description:
	 * 
	 * @param target
	 * @author jjtEatJava
	 * @date 2018年1月27日
	 */
	private void validateTarget(Target target) {
		if (StrUtil.isBlank(target.getContent()))
			throw new IllegalArgumentException("内容不能为空");
		if (target.getDeadline() == null || target.getDeadline().before(DateUtil.parse(DateUtil.format(DateUtil.tomorrow(), "yyyy-MM-dd"))))
			throw new IllegalArgumentException("截止时间不能小于当前时间");
		if (target.getReward() <= 0)
			throw new IllegalArgumentException("奖励分数必须大于0");
		if (target.getPunishment() <= 0)
			throw new IllegalArgumentException("惩罚分数必须大于0");
	}
}
