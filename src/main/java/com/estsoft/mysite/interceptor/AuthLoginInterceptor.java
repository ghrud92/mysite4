package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Web Application Context IoC Container 가져오기
//		ApplicationContext applicationContext =
//				WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		
		// Bean
//		UserService userService = applicationContext.getBean(UserService.class);

		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");
		
		User user = new User();
		user.setEmail(email);
		user.setPasswd(passwd);
		
		// Login 작업
		User authUser = userService.login(user);
		if(authUser == null){
			response.sendRedirect(request.getContextPath() + "/user/loginform");
			return false;
		}
		
		// Login 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath() + "/main");
		return false;
	}

}
