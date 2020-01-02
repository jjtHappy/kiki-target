package com.kiki.target.module.target;

import java.util.Date;

import com.kiki.target.common.vo.Vo;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月28日
 */
public class TargetVo implements Vo{
    private String id;
    private String content;
    private Date deadline;
    private int reward;
    private int punishment;
    private int state;
    private Date createTime;
    private Date updateTime;
    private boolean enabled;
    
    //需要补充
    private String supervisionId;
    private String userId;
    
    private String stateName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
	public int getPunishment() {
		return punishment;
	}
	public void setPunishment(int punishment) {
		this.punishment = punishment;
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
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
