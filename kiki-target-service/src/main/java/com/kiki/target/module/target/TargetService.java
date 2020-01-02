package com.kiki.target.module.target;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiki.target.common.model.Reviewed;
import com.kiki.target.common.model.Supervision;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.model.User;
import com.kiki.target.module.reviewed.ReviewedService;
import com.kiki.target.module.user.UserService;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import javax.persistence.criteria.Order;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年1月27日
 */
@Service
public class TargetService {
	@Autowired
	TargetDao targetDao;
	@Autowired
	ReviewedService reviewedService;
	@Autowired
	UserService userService;
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Title: Description:
	 * 
	 * @param target
	 * @author jjtEatJava
	 * @param loginUser
	 * @date 2018年1月27日
	 */
	public Target add(String content, Date deadline, int punishment, int reward, User loginUser) {
		Target target = new Target();
		target.setContent(content);
		target.setDeadline(deadline);
		target.setPunishment(punishment);
		target.setReward(reward);

		target.setSupervision(userService.getPersistedUser(loginUser).getSupervisionsForUserId().iterator().next());
		target.setState(1);
		target.setCreateTime(new Date());
		target.setUpdateTime(new Date());
		target.setEnabled(true);
		return targetDao.save(target);
	}

	/**
	 * Title: Description:
	 * 
	 * @param targetId
	 * @param comment
	 * @return
	 * @author jjtEatJava
	 * @param loginUser
	 * @date 2018年1月28日
	 */
	@Transactional
	public Target complete(String targetId, String comment, User loginUser) {
		User user = userService.getPersistedUser(loginUser);
		Target target = this.findByIdAndEnabled(targetId, true);
		if (target == null || !target.getSupervision().getUserByUserId().getId().equals(loginUser.getId()))
			throw new IllegalArgumentException("targetId不存在");
		if (target.getState() > 1 || target.getDeadline().getTime() < new Date().getTime())
			throw new IllegalArgumentException("该任务状态无法更改");

		Reviewed reviewed = new Reviewed();
		reviewed.setComment(comment);
		reviewed.setCreateTime(new Date());
		reviewed.setEnabled(true);
		reviewed.setState(1);
		reviewed.setSupervision(user.getSupervisionsForUserId().iterator().next());
		reviewed.setTarget(target);
		reviewed.setUpdateTime(new Date());

		target.setState(2);
		target.setUpdateTime(new Date());
		this.save(target);
		this.reviewedService.save(reviewed);
		return target;
	}

	/**
	 * Title: Description:
	 * 
	 * @param target
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	public Target save(Target target) {
		return targetDao.save(target);
	}

	/**
	 * Title: Description:
	 * 
	 * @param targetId
	 * @param b
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月28日
	 */
	public Target findByIdAndEnabled(String targetId, boolean state) {
		return targetDao.findByIdAndEnabled(targetId, state);
	}

	/**
	 * Title: Description:
	 * 
	 * @param supervisionId
	 * @param content
	 * @param deadline
	 * @param reward
	 * @param punishment
	 * @param state
	 * @param pageable
	 * @param loginUser
	 * @return
	 * @author jjtEatJava
	 * @date 2018年1月29日
	 */
	public Page<Target> findBy(String supervisionId, String content, Date deadline, Integer reward, Integer punishment,
			Integer state,Boolean enabled, Pageable pageable, User loginUser) {
		User user = userService.getPersistedUser(loginUser);
		if (!user.getSupervisionsForSuperintendentId().iterator().next().getId().equals(supervisionId)
				&&!user.getSupervisionsForUserId().iterator().next().getId().equals(supervisionId))
			throw new IllegalArgumentException("supervisionId 不存在");
		return targetDao.findAll(new Specification<Target>() {
			@Override
			public Predicate toPredicate(Root<Target> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// 条件集合
				List<Predicate> predicates = new ArrayList<Predicate>();
				// supervisionId
				predicates.add(cb.equal(root.get("supervision").get("id"), supervisionId));
				// content
				if (!StrUtil.isBlank(content))
					predicates.add(cb.like(root.get("content"), "%" + content + "%"));
				// deadline 查找之前的时间
				if (deadline != null)
					predicates.add(
							cb.between(root.get("deadline"), DateUtil.parse("1970-01-01", "yyyy-MM-dd"), deadline));
				// reward
				if(reward!=null)
				predicates.add(cb.equal(root.get("reward"), reward));
				//punishment
				if(punishment!=null)
					predicates.add(cb.equal(root.get("punishment"), punishment));
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
	 * 清理过期的任务
	 * Title:
	 * Description:
	 * @author jjtEatJava
	 * @date 2018年1月30日
	 */
	@Transactional
	public int[] expireCheck() {
		//先执行扣分操作
		String punishUser = "UPDATE user u, ( SELECT u.id AS user_id, SUM(t.punishment) AS punishment FROM target t, supervision s, user u WHERE t.supervision_id = s.id AND s.user_id = u.id AND t.state <= 2 AND t.state >= 1 AND t.deadline <= now() GROUP BY u.id ) result SET u.amount = u.amount - result.punishment,u.update_time = now() WHERE u.id = result.user_id";
		//更新超时任务状态
		String updateExpireTargetState = "update target set state = -1,update_time = now() where state <= 2 and state >=1 and deadline <= now()";
		return jdbcTemplate.batchUpdate(punishUser,updateExpireTargetState);
	}

	/**
	 * Title:
	 * Description:
	 * @param target
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	@Transactional
	public void reviewedTrue(Target target) {
		target.setState(3);
		target.setUpdateTime(new Date());
		this.save(target);
		User user = target.getSupervision().getUserByUserId();
		user.setAmount(user.getAmount()+target.getReward());
		user.setUpdateTime(new Date());
		userService.save(user);
	}

	/**
	 * Title:
	 * Description:
	 * @param target
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	@Transactional
	public void reviewedFalse(Target target) {
		boolean isExpire = isExpire(target);
		if(isExpire) {
			target.setState(-1);
			target.setUpdateTime(new Date());	
		}else {
			target.setState(1);
			target.setUpdateTime(new Date());	
		}
		this.save(target);
	}

	/**
	 * Title:
	 * Description:
	 * @param target
	 * @return
	 * @author jjtEatJava
	 * @date 2018年2月1日
	 */
	private boolean isExpire(Target target) {
		
		return target.getDeadline().compareTo(new Date())<=0;
	}
}
