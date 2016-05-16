package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/joinform")
	public String joinform() {
		return "/user/joinform";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(
			@Valid @ModelAttribute User user, 
			BindingResult result, 
			Model model){
		if(result.hasErrors()){
			// 에러 출력
//			List<ObjectError> list = result.getAllErrors();
//	    	for (ObjectError e : list) {
//	            System.out.println(" ObjectError : " + e );
//	    	}
			model.addAttribute(result.getModel());
			return "/user/joinform";
		}
		
		userService.join(user);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping("/loginform")
	public String loginform() {
		return "/user/loginform";
	}

	@RequestMapping("/checkemail")
	@ResponseBody
	public Object checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		User user = userService.getUser(email);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("data", user == null);

		return map;
	}

	@RequestMapping("/modifyform")
	public String modifyForm(Model model, HttpSession session) {
		User user = (User) session.getAttribute("authUser");
		user = userService.getUser(user.getNo());
		model.addAttribute("user", user);
		return "/user/modifyform";
	}

	@RequestMapping("/modify")
	public String modify(@ModelAttribute User user, HttpSession session) {
		userService.modify(user);
		User authUser = (User) session.getAttribute("authUser");
		authUser.setName(user.getName());
		return "redirect:/main";
	}
}
