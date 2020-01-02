package com.kiki.target.module.reviewed;

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

import com.kiki.target.common.model.Reviewed;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.model.User;
import com.kiki.target.module.target.TargetService;
import com.kiki.target.module.user.UserService;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月28日
 */
@Service
public class ReviewedService {
	@Autowired
	ReviewedDao reviewedDao;
	@Autowired
	UserService userService;
	@Autowired
	TargetService targetService;
	/**
	 * Title:
	 * Description:
	 * @param reviewed
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	public Reviewed save(Reviewed reviewed) {
		return reviewedDao.save(reviewed);
	}
	/**
	 * Title:
	 * Description:
	 * @param state
	 * @param comment
	 * @param supervisionId
	 * @param pageable
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月31日
	 */
	public Page<Reviewed> findBy(Integer state, String comment,Boolean enabled, String supervisionId, Pageable pageable,
			User loginUser) {
		User user = userService.getPersistedUser(loginUser);
		if (!user.getSupervisionsForSuperintendentId().iterator().next().getId().equals(supervisionId)
				&&!user.getSupervisionsForUserId().iterator().next().getId().equals(supervisionId))
			throw new IllegalArgumentException("supervisionId 不存在");
		return reviewedDao.findAll(new Specification<Reviewed>() {
			@Override
			public Predicate toPredicate(Root<Reviewed> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// 条件集合
				List<Predicate> predicates = new ArrayList<Predicate>();
				// supervisionId
				predicates.add(cb.equal(root.get("supervision").get("id"), supervisionId));
				// content
				if (!StrUtil.isBlank(comment))
					predicates.add(cb.like(root.get("comment"), "%" + comment + "%"));
				//state
				if(state!=null)
					predicates.add(cb.equal(root.get("state"), state));
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
	 * @param reviedId
	 * @param reply
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	@Transactional
	public Reviewed verify(String reviewedId, boolean reply,User loginUser) {
		Reviewed reviewed = this.findByIdAndEnabled(reviewedId,true);
		User user = userService.getPersistedUser(loginUser);
		if(reviewed==null||!reviewed.getSupervision().getUserBySuperintendentId().getId().equals(user.getId()))
			throw new IllegalArgumentException("reviewedId 不存在");
		if(reviewed.getState()!=1)
			throw new IllegalArgumentException("该审核状态不允许改变");
		if(reply) {//同意的逻辑
			//同意的话直接调用TargetService 自身的方法去修改状态与添加积分
			targetService.reviewedTrue(reviewed.getTarget());
			reviewed.setState(2);
		}else {//不同意的逻辑
			//不同意的话直接调用TargetService 自身的方法去修改状态
			targetService.reviewedFalse(reviewed.getTarget());
			reviewed.setState(-1);
		}
		reviewed.setUpdateTime(new Date());
		this.save(reviewed);
		return reviewed;
	}
	/**
	 * Title:
	 * Description:
	 * @param reviewdId
	 * @param b
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	private Reviewed findByIdAndEnabled(String reviewedId, boolean enabled) {
		return reviewedDao.findByIdAndEnabled(reviewedId,enabled);
	}
	/**
	 * Title:
	 * Description:
	 * @param state
	 * @param comment
	 * @param enabled
	 * @param supervisionId
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年3月2日
	 */
	public long countBy(Integer state, String comment, Boolean enabled, String supervisionId, User loginUser) {
		User user = userService.getPersistedUser(loginUser);
		if (!user.getSupervisionsForSuperintendentId().iterator().next().getId().equals(supervisionId)
				&&!user.getSupervisionsForUserId().iterator().next().getId().equals(supervisionId))
			throw new IllegalArgumentException("supervisionId 不存在");
		return reviewedDao.count(new Specification<Reviewed>() {
			@Override
			public Predicate toPredicate(Root<Reviewed> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// 条件集合
				List<Predicate> predicates = new ArrayList<Predicate>();
				// supervisionId
				predicates.add(cb.equal(root.get("supervision").get("id"), supervisionId));
				// content
				if (!StrUtil.isBlank(comment))
					predicates.add(cb.like(root.get("comment"), "%" + comment + "%"));
				//state
				if(state!=null)
					predicates.add(cb.equal(root.get("state"), state));
				//enabled
				if(enabled!=null)
					predicates.add(cb.equal(root.get("enabled"), enabled));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
	}

}
