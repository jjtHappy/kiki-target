package com.kiki.target.module.prize;

import java.math.BigDecimal;
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

import com.kiki.target.common.model.Prize;
import com.kiki.target.common.model.Store;
import com.kiki.target.common.model.User;
import com.kiki.target.module.store.StoreService;
import com.kiki.target.module.user.UserService;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年2月1日
 */
@Service
public class PrizeService {

	@Autowired
	PrizeDao prizeDao;
	@Autowired
	StoreService storeService;
	@Autowired
	UserService userService;

	/**
	 * 
	 * Title: Description:
	 * 
	 * @param name
	 * @param storeId
	 * @param worth
	 * @param score
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月5日
	 */
	public Prize add(String name, String storeId, BigDecimal worth, int score, User loginUser) {
		loginUser = userService.getPersistedUser(loginUser);
		if (loginUser.getSupervisionsForSuperintendentId().size() < 0)
			throw new IllegalArgumentException("当前用户不是一个监督者");
		Store store = storeService.findByIdAndEnabled(storeId);
		if (store == null)
			throw new IllegalArgumentException("请上传图片");
		Prize prize = new Prize();
		prize.setCreateTime(new Date());
		prize.setEnabled(true);
		prize.setName(name);
		prize.setScore(score);
		prize.setStore(store);
		prize.setSupervision(loginUser.getSupervisionsForSuperintendentId().iterator().next());
		prize.setUpdateTime(new Date());
		prize.setWorth(worth);
		return this.save(prize);
	}

	/**
	 * Title: Description:
	 * 
	 * @param prize
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月5日
	 */
	public Prize save(Prize prize) {
		return prizeDao.save(prize);
	}

	/**
	 * Title: Description:
	 * 
	 * @param supervisionId
	 * @param pageable
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月5日
	 */
	public Page<Prize> findBy(String supervisionId,Boolean enabled, Pageable pageable, User loginUser) {
		loginUser = userService.getPersistedUser(loginUser);
		if (!(loginUser.getSupervisionsForSuperintendentId().size() > 0
				&& loginUser.getSupervisionsForSuperintendentId().iterator().next().getId().equals(supervisionId))
				&& !(loginUser.getSupervisionsForUserId().size() > 0
						&& loginUser.getSupervisionsForUserId().iterator().next().getId().equals(supervisionId)))
			throw new IllegalArgumentException("supervisionId 不存在");
			return prizeDao.findAll(new Specification<Prize>() {
				
				@Override
				public Predicate toPredicate(Root<Prize> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					// 条件集合
					List<Predicate> predicates = new ArrayList<Predicate>();
					// supervisionId
					predicates.add(cb.equal(root.get("supervision").get("id"), supervisionId));
					//enabled
					if(enabled!=null)
						predicates.add(cb.equal(root.get("enabled"), enabled));	
					return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
				}
			}, pageable);
	}

	/**
	 * Title:
	 * Description:
	 * @param prizeId
	 * @param b
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月6日
	 */
	public Prize findByIdAndEnabled(String prizeId, boolean enabled) {
		return prizeDao.findByIdAndEnabled(prizeId,enabled);
	}

}
