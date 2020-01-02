package com.kiki.target.module.prize;

import java.math.BigDecimal;
import java.util.Date;

import com.kiki.target.common.model.Store;
import com.kiki.target.common.model.Supervision;
import com.kiki.target.common.vo.Vo;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月5日
 */
public class PrizeVo implements Vo{
	private String id;
	private String name;
	private BigDecimal worth;
	private int score;
	private Date createTime;
	private Date updateTime;
	private boolean enabled;
	
	//需要补充
	private String storeId;
	private String supervisionId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getWorth() {
		return worth;
	}
	public void setWorth(BigDecimal worth) {
		this.worth = worth;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getSupervisionId() {
		return supervisionId;
	}
	public void setSupervisionId(String supervisionId) {
		this.supervisionId = supervisionId;
	}
	
}
