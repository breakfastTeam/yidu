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
@Table(name = "t_share_log", catalog = "dongtai", uniqueConstraints = {})
public class ShareLog implements java.io.Serializable {

	// Fields

	private String id;
	private String customerId;
	private String articleId;
	private String shareType;
	private DateTime createTime;

	// Constructors

	/** default constructor */
	public ShareLog() {
	}

	/** minimal constructor */
	public ShareLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public ShareLog(String id, DateTime createTime, String customerId, String articleId) {
		this.id = id;
		this.createTime = createTime;
		this.customerId = customerId;
		this.articleId = articleId;
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


	@Column(name = "customer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "article_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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


	@Column(name = "share_type", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
}