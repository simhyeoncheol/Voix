package com.Voix.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class PriceController {

	
	@RequestMapping(value = "/PricePage")
	public ModelAndView PricePage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Basic/PricePage");
		session.setAttribute("sideState", "P");
		return mav;
	}
	
}
