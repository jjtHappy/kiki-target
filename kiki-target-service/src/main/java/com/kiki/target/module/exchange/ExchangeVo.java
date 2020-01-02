package com.kiki.target.module.exchange;

import java.util.Date;

import com.kiki.target.common.model.Prize;
import com.kiki.target.common.model.Supervision;
import com.kiki.target.common.vo.Vo;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月6日
 */
public class ExchangeVo implements Vo{
	private String id;
	private Date createTime;
	private Date updateTime;
	private boolean enabled;
	private int cost;
	
	//需要补充
	private String supervisionId;
	
	private String prizeName;
	private String prizeId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getSupervisionId() {
		return supervisionId;
	}
	public void setSupervisionId(String supervisionId) {
		this.supervisionId = supervisionId;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public String getPrizeId() {
		return prizeId;
	}
	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	
	
}
