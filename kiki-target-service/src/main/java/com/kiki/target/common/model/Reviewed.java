package com.kiki.target.common.model;
//123 Generated 2018-2-4 2:05:59 by Hibernate Tools 5.2.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * Reviewed generated by hbm2java
 */
@Entity
@Table(name = "reviewed", catalog = "db_target")
public class Reviewed implements java.io.Serializable {

	private String id;
	private Supervision supervision;
	private Target target;
	private int state;
	private Date createTime;
	private Date updateTime;
	private boolean enabled;
	private String comment;

	public Reviewed() {
	}

	public Reviewed(String id, Supervision supervision, Target target, int state, Date createTime, Date updateTime,
			boolean enabled, String comment) {
		this.id = id;
		this.supervision = supervision;
		this.target = target;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.enabled = enabled;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(generator = "generator")    
	@GenericGenerator(name = "generator", strategy = "uuid")   
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supervision_id", nullable = false)
	public Supervision getSupervision() {
		return this.supervision;
	}

	public void setSupervision(Supervision supervision) {
		this.supervision = supervision;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_id", nullable = false)
	public Target getTarget() {
		return this.target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	@Column(name = "state", nullable = false)
	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false, length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "comment", nullable = false, length = 400)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
