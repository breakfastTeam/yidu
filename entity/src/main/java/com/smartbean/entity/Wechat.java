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
 * TWechat entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_wechat", catalog = "dongtai", uniqueConstraints = {})
public class Wechat implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String account;
	private String logo;
	private String company;
	private DateTime createTime;
	private String openId;
	private String typeId;
	private long num;
	private String briefIntro;
	private WechatType wechatType;
	private String status;

	// Constructors

	/** default constructor */
	public Wechat() {
	}

	/** minimal constructor */
	public Wechat(String id) {
		this.id = id;
	}

	/** full constructor */
	public Wechat(String id, String name, String account, DateTime createTime,String logo, String company,
				  String openId, String typeId, String briefIntro, WechatType wechatType, String status) {
		this.id = id;
		this.name = name;
		this.account = account;
		this.createTime = createTime;
		this.logo = logo;
		this.company = company;
		this.openId = openId;
		this.typeId = typeId;
		this.briefIntro = briefIntro;
		this.wechatType = wechatType;
		this.status = status;
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

	@Column(name = "name", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "account", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "logo", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "company", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	@Column(name = "open_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "type_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "num", unique = false, nullable = true, insertable = true, updatable = true)
	public long getNum() {
		return this.num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	@Column(name = "brief_intro", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getBriefIntro() {
		return this.briefIntro;
	}

	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id",nullable = true, insertable = false, updatable = false)
	public WechatType getWechatType() {
		return this.wechatType;
	}

	public void setWechatType(WechatType wechatType) {
		this.wechatType = wechatType;
	}

	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}