package com.estsoft.mysite.repository;

import static com.estsoft.mysite.domain.QUser.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.User;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class UserRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void insert(User user){
		em.persist(user);
	}
	
	// Login
	public User get(User target){
		JPAQuery query = new JPAQuery(em);
		
		User authUser =
			query.from(user)
			.where(user.email.eq(target.getEmail()), user.passwd.eq(target.getPasswd()))
			.singleResult(user);
		
		return authUser;
	}

	//gender 포함
	public User get(Long no){
		User user = em.find(User.class, no);
		return user;
	}
	
	// 정보 수정
	public void update(User target){
		JPAUpdateClause clause = new JPAUpdateClause(em, user);
		
		clause.where(user.no.eq(target.getNo()))
		.set(user.name, target.getName())
		.set(user.email, target.getEmail())
		.set(user.gender, target.getGender());
		
		if(target.getPasswd() != ""){
			clause.set(user.passwd, target.getPasswd());
		}
		
		clause.execute();
	}

	public User get(String email){
		JPAQuery query = new JPAQuery(em);
		
		User result =
			query.from(user)
			.where(user.email.eq(email))
			.singleResult(user);
		
		return result;
	}
}
