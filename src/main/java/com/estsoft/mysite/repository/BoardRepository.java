package com.estsoft.mysite.repository;

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
import com.estsoft.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;

	@PersistenceContext
	private EntityManager em;
	
	public List<BoardVo> getList(String kwd, int page, int CountPage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", '%' + kwd + '%');
		map.put("limit", (page-1)*CountPage);
		map.put("CountPage", CountPage);
		
		List<BoardVo> list = sqlSession.selectList("board.getList", map);
		return list;
	}
	
	public void insert(Board board){
		em.persist(board);
	}
	
	public void increaseOrder(Board board){
		String jpql = "update Board b set b.order_no = b.order_no + 1"
				+ " where b.group_no = :group_no and b.order_no >= :order_no";
		Query query = em.createQuery(jpql);
		
		query.setParameter("group_no", board.getGroupNo());
		query.setParameter("order_no", board.getOrderNo());
		
		query.executeUpdate();
	}
	
	public BoardVo getContent(Long no){
		BoardVo vo = sqlSession.selectOne("board.get", no);
		return vo;
	}
	
	public void update(BoardVo vo){
		sqlSession.update("board.update", vo);
	}
	
	public void delete(Long no){
		sqlSession.delete("board.delete", no);
	}
	
	public void increaseHit(Long no){
		sqlSession.update("board.increaseHit", no);
	}
	
	public int getCount(String kwd){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		
		return sqlSession.selectOne("board.getCount", map);
	}
	
}
