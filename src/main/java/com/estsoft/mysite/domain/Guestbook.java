package com.estsoft.mysite.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="guestbook")
public class Guestbook {

	@Id
	@Column(name="no")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="name", nullable=false, length=100)
	private String name;
	
	@Column(name="passwd", nullable=false, length=64)
	private String passwd;
	
	@Column(name="message", nullable=false, columnDefinition="text")
	private String message;
	
	@Column(name="reg_date", nullable=false)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date regDate;

	@Override
	public String toString() {
		return "Guestbook [no=" + no + ", name=" + name + ", passwd=" + passwd + ", message=" + message + ", regDate="
				+ regDate + "]";
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
