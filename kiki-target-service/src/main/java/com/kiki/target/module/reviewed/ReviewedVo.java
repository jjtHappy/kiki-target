package com.kiki.target.module.reviewed;

import java.util.Date;

import com.kiki.target.common.model.Supervision;
import com.kiki.target.common.model.Target;
import com.kiki.target.common.vo.Vo;

/**
 * Title: Description:
 * 
 * @author jjtEatJava
 * @date 2018年1月31日
 */
public class ReviewedVo implements Vo{
	private String id;
	private int state;
	private Date createTime;
	private Date updateTime;
	private boolean enabled;
	private String comment;

	// 需要补充
	private String supervisionId;
	private String userId;
	
	private String targetId;
    private String targetContent;
    private Date targetDeadline;
    private int targetReward;
    private int targetPunishment;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSupervisionId() {
		return supervisionId;
	}
	public void setSupervisionId(String supervisionId) {
		this.supervisionId = supervisionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTargetContent() {
		return targetContent;
	}
	public void setTargetContent(String targetContent) {
		this.targetContent = targetContent;
	}
	public Date getTargetDeadline() {
		return targetDeadline;
	}
	public void setTargetDeadline(Date targetDeadline) {
		this.targetDeadline = targetDeadline;
	}
	public int getTargetReward() {
		return targetReward;
	}
	public void setTargetReward(int targetReward) {
		this.targetReward = targetReward;
	}
	public int getTargetPunishment() {
		return targetPunishment;
	}
	public void setTargetPunishment(int targetPunishment) {
		this.targetPunishment = targetPunishment;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
    
}
