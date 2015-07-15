package com.smartbean.entity;// default package

import org.hibernate.annotations.*;
import org.joda.time.DateTime;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Parameter;
import javax.persistence.Table;

/**
 * TArticle entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_article", catalog = "dongtai", uniqueConstraints = {})
public class Article implements java.io.Serializable {

	// Fields

	private String id;
	private String subjectId;
	private String wechatId;
	private Wechat wechat;
	private Subject subject;
	private String content;
	private DateTime createTime;
	private String author;
	private Integer readTimes;
	private String detailUrl;
	private String briefIntro;
	private String logo;
	private String title;
	private String status;
	private String openId;

	// Constructors

	/** default constructor */
	public Article() {
	}

	/** minimal constructor */
	public Article(String id) {
		this.id = id;
	}

	/** full constructor */
	public Article(String id, Wechat wechat, Subject subject,String wechatId, String subjectId,String title,String status,String openId,
				   String briefIntro, String logo,String content, DateTime createTime, String author, Integer readTimes, String detailUrl) {
		this.id = id;
		this.wechat = wechat;
		this.subject = subject;
		this.content = content;
		this.createTime = createTime;
		this.author = author;
		this.readTimes = readTimes;
		this.wechatId = wechatId;
		this.subjectId = subjectId;
		this.detailUrl = detailUrl;
		this.title = title;
		this.briefIntro = briefIntro;
		this.logo = logo;
		this.status = status;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wechat_id", nullable = true, insertable = false, updatable = false)
	public Wechat getWechat() {
		return this.wechat;
	}

	public void setWechat(Wechat wechat) {
		this.wechat = wechat;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_id",nullable = true, insertable = false, updatable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime",
			parameters = { @org.hibernate.annotations.Parameter(name = "databaseZone", value = "Asia/Shanghai"),
					@org.hibernate.annotations.Parameter(name = "javaZone", value = "jvm")})
	@Column(name = "create_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public DateTime getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	@Column(name = "author", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "read_times", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getReadTimes() {
		return this.readTimes;
	}

	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}

	@Column(name = "subject_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "wechat_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getWechatId() {
		return this.wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	@Column(name = "detail_url", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getDetailUrl() {
		return this.detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "brief_intro", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getBriefIntro() {
		return this.briefIntro;
	}

	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}

	@Column(name = "logo", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "open_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}