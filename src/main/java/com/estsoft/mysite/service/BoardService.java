package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.repository.BoardRepository;
import com.estsoft.mysite.vo.BoardVo;

@Service
@Transactional
public class BoardService {
	
	private static final int CountPage = 2;	// 페이지 당 게시글 수
	private static final int CountList = 2; // 페이지 리스트의 수
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BoardRepository boardRepository;
	
	public Map<String, Object> getMap(String kwd, int page){
		
		// 게시판의 리스트
		Map<String, Object> map = boardRepository.getList(kwd, page, CountPage);

		// 기본 검색 & 페이지 정보
		map.put("kwd", kwd);
		map.put("page", page);
		map.put("CountPage",CountPage);
		map.put("CountList", CountList);
		
		// 페이징 처리
		int left = 1;
		int right = 0;
		int startPage, lastPage;
		String tempCount = map.get("totalCount").toString();
		int count = Integer.parseInt(tempCount);
		int maxPage = count/CountPage;
		if(count % CountPage != 0)
			maxPage++;
		if(page < 1 || page > maxPage)
			page = 1;
		int maxPageGroup = maxPage/CountList;
		if(maxPage % CountList != 0)
			maxPageGroup++;
		int selectedPageGroup = page/CountList;
		if(page % CountList != 0){
			selectedPageGroup++;
		}
		if(selectedPageGroup == 1)
			left = 0;
		if(selectedPageGroup < maxPageGroup)
			right = 1;
		startPage = (selectedPageGroup - 1) * CountList + 1;
		lastPage = (selectedPageGroup) * CountList;
		if(lastPage > maxPage)
			lastPage = maxPage;
		
		map.put("left", left);
		map.put("right", right);
		map.put("startPage", startPage);
		map.put("lastPage", lastPage);
		map.put("page", page);
		map.put("total", count);
		
		return map;
	}
	
	public void write(Board board){
		if(board.getGroupNo() != 0){
			boardRepository.increaseOrder(board);
		}
		boardRepository.insert(board);
	}
	
	public void increaseHit(Long no){
		boardRepository.increaseHit(no);
	}
	
	public Board getView(Long no){
		Board board = boardRepository.getContent(no);
		return board;
	}
	
	public void modify(Board board){
		boardRepository.update(board);
	}
	
	public void delete(Long no){
		boardRepository.delete(no);
	}
}
