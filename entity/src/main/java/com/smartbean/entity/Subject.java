package com.smartbean.entity;// default package

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * TSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_subject", catalog = "dongtai", uniqueConstraints = {})
public class Subject implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private DateTime createTime;
	private Integer type;//0、热门订阅 1、普通分类

	// Constructors

	/** default constructor */
	public Subject() {
	}

	/** minimal constructor */
	public Subject(String id) {
		this.id = id;
	}

	/** full constructor */
	public Subject(String id, String name, DateTime createTime, Integer type) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
		this.type = type;
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

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "type", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}