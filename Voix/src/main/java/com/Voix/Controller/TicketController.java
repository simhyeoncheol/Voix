package com.Voix.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Voix.Dto.Ticket;
import com.Voix.Service.TicketService;

@Controller
public class TicketController {

	@Autowired
	private TicketService tsvc;
	
	@RequestMapping(value ="/TicketPage")
	public ModelAndView TicketPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ArrayList<HashMap<String, String>> TkList_map = tsvc.getTicketList_map();
		session.setAttribute("sideState", "N");
		session.setAttribute("rankState", "TK");
		System.out.println(TkList_map);
		mav.addObject("TkListMap",TkList_map);
		mav.setViewName("Basic/TicketPage");
		return mav;
		
		
	}
	@RequestMapping(value ="/TicketInfoPage")
	public ModelAndView TicketInfoPage(String tkcode) {
		ModelAndView mav = new ModelAndView();
		Ticket tk = tsvc.getTkInfo(tkcode);
		mav.addObject("tk", tk);
		ArrayList<HashMap<String,String>> reviewList = tsvc.selectReviewList(tkcode);
		mav.addObject("reviewList",reviewList);
		mav.setViewName("BasicInfo/TicketInfoPage");
		return mav;
	}
	
	@RequestMapping(value="/getMapXY")
	public @ResponseBody String getMapXY(String tkplace) throws IOException {
		System.out.println("지도 좌표불러오기");
		return tsvc.getMapXY(tkplace);
	}
		@RequestMapping(value="/getTkTitle")
	public @ResponseBody ArrayList<Ticket> getTkTitle(String tkplace) {
		System.out.println("장소를 이용하여 공연제목들 불러오기");
		return tsvc.getTkTitle(tkplace);
	}
		@RequestMapping(value = "/registReview")
		public ModelAndView registReview(String restate, String recontent, HttpSession session, RedirectAttributes ra) {
			System.out.println("댓글 등록 요청 - /registReview");
			System.out.println("티켓코드 : " + restate);
			System.out.println("댓글내용 : " + recontent);
			String rewriter = (String)session.getAttribute("loginId");
			System.out.println("작성자 : " + rewriter);
			int registResult = tsvc.registReview(restate, recontent, rewriter);
			ModelAndView mav = new ModelAndView();
			ra.addFlashAttribute("msg", "댓글이 등록 되었습니다.");
			mav.setViewName("redirect:/TicketInfoPage?tkcode="+restate);
			return mav;

		}
		@RequestMapping(value="/deleteReview")
		public ModelAndView deleteReivew(String recode,String tkcode,RedirectAttributes ra) {
			System.out.println("리뷰 삭제 요청");
			ModelAndView mav = new ModelAndView();
			int Result = tsvc.deleteReview(recode);
			ra.addFlashAttribute("msg", "관람평 삭제 완료 되었습니다.");
			mav.setViewName("redirect:/TicketInfoPage?tkcode="+tkcode);

			return mav;
		}
	
	
}
