package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.service.GuestbookService;

@RequestMapping("/guestbook")
@Controller
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestBookService;

	@RequestMapping("")
	public String index(Model model){
		List<Guestbook> list = guestBookService.getList();
		model.addAttribute("list", list);
		//System.out.println("list:" + list);
		return "/guestbook/ajax-main";
	}
	
	@RequestMapping("/ajax-list")
	@ResponseBody
	public Map<String, Object> ajaxList(@RequestParam("p") int page){
		List<Guestbook> list = guestBookService.getList(page);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", "success");
		map.put("data", list);
		
		return map;
	}
	
	@RequestMapping("/ajax-insert")
	@ResponseBody
	public Map<String, Object> ajaxInsert(@ModelAttribute Guestbook guestbook){
		
		guestBookService.insert(guestbook);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", guestbook);
		
		return map;
	}
	
	@RequestMapping("/ajax-delete")
	@ResponseBody
	public Map<String, Object> ajaxDelete(@ModelAttribute Guestbook guestbook){
		guestBookService.delete(guestbook);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		
		return map;
	}
	
	@RequestMapping("/insert")
	public String insert(@ModelAttribute Guestbook guestbook){
		guestBookService.insert(guestbook);
		return "redirect:/guestbook";
	}
	
	@RequestMapping("/delete_form")
	public String deleteForm(){
		return "/guestbook/deleteform";
	}
	
	@RequestMapping("/delete")
	public String delete(@ModelAttribute Guestbook guestbook){
		guestBookService.delete(guestbook);
		return "redirect:/guestbook";
		
	}
}
