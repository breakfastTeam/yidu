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
@Table(name = "t_read_log", catalog = "dongtai", uniqueConstraints = {})
public class ReadLog implements java.io.Serializable {

	// Fields

	private String id;
	private String articleId;
	private String customerId;
	private Article article;
	private Customer customer;
	private DateTime createTime;

	// Constructors

	/** default constructor */
	public ReadLog() {
	}

	/** minimal constructor */
	public ReadLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public ReadLog(String id, String articleId, DateTime createTime, String customerId) {
		this.id = id;
		this.articleId = articleId;
		this.createTime = createTime;
		this.customerId = customerId;
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

	@Column(name = "article_id", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = true, insertable = false, updatable = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = true, insertable = false, updatable = false)
	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}



}