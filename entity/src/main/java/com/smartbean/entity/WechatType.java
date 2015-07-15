package com.smartbean.entity;// default package

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * TWechatType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_wechat_type", catalog = "dongtai", uniqueConstraints = {})
public class WechatType implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String parentId;
	private WechatType parent;
	private DateTime createTime;
	@Transient
	private List<WechatType> children;

	// Constructors

	/** default constructor */
	public WechatType() {
	}

	/** minimal constructor */
	public WechatType(String id) {
		this.id = id;
	}

	/** full constructor */
	public WechatType(String id, String name, DateTime createTime,
						String parentId, WechatType parent) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.parent = parent;
		this.createTime = createTime;
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

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
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

	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id",nullable = true, insertable = false, updatable = false)
	public WechatType getParent() {
		return this.parent;
	}

	public void setParent(WechatType parent) {
		this.parent = parent;
	}

	@Transient
	public List<WechatType> getChildren() {
		return children;
	}

	public void setChildren(List<WechatType> children) {
		this.children = children;
	}
}