package com.Voix.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Voix.Dto.Chart;
import com.Voix.Dto.playL;
import com.Voix.Service.ChartService;

@Controller
public class ChartController {

	@Autowired
	private ChartService csvc;
	
	@RequestMapping(value = "/ChartPage")
	public ModelAndView ChartPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ArrayList<HashMap<String, String>> ChartList_map = csvc.getChartList_map();
		session.setAttribute("sideState", "M");
		System.out.println(ChartList_map);
		mav.addObject("ChartListMap",ChartList_map);
		mav.setViewName("Basic/ChartPage");
		return mav;
	}
	
	@RequestMapping(value ="/ChartInfoPage")
	public ModelAndView ChartInfoPage(String sgcode) {
		ModelAndView mav = new ModelAndView();
		ArrayList<Chart> ChartInfoList = csvc.getChartInfoList(sgcode);
		System.out.println(ChartInfoList);
		mav.addObject("ChartInfoList", ChartInfoList);
		mav.setViewName("BasicInfo/ChartInfoPage");
		return mav;
	}
	@RequestMapping(value="/PlayListAdd")
	public @ResponseBody String PlayListAdd(HttpSession session,String sgcode){
	    List<playL> playlist = new ArrayList<playL>();
	    playL song = new playL();
	    song.setSgcode(sgcode); // sgcode는 곡 코드를 나타내는 것으로 가정
	    playlist.add(song);
	    
	    session.setAttribute("playlist", playlist);
	    
	    return "Song added to playlist successfully";
	}
	
}