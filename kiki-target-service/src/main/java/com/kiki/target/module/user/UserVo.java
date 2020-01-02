package com.kiki.target.module.user;

import java.util.Date;

import com.kiki.target.common.vo.Vo;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年1月29日
 */
public class UserVo implements Vo{
	private String id;
	private String account;
	private String name;
	private int amount;
	private Date createTime;
	private Date updateTime;
	
	//需要补充
	private String authToken;
	private String supervisionsIdForUserId;
	private String supervisionsIdForSuperintendentId;
	
	private String superintendent;//监督者
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
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
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getSupervisionsIdForUserId() {
		return supervisionsIdForUserId;
	}
	public void setSupervisionsIdForUserId(String supervisionsIdForUserId) {
		this.supervisionsIdForUserId = supervisionsIdForUserId;
	}
	public String getSupervisionsIdForSuperintendentId() {
		return supervisionsIdForSuperintendentId;
	}
	public void setSupervisionsIdForSuperintendentId(String supervisionsIdForSuperintendentId) {
		this.supervisionsIdForSuperintendentId = supervisionsIdForSuperintendentId;
	}
	public String getSuperintendent() {
		return superintendent;
	}
	public void setSuperintendent(String superintendent) {
		this.superintendent = superintendent;
	}
}
