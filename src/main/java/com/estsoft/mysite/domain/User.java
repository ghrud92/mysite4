package com.estsoft.mysite.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	@Column(name="no")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="name", nullable=false, length=100)
	private String name;
	
	@Column(name="email", nullable=false, length=200)
	private String email;
	
	@Column(name="passwd", nullable=false, length=80)
	private String passwd;
	
	@Column(name="gender", nullable=false)
	@Enumerated(value=EnumType.STRING)
	private Gender gender;

	@Override
	public String toString() {
		return "User [no=" + no + ", name=" + name + ", email=" + email + ", passwd=" + passwd + ", gender=" + gender
				+ "]";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
