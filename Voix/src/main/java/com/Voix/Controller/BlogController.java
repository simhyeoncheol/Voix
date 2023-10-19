package com.Voix.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {

	@RequestMapping(value = "/BlogPage")
	public ModelAndView Blog(HttpSession session){
		ModelAndView mav = new ModelAndView();
		session.setAttribute("sideState", "N");
		session.setAttribute("rankState", "BL");
		mav.setViewName("Basic/BlogPage");
		return mav;
		
	}
}
