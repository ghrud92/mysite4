package com.estsoft.mysite.repository;

import static com.estsoft.mysite.domain.QBoard.board;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Board;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;

	@PersistenceContext
	private EntityManager em;
	
	public Map<String, Object> getList(String kwd, int page, int CountPage){
		JPAQuery query = new JPAQuery(em);
		
		SearchResults<Board> results =
			query.from(board)
			.where(board.title.like("%"+kwd+"%"))
			.orderBy(board.groupNo.desc())
			.orderBy(board.orderNo.asc())
			.offset((page-1)*CountPage)
			.limit(CountPage)
			.listResults(board);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", results.getResults());
		map.put("totalCount", results.getTotal());
		
		return map;
	}
	
	public void insert(Board target){
		JPAQuery query = new JPAQuery(em);
		
		target.setRegDate(new Date());
		
		
		if(target.getGroupNo() == 0){
			List<Board> list = 
				query.from(board)
				.orderBy(board.groupNo.desc())
				.offset(0)
				.limit(1)
				.list(board);
			if(list.isEmpty()){
				target.setGroupNo(1);
			}else{
				target.setGroupNo(list.get(0).getGroupNo() + 1);
			}
		}
		em.persist(target);
	}
	
	public void increaseOrder(Board board){
		String jpql = "update Board b set b.orderNo = b.orderNo + 1"
				+ " where b.groupNo = :groupNo and b.orderNo >= :orderNo";
		Query query = em.createQuery(jpql);
		
		query.setParameter("groupNo", board.getGroupNo());
		query.setParameter("orderNo", board.getOrderNo());
		
		query.executeUpdate();
	}
	
	public Board getContent(Long no){
		Board result = em.find(Board.class, no);
		return result;
	}
	
	public void update(Board target){
		JPAUpdateClause clause = new JPAUpdateClause(em, board);
		
		clause.where(board.no.eq(target.getNo()))
		.set(board.title, target.getTitle())
		.set(board.content, target.getContent())
		.execute();
	}
	
	public void delete(Long no){
		JPADeleteClause clause = new JPADeleteClause(em, board);
		
		clause.where(board.no.eq(no))
		.execute();
	}
	
	public void increaseHit(Long no){
		JPAUpdateClause clause = new JPAUpdateClause(em, board);
		
		clause.where(board.no.eq(no))
		.set(board.hit, board.hit.add(1))
		.execute();
	}
	
	public int getCount(String kwd){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		
		return sqlSession.selectOne("board.getCount", map);
	}
	
}
