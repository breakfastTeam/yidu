package com.smartbean.entity;// default package

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * TSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_push_log", catalog = "dongtai", uniqueConstraints = {})
public class PushLog implements java.io.Serializable {

	// Fields

	private String id;
	private String typeId;
	private String type;
	private String customerId;
	private DateTime createTime;

	// Constructors

	/** default constructor */
	public PushLog() {
	}

	/** minimal constructor */
	public PushLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public PushLog(String id, String type, String typeId, DateTime createTime, String customerId) {
		this.id = id;
		this.createTime = createTime;
		this.customerId = customerId;
		this.type = type;
		this.typeId = typeId;
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

	@Column(name = "type_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Column(name = "customer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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



}