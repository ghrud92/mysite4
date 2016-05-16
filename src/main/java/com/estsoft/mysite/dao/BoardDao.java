package com.estsoft.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> getList(String kwd, int page, int CountPage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", '%' + kwd + '%');
		map.put("limit", (page-1)*CountPage);
		map.put("CountPage", CountPage);
		
		List<BoardVo> list = sqlSession.selectList("board.getList", map);
		return list;
	}
	
	public void insert(BoardVo vo){
		sqlSession.insert("board.insert", vo);
	}
	
	public void increaseOrder(BoardVo vo){
		sqlSession.update("board.increaseOrder", vo);
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
