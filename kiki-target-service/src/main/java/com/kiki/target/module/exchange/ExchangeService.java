package com.kiki.target.module.exchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiki.target.common.model.Exchange;
import com.kiki.target.common.model.Prize;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.model.User;
import com.kiki.target.module.prize.PrizeService;
import com.kiki.target.module.user.UserService;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月5日
 */
@Service
public class ExchangeService {
	@Autowired
	ExchangeDao exchangeDao;
	@Autowired
	PrizeService prizeService;
	@Autowired
	UserService userService;

	/**
	 * Title:
	 * Description:
	 * @param object
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	@Transactional
	public Exchange add(String prizeId, User loginUser) {
		Prize prize = prizeService.findByIdAndEnabled(prizeId,true);
		if(prize==null||!prize.getSupervision().getUserByUserId().getId().equals(loginUser.getId()))
			throw new IllegalArgumentException("奖品Id为空");
		if(loginUser.getAmount()<prize.getScore())
			throw new IllegalArgumentException("积分不足无法兑换");
		loginUser.setAmount(loginUser.getAmount()-prize.getScore());
		loginUser.setUpdateTime(new Date());
		userService.save(loginUser);
		
		Exchange exchange = new Exchange();
		exchange.setCost(prize.getScore());
		exchange.setCreateTime(new Date());
		exchange.setEnabled(true);
		exchange.setPrize(prize);
		exchange.setSupervision(prize.getSupervision());
		exchange.setUpdateTime(new Date());
		return this.save(exchange);
	}

	/**
	 * Title:
	 * Description:
	 * @param exchange
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	public Exchange save(Exchange exchange) {
		return this.exchangeDao.save(exchange);
	}

	/**
	 * Title:
	 * Description:
	 * @param supervisionId
	 * @param prizeId
	 * @param enabled
	 * @param pageable
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年3月18日
	 */
	public Page<Exchange> findBy(String supervisionId, Integer prizeId, Boolean enabled, Pageable pageable,
			User loginUser) {
		User user = userService.getPersistedUser(loginUser);
		if (!user.getSupervisionsForSuperintendentId().iterator().next().getId().equals(supervisionId)
				&&!user.getSupervisionsForUserId().iterator().next().getId().equals(supervisionId))
			throw new IllegalArgumentException("supervisionId 不存在");
		return exchangeDao.findAll(new Specification<Exchange>() {
			@Override
			public Predicate toPredicate(Root<Exchange> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 条件集合
				List<Predicate> predicates = new ArrayList<Predicate>();
				// supervisionId
				predicates.add(cb.equal(root.get("supervision").get("id"), supervisionId));
				//enabled
				if(enabled!=null)
					predicates.add(cb.equal(root.get("enabled"), enabled));	
				if(prizeId!=null)
					predicates.add(cb.equal(root.get("prizeId"), prizeId));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		}, pageable);
	}
}
