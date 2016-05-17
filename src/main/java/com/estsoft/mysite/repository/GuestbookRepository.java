package com.estsoft.mysite.repository;

import static com.estsoft.mysite.domain.QGuestbook.guestbook;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Guestbook;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class GuestbookRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void insert(Guestbook guestbook){
		guestbook.setRegDate(new Date());
		em.persist(guestbook);
	}
	
	public Guestbook get(Long no){
		Guestbook guestbook = em.find(Guestbook.class, no);
		return guestbook;
	}
	
	public List<Guestbook> getList(){
		JPAQuery query = new JPAQuery(em);
		
		List<Guestbook> list = query.from(guestbook).orderBy(guestbook.no.desc()).list(guestbook);
		return list;
	}
	
	public List<Guestbook> getList(int page){
		JPAQuery query = new JPAQuery(em);
		
		List<Guestbook> list = 
			query.from(guestbook)
			.orderBy(guestbook.no.desc())
			.offset((page-1)*5)
			.limit(5)
			.list(guestbook);
		return list;
	}
	
	public void delete(Guestbook target){
		JPADeleteClause clause = new JPADeleteClause(em, guestbook);
		
		clause.where(guestbook.no.eq(target.getNo()), guestbook.passwd.eq(target.getPasswd())).execute();
	}
}
