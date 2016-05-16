package com.estsoft.mysite.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="board")
public class Board {

	@Id
	@Column(name="no")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="title", nullable=false, length=200)
	private String title;
	
	@Column(name="content", nullable=false, columnDefinition="text")
	private String content;
	
	@Column(name="reg_date", nullable=false)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date regDate;
	
	@Column(name="hit", nullable=false)
	private int hit;
	
	@Column(name="group_no", nullable=false)
	private int groupNo;
	
	@Column(name="order_no", nullable=false)
	private int orderNo;
	
	@Column(name="depth", nullable=false)
	private int depth;
	
	@ManyToOne
	@JoinColumn(name="user_no", nullable=false)
	private User user;

	@Override
	public String toString() {
		return "Board [no=" + no + ", title=" + title + ", content=" + content + ", regDate=" + regDate + ", hit=" + hit
				+ ", groupNo=" + groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", user=" + user + "]";
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
