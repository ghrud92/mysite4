package com.estsoft.mysite.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.User;

@Repository
public class UserRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void insert(User user){
		em.persist(user);
	}
	
	// Login
	public User get(User user){
		String psql = "select u from User u where u.email = :email and u.passwd = :passwd";
		TypedQuery<User> query = em.createQuery(psql, User.class);
		query.setParameter("email", user.getEmail());
		query.setParameter("passwd", user.getPasswd());
		
		List<User> list = query.getResultList();
		if(list.isEmpty()){
			return null;
		}
		
		return list.get(0);
	}

	//gender 포함
	public User get(Long no){
		User user = em.find(User.class, no);
		return user;
	}
	
	// 정보 수정
	public void update(User user){
		User target = em.find(User.class, user.getNo());
		target.setName(user.getName());
		target.setEmail(user.getEmail());
		target.setGender(user.getGender());
		if(user.getPasswd() != null){
			target.setPasswd(user.getPasswd());
		}
	}

	public User get(String email){
		String psql = "select u from User u where u.email = :email";
		TypedQuery<User> query = em.createQuery(psql, User.class);
		query.setParameter("email", email);
		
		List<User> list = query.getResultList();
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
}
