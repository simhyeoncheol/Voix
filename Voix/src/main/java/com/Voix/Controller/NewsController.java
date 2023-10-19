package com.Voix.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Voix.Dto.News;
import com.Voix.Service.NewsService;

@Controller
public class NewsController {

	@Autowired
	private NewsService nsvc;
	
	@RequestMapping(value = "/NewsPage")
	public ModelAndView News(HttpSession session){
		ModelAndView mav = new ModelAndView();
		session.setAttribute("sideState", "N");
		session.setAttribute("rankState", "NW");
		ArrayList<HashMap<String, String>> NewsList_map = nsvc.getNewsList_map();
		System.out.println(NewsList_map);
		mav.addObject("NewsListMap",NewsList_map);
		mav.setViewName("Basic/NewsPage");
		return mav;
		
	}
	
	@RequestMapping(value ="/NewsInfoPage")
	public ModelAndView NewsInfoPage(String nwcode) {
		ModelAndView mav = new ModelAndView();
		News nw = nsvc.getNwInfo(nwcode);
		mav.addObject("nw", nw);
		ArrayList<HashMap<String,String>> reviewList = nsvc.selectReviewList(nwcode);
		mav.addObject("reviewList",reviewList);
		System.out.println(reviewList);
		mav.setViewName("BasicInfo/NewsInfoPage");
		return mav;
		
		
	}
	@RequestMapping(value = "/newsRegistReview")
	public ModelAndView registReview(String restate, String recontent, HttpSession session, RedirectAttributes ra) {
		String rewriter = (String)session.getAttribute("loginId");
		int registResult = nsvc.newsRegistReview(restate, recontent, rewriter);
		ModelAndView mav = new ModelAndView();
		ra.addFlashAttribute("msg", "댓글이 등록 되었습니다.");
		mav.setViewName("redirect:/NewsInfoPage?nwcode="+restate);
		return mav;
		
	}
	
	@RequestMapping(value="/deleteNewsReview")
	public ModelAndView deleteReivew(String recode,String nwcode,RedirectAttributes ra) {
		System.out.println("리뷰 삭제 요청");
		ModelAndView mav = new ModelAndView();
		int Result = nsvc.deleteNewsReview(recode);
		ra.addFlashAttribute("msg", "관람평 삭제 완료 되었습니다.");
		mav.setViewName("redirect:/NewsInfoPage?nwcode="+nwcode);
	
		return mav;
	}
	@RequestMapping(value="/likeNews")
	public @ResponseBody int likeNews(String like, HttpSession session) {
		System.out.println("뉴스 찜 기능");
		String mid = session.getAttribute("loginId").toString();
		System.out.println("뉴스- 아이디 확인:"+mid);
		System.out.println("뉴스-   찜 확인:"+like);
		
	return nsvc.likeNews(like,mid);
	}
}
