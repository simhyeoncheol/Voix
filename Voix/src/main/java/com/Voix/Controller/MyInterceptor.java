package com.Voix.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("HandlerInterceptor");
		HttpSession session = request.getSession();
		session.setAttribute("SerchState", "N");
		return true;
	}
	
	
	
	
	
}
