package com.Voix.Controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Voix.Dto.Blog;
import com.Voix.Dto.Criteria;
import com.Voix.Dto.PageMaker;
import com.Voix.Service.BlogService;
import com.google.gson.Gson;

@Controller
public class BlogController {
	
	@Autowired
	private BlogService bsvc;

	@RequestMapping(value = "/BlogPage")
	public ModelAndView Blog(HttpSession session, Criteria cri)throws Exception{
		ModelAndView mav = new ModelAndView();
		session.setAttribute("sideState", "N");
		session.setAttribute("rankState", "BL");
		session.setAttribute("SerchState", "Y");
		ArrayList<HashMap<String, String>> BlogList_map = bsvc.getBlogList_map();
		int perPageNum = cri.getPerPageNum();
		int page = cri.getPage();
		String startBGCODE = String.format("B%04d", (page - 1) * perPageNum + 1);
	   	String endBGCODE = String.format("B%04d", page * perPageNum);
		//System.out.println(BlogList_map);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(bsvc.countBoardListTotal());
		List<Map<String, Object>> list = bsvc.selectBoardList(startBGCODE, endBGCODE);
		mav.addObject("BlogList", list);
		mav.addObject("pageMaker", pageMaker);
		System.out.println(list);
		System.out.println(pageMaker);
		//System.out.println(BlogList_map);
		//mav.addObject("BlogListMap",BlogList_map);

		// 현재 사용자가 어떤 블로그를 '찜'햇는지 가져옴 
		String loginId = (String) session.getAttribute("loginId");
		System.out.println("loginId:"+loginId);
		if(loginId != null){
			ArrayList<String> likedBlogList = bsvc.getLikedBlogList(loginId);
			System.out.println("likedBlogList"+likedBlogList);
			// 블로그 목록을 반복하면서 '찜' 상태에 따라 이미지 URL 설정합니다.
			for (HashMap<String, String> blogMap : BlogList_map) {
			String bgcode = blogMap.get("BGCODE");
			System.out.println("bgcode"+bgcode); //nwcode 나오냐?
			boolean isLiked = likedBlogList.contains(bgcode);
			blogMap.put("BGLIKED", String.valueOf(isLiked));
		        }
		}
		mav.setViewName("Basic/BlogPage");
		return mav;		
	}
	
	@RequestMapping(value ="/BlogInfoPage")
	public ModelAndView NewsInfoPage(String bgcode) {
		ModelAndView mav = new ModelAndView();
		int blogupdate = bsvc.UpdateBlogBigHit(bgcode);
		Blog bg = bsvc.getbgInfo(bgcode);
		mav.addObject("bg", bg);
		ArrayList<HashMap<String,String>> reviewList = bsvc.selectReviewList(bgcode);
		mav.addObject("reviewList",reviewList);
		System.out.println(reviewList);
		mav.setViewName("BasicInfo/BlogInfoPage");
		return mav;
	}
	
	@RequestMapping(value = "/BlogRegistReview")
	public ModelAndView registReview(String restate, String recontent, HttpSession session, RedirectAttributes ra) {
		String rewriter = (String)session.getAttribute("loginId");
		int registResult = bsvc.BlogRegistReview(restate, recontent, rewriter);
		ModelAndView mav = new ModelAndView();
		ra.addFlashAttribute("msg", "댓글이 등록 되었습니다.");
		mav.setViewName("redirect:/BlogInfoPage?bgcode="+restate);
		return mav;
	}
	
	@RequestMapping(value="/deleteBlogReview")
	public ModelAndView deleteReivew(String recode,String bgcode,RedirectAttributes ra) {
		System.out.println("리뷰 삭제 요청");
		ModelAndView mav = new ModelAndView();
		int Result = bsvc.deleteBlogReview(recode);
		ra.addFlashAttribute("msg", "관람평 삭제 완료 되었습니다.");
		mav.setViewName("redirect:/BlogInfoPage?bgcode="+bgcode);
		return mav;
	}
	
	@RequestMapping(value="/likeBlog")
	public @ResponseBody int likeNews(String like, HttpSession session) {
		System.out.println("블로그 찜 기능");
		String mid = session.getAttribute("loginId").toString();
		System.out.println("블로그- 아이디 확인:"+mid);
		System.out.println("블로그-   찜 확인:"+like);

	    // 사용자가 이미 해당 블로그를 '찜'했는지 확인
	    ArrayList<String> likedBlogList = bsvc.getLikedBlogList(mid);
	    if (likedBlogList.contains(like)) {
	        // 이미 '찜'한 경우 '찜' 취소
	        int result = bsvc.unlikeBlog(like, mid);
	        System.out.println(like);
	        System.out.println(mid);
	        if (result > 0) {
	            System.out.println("블로그 '찜' 취소 완료");
	            return 0; // 클라이언트에게 '찜'이 취소되었음을 알립니다.
	        } else {
	            System.out.println("블로그 '찜' 취소 실패");
	            return -1; // '찜' 취소 실패를 클라이언트에게 알립니다.
	        }
	    } else {
	        // '찜'하지 않은 경우 '찜' 처리
	        int result = bsvc.likeBlog(like, mid);
	        if (result > 0) {
	            System.out.println("블로그 '찜' 완료");
	            return 1; // 클라이언트에게 '찜' 완료를 알립니다.
	        } else {
	            System.out.println("블로그 '찜' 실패");
	            return -1; // '찜' 실패를 클라이언트에게 알립니다.
	        }
	    }	
	}			
	//return bsvc.likeBlog(like,mid);
	//}
	
	@RequestMapping(value = "/BlogHitList")
	public  @ResponseBody String BlogHitList() {
		System.out.println("사이드바 조회수 목록 출력");
		ModelAndView mav = new ModelAndView();
		ArrayList<HashMap<String,String>> BlogHitList = bsvc.selectBlogHitList();
		
		return new Gson().toJson(BlogHitList);
	}
	@RequestMapping(value = "/chatPage")
	public ModelAndView chatPage2(String chatId, HttpServletRequest request) {
		System.out.println("채팅페이지 이동 요청 - /chatPage");
		System.out.println("사용할 아이디: " + chatId);
		ModelAndView mav = new ModelAndView();
		request.getAttribute("loginId");
		mav.setViewName("/BasicInfo/ChatPage");
		return mav;
	}
	
	@RequestMapping(value = "/chatbotSend" ,produces = "application/text; charset=UTF-8")
  public @ResponseBody String chatbotSend(String inputText,String mid) throws UnsupportedEncodingException {
      System.out.println("작성내용"+inputText);
			String msg = "";
      msg = bsvc.chatbotAPI(inputText,mid);
      return  msg;
  }
}
