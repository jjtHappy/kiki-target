package com.kiki.target.module.exchange;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiki.target.common.model.Exchange;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.model.User;
import com.kiki.target.common.response.ResultMapBuilder;
import com.kiki.target.common.vo.factory.PageVoUtil;
import com.kiki.target.common.vo.factory.VoListBuilder;
import com.kiki.target.module.target.TargetVoFactory;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月5日
 */
@Controller
@RequestMapping("/exchange")
public class ExchangeController {
	@Autowired
	ExchangeService exchangeService;
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(@RequestBody Map<String,Object> params,@RequestAttribute("authUser")User loginUser) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		validateAdd(params);
		Exchange exchange = exchangeService.add((String)params.get("prizeId"),loginUser);
		return ResultMapBuilder.buildSuccessMap(new ExchangeVoFactory(exchange).build(), null);
	}
	
	@RequestMapping(value="/findall",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findalll(
			String supervisionId,
			Integer prizeId,
			Boolean enabled,
			 Integer page,
			Integer size,
			 String sort,
			 @RequestAttribute("authUser")User loginUser,
			@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10, sort = {
					"createTime" }) Pageable pageable
			) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		if(StrUtil.isBlank(supervisionId)) throw new IllegalArgumentException("supervisionId不能为空");
		Page<Exchange> exchanges = exchangeService.findBy(supervisionId,prizeId,enabled,pageable,loginUser);
		return ResultMapBuilder.buildSuccessMap(VoListBuilder.builde(new ExchangeVoFactory(null), exchanges.getContent()), null,PageVoUtil.getVo(exchanges));
	}

	/**
	 * Title:
	 * Description:
	 * @param params
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	private void validateAdd(Map<String, Object> params) {
		validateCommon(params);
	}

	/**
	 * Title:
	 * Description:
	 * @param params
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	private void validateCommon(Map<String, Object> params) {
		String prizeId = params.get("prizeId")==null||!(params.get("prizeId") instanceof String)?"":(String) params.get("prizeId");
		if(StrUtil.isBlank(prizeId)) throw new IllegalArgumentException("奖品id不能为空");
	}
}
