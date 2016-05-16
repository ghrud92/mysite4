package com.estsoft.mysite.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Guestbook;

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
		String psql = "select g from Guestbook g order by g.no desc";
		TypedQuery<Guestbook> query = em.createQuery(psql, Guestbook.class);
		List<Guestbook> list = query.getResultList();
		return list;
	}
	
	public List<Guestbook> getList(int page){
		String psql = "select g from Guestbook g order by g.no desc";
		TypedQuery<Guestbook> query = em.createQuery(psql, Guestbook.class);
		query.setFirstResult((page-1)*5);
		query.setMaxResults(5);
		List<Guestbook> list = query.getResultList();
		return list;
	}
	
	public void delete(Guestbook guestbook){
		String psql = "select g from Guestbook g where no = :no and passwd = :passwd";
		TypedQuery<Guestbook> query = em.createQuery(psql, Guestbook.class);
		query.setParameter("no", guestbook.getNo());
		query.setParameter("passwd", guestbook.getPasswd());
		
		Guestbook target = query.getSingleResult();
		em.remove(target);
	}
}
