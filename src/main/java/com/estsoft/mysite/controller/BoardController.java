package com.estsoft.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(
			Model model,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd){
		
		Map<String, Object> map = boardService.getMap(kwd, page);
		model.addAttribute("pageMap",map);
		
		return "/board/list";
	}
	
	@RequestMapping("/writeform")
	public String writeForm(
			Model model,
			@RequestParam(value="groupNo", required=true, defaultValue="0") Integer groupNo,
			@RequestParam(value="orderNo", required=true, defaultValue="0") Integer orderNo,
			@RequestParam(value="depth", required=true, defaultValue="-1") Integer depth){
		
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("depth", depth);
		return "/board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser User authUser, @ModelAttribute Board board){
		System.out.println("in write, authUser : " + authUser);
		board.setUser(authUser);
		boardService.write(board);
		return "redirect:/board";
	}
	
	@RequestMapping("/view")
	public String view(
			Model model,
			@RequestParam(value="no",required=true) Long no,
			@RequestParam(value="page", required=true, defaultValue="1") int page,
			@RequestParam(value="kwd", required=true, defaultValue="") String kwd){
		
		// 조회수 증가
		boardService.increaseHit(no);
		
		// 내용 가져오기
		BoardVo vo = boardService.getView(no);
		model.addAttribute("content", vo);
		
		return "/board/view";
	}
	
	@RequestMapping("/modifyform")
	public String modifyform(Model model, @RequestParam("no") Long no){
		BoardVo vo = boardService.getView(no);
		model.addAttribute("content",vo);
		return "/board/modify";
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute BoardVo vo){
		boardService.modify(vo);
		return "redirect:/board";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("no") Long no){
		boardService.delete(no);
		return "redirect:/board";
	}
}
