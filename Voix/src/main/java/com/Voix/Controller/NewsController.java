package com.Voix.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Voix.Dto.Criteria;
import com.Voix.Dto.News;
import com.Voix.Dto.PageMaker;
import com.Voix.Service.NewsService;
import com.google.gson.Gson;

@Controller
public class NewsController {

	@Autowired
	private NewsService nsvc;
	
	@RequestMapping(value = "/NewsPage")
	public ModelAndView News(HttpSession session, Criteria cri)throws Exception{
		ModelAndView mav = new ModelAndView();
		session.setAttribute("sideState", "N");
		session.setAttribute("rankState", "NW");
		session.setAttribute("SerchState", "Y");
		int perPageNum = cri.getPerPageNum();
		int page = cri.getPage();
		String startNWCODE = String.format("N%04d", (page - 1) * perPageNum + 1);
	   	String endNWCODE = String.format("N%04d", page * perPageNum);
		ArrayList<HashMap<String, String>> NewsList_map = nsvc.getNewsList_map();
		//System.out.println(NewsList_map);
		
		// 현재 사용자가 어떤 뉴스를 '찜'햇는지 가져옴
		String loginId = (String) session.getAttribute("loginId");
		System.out.println("loginId:" + loginId);
		if (loginId != null) {
			ArrayList<String> likedNewsList = nsvc.getLikedNewsList(loginId);
			System.out.println("likedNewsList" + likedNewsList);
			// 뉴스 목록을 반복하면서 '찜' 상태에 따라 이미지 URL 설정
			for (HashMap<String, String> newsMap : NewsList_map) {
				String nwcode = newsMap.get("NWCODE");
				// System.out.println("nwcode"+nwcode); 
				boolean isLiked = likedNewsList.contains(nwcode);
				newsMap.put("NWLIKED", String.valueOf(isLiked));
			}
		}

		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(nsvc.countBoardListTotal());
		List<Map<String, Object>> list = nsvc.selectBoardList(startNWCODE, endNWCODE);
		mav.addObject("list", list);
		mav.addObject("pageMaker", pageMaker);
		System.out.println(list);
		System.out.println(pageMaker);
		//mav.addObject("NewsListMap",NewsList_map);
		mav.setViewName("Basic/NewsPage");
		return mav;
		
	}
	
	@RequestMapping(value ="/NewsInfoPage")
	public ModelAndView NewsInfoPage(String nwcode) {
		ModelAndView mav = new ModelAndView();
		int nuwsupdate = nsvc.UpdateNewsBigHit(nwcode);
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
		
		// 사용자가 이미 해당 뉴스를 '찜'했는지 확인
		ArrayList<String> likedNewsList = nsvc.getLikedNewsList(mid);
		if (likedNewsList.contains(like)) {
			// 이미 '찜'한 경우 '찜' 취소
			int result = nsvc.unlikeNews(like, mid);
			System.out.println(like);
			System.out.println(mid);
			if (result > 0) {
				System.out.println("뉴스 '찜' 취소 완료");
				return 0; 
			} else {
				System.out.println("뉴스 '찜' 취소 실패");
				return -1; 
			}
		} else {
			// '찜'하지 않은 경우 '찜' 처리
			int result = nsvc.likeNews(like, mid);
			if (result > 0) {
				System.out.println("뉴스 '찜' 완료");
				return 1;
			} else {
				System.out.println("뉴스 '찜' 실패");
				return -1; 
			}
		}	
	}
		
	
	@RequestMapping(value = "/NewsHitList")
	public  @ResponseBody String NewsHitList() {
		System.out.println("사이드바 조회수 목록 출력");
		ModelAndView mav = new ModelAndView();
		ArrayList<HashMap<String,String>> NewsHitList = nsvc.selectNewsHitList();
		
		return new Gson().toJson(NewsHitList);
	}

	/*-------------------------------멜론크롤링----------------------------------*/
	@RequestMapping(value="/crawling")
	public ModelAndView olive() {
	    ModelAndView mav = new ModelAndView();
	    System.out.println("크롤링");

	    // WebDriver 설정 - ChromeDriver 사용
	    System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver-win64\\chromedriver.exe");
	    WebDriver driver = new ChromeDriver();

	    try {
	        // 뉴스 정보를 담을 리스트 생성
	        List<News> newsList = new ArrayList<>();

	        // 1부터 991까지 페이지 번호를 10씩 증가시키며 반복
	        for (int pageNumber = 1; pageNumber <= 91; pageNumber += 10) {
	            String melonNewsCrawling = "https://www.melon.com/musicstory/index.htm?mstoryCateSeq=#params%5BmstoryCateSeq%5D=&po=pageObj&startIndex=" + pageNumber;
	            driver.get(melonNewsCrawling);
	            Thread.sleep(2000);  // 페이지 로딩을 기다린다. (2초)
	            // 각 페이지에서 스크랩한 정보를 수집
	            List<WebElement> items = driver.findElements(By.cssSelector("div.wrap_musicspecl ul>li"));
	            for (WebElement item : items) {
	                News news = new News();	               	                
	                WebElement dateElement = item.findElement(By.cssSelector("div > div > span.none"));
	                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	                jsExecutor.executeScript("arguments[0].classList.remove('none');", dateElement);
	                String date = dateElement.getText();
	                date = date.replace(".0", "");
	                //String date = dateTime.split(" ")[1];
	                news.setNwdate(date);
	                System.out.println(date);
	                String title = item.findElement(By.cssSelector("dl>dt")).getText();
	                news.setNwtitle(title);
	                System.out.println(title);
	                String content = item.findElement(By.cssSelector("dl>dd.sumry")).getText();
	                content = content.replace("안녕하세요, 멜론 가족 여러분!", "").trim();
	                content = content.replace("안녕하세요. 멜론 가족 여러분!", "").trim();
	                content = content.replace("멜론 가족 여러분 안녕하세요!", "").trim();
	                content = content.replace("안녕하세요 멜론 스테이션 청취자 여러분!", "").trim();
	                content = content.replace("멜론 가족 여러분, 안녕하세요!", "").trim();
	                news.setNwcontent(content);
	                System.out.println(content);
	                String img = item.findElement(By.cssSelector("p>a>img")).getAttribute("src");
	                news.setNwimg(img);
	                System.out.println(img);
	                newsList.add(news);
	            }
	        }

	        // DB에 저장
	        
	        String maxNwcode = nsvc.selectMaxNwCode();
	        int insertCount = 0;
	        for (News news : newsList) {
	            // 영화코드 생성
	            String nwcode = nsvc.genCode(maxNwcode);
	            news.setNwcode(nwcode);

	            try {
	                int insertResult = nsvc.insertNews(news);
	                insertCount += insertResult;
	                maxNwcode = nwcode;
	            } catch (Exception e) {
	                e.printStackTrace();
	                continue;
	            }
	        }

	        mav.addObject("newsList", insertCount);
	        mav.setViewName("redirect:/NewsPage");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	       driver.quit();  // WebDriver 종료
	    }

	    return mav;
	}
	/*-------------------------------멜론크롤링-끝---------------------------------*/
}
