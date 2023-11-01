package com.Voix.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Voix.Dto.Price;
import com.Voix.Service.PriceService;



@Controller
public class PriceController {
@Autowired
PriceService psvc;
	
	@RequestMapping(value = "/PricePage")
	public ModelAndView PricePage(HttpSession session) throws IOException {
		ModelAndView mav = new ModelAndView();
		
		session.setAttribute("sideState", "P");
		System.out.println("getPricemelon 정보 수집 요청");

		ArrayList<Price> PriceList = psvc.getPricemelon();
		System.out.println(PriceList);
		
		mav.addObject("PriceList",PriceList);
		mav.setViewName("Basic/PricePage");
		return mav;
	}

}
