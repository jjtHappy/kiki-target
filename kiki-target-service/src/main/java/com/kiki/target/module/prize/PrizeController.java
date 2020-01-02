package com.kiki.target.module.prize;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kiki.target.common.model.Prize;
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
 * @date 2018年2月1日
 */
@Controller
@RequestMapping("/prize")
public class PrizeController {
	@Autowired
	PrizeService prizeService;
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(@RequestBody Map<String,Object> param,@RequestAttribute("authUser") User loginUser) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		validateAdd(param);
		Prize prize = prizeService.add((String)param.get("name"),(String)param.get("storeId"),new BigDecimal((String) param.get("worth")),(Integer)param.get("score"),loginUser);
		return ResultMapBuilder.buildSuccessMap(new PrizeVoFactory(prize).build(), null);
	}
	
	@RequestMapping("/findall")
	@ResponseBody
	public Map<String,Object> findall(
			String supervisionId,
			Boolean enabled,
			 Integer page,
			Integer size,
			 String sort,
			 @RequestAttribute("authUser")User loginUser,
			@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10, sort = {
					"createTime" }) Pageable pageable
			) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		if(StrUtil.isBlank(supervisionId)) throw new IllegalArgumentException("supervisionId不能为空");
		Page<Prize> prizes = prizeService.findBy(supervisionId,enabled,pageable,loginUser);
		return ResultMapBuilder.buildSuccessMap(VoListBuilder.builde(new PrizeVoFactory(null), prizes.getContent()), null,PageVoUtil.getVo(prizes));
	}

	/**
	 * Title:
	 * Description:
	 * @param prize
	 * @author jjtEatJava
	 * @date 2018年2月5日
	 */
	private void validateAdd(Map<String,Object> param) {
		validateCommon(param);
	}

	/**
	 * Title:
	 * Description:
	 * @param prize
	 * @author jjtEatJava
	 * @date 2018年2月5日
	 */
	private void validateCommon(Map<String,Object> param) {
		String name = param.get("name")==null||!(param.get("name") instanceof String)?"":(String) param.get("name");
		String storeId = param.get("storeId")==null||!(param.get("storeId") instanceof String)?"":(String) param.get("storeId");
		BigDecimal worth = null;
		try {
			worth =new BigDecimal((String)param.get("worth"));
		}catch (ClassCastException|NullPointerException|NumberFormatException e) {
			worth=new BigDecimal(-1);
		}
		Integer score = param.get("score")==null||!(param.get("score") instanceof Integer)?-1:(Integer) param.get("score");
		
		if(StrUtil.isBlank(name)) throw new IllegalArgumentException("奖励名不能空");
		if(StrUtil.isBlank(storeId)) throw new IllegalArgumentException("请上传图片");
		if(worth.compareTo(new BigDecimal(0))<=0) throw new IllegalArgumentException("商品价格不能小于等于0,且需要保留小数点后两位");
		if(score<=0) throw new IllegalArgumentException("兑换分数不能小于等于0,且必须为整数");	
	}
}
