package com.smartbean.entity;// default package

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * TSubscribe entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_subscribe", catalog = "dongtai", uniqueConstraints = {})
public class Subscribe implements java.io.Serializable {

	// Fields

	private String id;
	private String customerId;
	private String wechatId;
	private DateTime createTime;

	// Constructors

	/** default constructor */
	public Subscribe() {
	}

	/** minimal constructor */
	public Subscribe(String id) {
		this.id = id;
	}

	/** full constructor */
	public Subscribe(String id, String wechatId, String customerId, DateTime createTime) {
		this.id = id;
		this.createTime = createTime;
		this.customerId = customerId;
		this.wechatId = wechatId;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime",
			parameters = { @Parameter(name = "databaseZone", value = "Asia/Shanghai"),
					@Parameter(name = "javaZone", value = "jvm")})
	@Column(name = "create_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public DateTime getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	@Column(name = "customer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "wechat_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
}