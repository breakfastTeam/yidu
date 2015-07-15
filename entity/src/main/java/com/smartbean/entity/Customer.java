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
 * TCustomer entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_customer", catalog = "dongtai", uniqueConstraints = {})
public class Customer implements java.io.Serializable {

	// Fields

	private String id;
	private String avatar;
	private Integer gender;
	private DateTime createTime;
	private String city;
	private String nickName;
	private Integer groupId;
	private String remark;
	private DateTime subscribeTime;
	private String language;
	private String province;
	private String country;
	private String openId;
	private String status;

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(String id) {
		this.id = id;
	}

	/** full constructor */
	public Customer(String id, String avatar, Integer gender,
					DateTime createTime, String city, String nickName, Integer groupId,
			String remark, DateTime subscribeTime, String language,
			String province, String country, String openId) {
		this.id = id;
		this.avatar = avatar;
		this.gender = gender;
		this.createTime = createTime;
		this.city = city;
		this.nickName = nickName;
		this.groupId = groupId;
		this.remark = remark;
		this.subscribeTime = subscribeTime;
		this.language = language;
		this.province = province;
		this.country = country;
		this.openId = openId;
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

	@Column(name = "avatar", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "gender", unique = false, nullable = true, insertable = true, updatable = true, length = 8)
	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime",
			parameters = { @Parameter(name = "databaseZone", value = "Asia/Shanghai"),
					@Parameter(name = "javaZone", value = "jvm")})
	@Column(name = "create_time")
	public DateTime getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	@Column(name = "city", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "nick_name", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "group_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime",
			parameters = { @Parameter(name = "databaseZone", value = "Asia/Shanghai"),
					@Parameter(name = "javaZone", value = "jvm")})
	@Column(name = "subscribe_time")
	public DateTime getSubscribeTime() {
		return this.subscribeTime;
	}

	public void setSubscribeTime(DateTime subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	@Column(name = "language", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "province", unique = false, nullable = true, insertable = true, updatable = true, length = 24)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "country", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "open_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}