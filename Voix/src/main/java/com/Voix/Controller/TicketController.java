package com.Voix.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
		session.setAttribute("SerchState", "Y");
		System.out.println(TkList_map);

		// 현재 사용자가 어떤 티켓를 '찜'햇는지 가져옴
		String loginId = (String) session.getAttribute("loginId");
		System.out.println("loginId:" + loginId);
		if (loginId != null) {
			ArrayList<String> likedTicketList = tsvc.getLikedTicketList(loginId);
			System.out.println("likedTicketList" + likedTicketList);
			// 티켓 목록을 반복하면서 '찜' 상태에 따라 이미지 URL 설정
			for (HashMap<String, String> ticketMap : TkList_map) {
				String tkcode = ticketMap.get("TKCODE");
				//System.out.println("tkcode" + tkcode);
				boolean isLiked = likedTicketList.contains(tkcode);
				ticketMap.put("TKLIKED", String.valueOf(isLiked));
			}
		}	
		mav.addObject("TkListMap",TkList_map);
		mav.setViewName("Basic/TicketPage");
		return mav;	
	}
	@RequestMapping(value="/likeTicket")
	public @ResponseBody int likeNews(String like, HttpSession session) {
		System.out.println("티켓 찜 기능");
		String mid = session.getAttribute("loginId").toString();
		System.out.println("티켓- 아이디 확인:"+mid);
		System.out.println("티켓-   찜 확인:"+like);

		// 사용자가 이미 해당 뉴스를 '찜'했는지 확인
		ArrayList<String> likedTicketList = tsvc.getLikedTicketList(mid);
		if (likedTicketList.contains(like)) {
			// 이미 '찜'한 경우 '찜' 취소
			int result = tsvc.unlikeTicket(like, mid);
			System.out.println(like);
			System.out.println(mid);
			if (result > 0) {
				System.out.println("티켓 '찜' 취소 완료");
				return 0;
			} else {
				System.out.println("티켓 '찜' 취소 실패");
				return -1; 
			}
		} else {
			// '찜'하지 않은 경우 '찜' 처리
			int result = tsvc.likeTicket(like, mid);
			if (result > 0) {
				System.out.println("티켓 '찜' 완료");
				return 1; 
			} else {
				System.out.println("티켓 '찜' 실패");
				return -1; 
			}
		}		
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

	@RequestMapping(value = "/getTicket_melon")
	public ModelAndView getTicket_melon() throws IOException, InterruptedException {
		ModelAndView mav = new ModelAndView();
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver-win64\\chromedriver.exe");
		ArrayList<String> MelonticketUrls = getMelonTicketUrls();
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		ArrayList<Ticket> TicketList = new ArrayList<Ticket>();

		for (String item : MelonticketUrls) {
			driver.get(item);
			try {
				Ticket TK = new Ticket();
				WebElement dateElement = driver.findElement(By.cssSelector("#periodInfo"));
				String date = dateElement.getText();
				System.out.println(date);
				TK.setTkdate(date);

				String title = driver.findElement(By.cssSelector("#conts > div > div.wrap_consert_product > div.wrap_consert_cont > div.box_consert_txt > p.tit")).getText();
				System.out.println(title);
				TK.setTktitle(title);

				String place = driver.findElement(By.cssSelector("#performanceHallBtn > span.place")).getText();
				System.out.println(place);
				TK.setTkplace(place);

				String img = driver.findElement(By.cssSelector("#conts > div > div.wrap_consert_product > div.wrap_consert_cont > div.box_consert_thumb.thumb_180x254 > img")).getAttribute("src");
				System.out.println(img);
				TK.setTkimg(img);

				String artist = driver.findElement(By.cssSelector("#conts > div > div:nth-child(3) > div.wrap_detail_left_cont > div.box_artist_checking > div > ul > li.first > a > strong")).getText();
				System.out.println(artist);
				TK.setTkartist(artist);

				String url = item;
				System.out.println(url);
				TK.setTkurl(url);
				String time = driver.findElement(By.cssSelector("#conts > div > div.wrap_consert_product > div.wrap_consert_cont > div.box_consert_txt > div.box_consert_info > dl.info_left > dd:nth-child(4)")).getText();
				System.out.println(time);
				TK.setTktime(time);

				TicketList.add(TK);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		// 추가된 앨범 개수 받아올 거
		for (Ticket TK : TicketList) {
			String MaxTkCode = tsvc.getMaxTkCode();
			String newTkCode = tsvc.genCode(MaxTkCode);
			TK.setTkcode(newTkCode);
			int insertCount = 0;
			try {
				int insertResult = tsvc.getTicket_melon(TK);
				insertCount += insertResult;
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("추가: " + insertCount);
		}
		driver.quit();
		return mav;
	}

	private ArrayList<String> getMelonTicketUrls() {
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		String melonTicketUrl = "https://ticket.melon.com/concert/index.htm?genreType=GENRE_CON";
		driver.get(melonTicketUrl);
		List<WebElement> aLinks = driver.findElements(By.cssSelector("#perf_poster > li>a"));
		ArrayList<String> thUrls = new ArrayList<String>();
		for (WebElement aLink : aLinks) {
			thUrls.add(aLink.getAttribute("href"));
		}
		driver.quit();
		return thUrls;
	}

	@RequestMapping(value = "/getTicket_Inter")
	public ModelAndView getTicket_Inter() throws IOException, InterruptedException {
		ModelAndView mav = new ModelAndView();
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver-win64\\chromedriver.exe");
		ArrayList<String> InterticketUrls = getInterTicketUrls();
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		ArrayList<Ticket> TicketList = new ArrayList<Ticket>();

		for (String item : InterticketUrls) {
			driver.get(item);
			try {
				Ticket TK = new Ticket();
				WebElement dateElement = driver.findElement(By.cssSelector("#container > div.contents > div.productWrapper > div.productMain > div.productMainTop > div > div.summaryBody > ul > li:nth-child(2) > div > p"));
				String date = dateElement.getText();
				System.out.println(date);
				TK.setTkdate(date);

				String title = driver.findElement(By.cssSelector("#container > div.contents > div.productWrapper > div.productMain > div.productMainTop > div > div.summaryTop > h2")).getText();
				System.out.println(title);
				TK.setTktitle(title);

				String place = driver.findElement(By.cssSelector("#container > div.contents > div.productWrapper > div.productMain > div.productMainTop > div > div.summaryBody > ul > li:nth-child(1) > div > a")).getText();
				System.out.println(place);
				TK.setTkplace(place);

				String img = driver.findElement(By.cssSelector("#container > div.contents > div.productWrapper > div.productMain > div.productMainTop > div > div.summaryBody > div > div.posterBoxTop > img")).getAttribute("src");
				System.out.println(img);
				TK.setTkimg(img);

				String artist = driver.findElement(By.cssSelector("#productMainBody > div > div > div.content.casting > div > ul > li > div.castingInfo > div.castingName")).getText();
				System.out.println(artist);
				TK.setTkartist(artist);

				String url = item;
				System.out.println(url);
				TK.setTkurl(url);
				String time = driver.findElement(By.cssSelector("#container > div.contents > div.productWrapper > div.productMain > div.productMainTop > div > div.summaryBody > ul > li:nth-child(3) > div > p")).getText();
				System.out.println(time);
				TK.setTktime(time);

				TicketList.add(TK);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		// 추가된 앨범 개수 받아올 거
		for (Ticket TK : TicketList) {
			String MaxTkCode = tsvc.getMaxTkCode();
			String newTkCode = tsvc.genCode(MaxTkCode);
			TK.setTkcode(newTkCode);
			int insertCount = 0;
			try {
				int insertResult = tsvc.getTicket_Interticket(TK);
				insertCount += insertResult;
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("추가: " + insertCount);
		}
		driver.quit();
		return mav;
	}

	private ArrayList<String> getInterTicketUrls() {
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		String InterTicketUrl = "https://mticket.interpark.com/genre?Genre=CO";
		driver.get(InterTicketUrl);
		List<WebElement> aLinks = driver.findElements(By.cssSelector("body > main > div.displayListWrapper > div.swiper-container-2.swiper-container-horizontal.swiper-container-autoheight > div > div.swiper-slide.swiper-slide-active > div > ul > li > a"));
		ArrayList<String> thUrls = new ArrayList<String>();
		for (WebElement aLink : aLinks) {
			thUrls.add(aLink.getAttribute("href"));
		}
		driver.quit();
		return thUrls;
	}

	@RequestMapping(value = "/getTicket_Yes24")
	public ModelAndView getTicket_Yes24() throws InterruptedException {
		ModelAndView mav = new ModelAndView();
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		String InterTicketUrl = "http://m.ticket.yes24.com/Genre/List.aspx?genre=15456";
		driver.get(InterTicketUrl);
		List<WebElement> aLinks = driver.findElements(By.cssSelector("#PerfList > li > a"));
		System.out.println(driver.findElements(By.cssSelector("#PerfList > li > a")).size());
		ArrayList<Ticket> TicketList = new ArrayList<Ticket>();
		int idx = 0;
		for (int i = 0; i < aLinks.size(); i++) {
			try {
				driver.findElements(By.cssSelector("#PerfList > li > a")).get(idx).click();
				/**/
				try {
					Ticket TK = new Ticket();
					WebElement dateElement = driver.findElement(By.cssSelector("#gd_norInfo > div.gd_top > div > dl:nth-child(2) > dd"));
					String date = dateElement.getText();
					System.out.println(date);
					TK.setTkdate(date);

					String title = driver.findElement(By.cssSelector("#detail_wrap > div.gd_hdArea > h2 > em")).getText();
					System.out.println(title);
					TK.setTktitle(title);

					String place = driver.findElement(By.cssSelector("#gd_norInfo > div.gd_top > div > dl:nth-child(3) > dd > a")).getText();
					System.out.println(place);
					TK.setTkplace(place);

					String img = driver.findElement(By.cssSelector("#gd_norInfo > div.gd_top > p > a > em > img")).getAttribute("src");
					System.out.println(img);
					TK.setTkimg(img);

					String url = driver.getCurrentUrl();
					System.out.println(url);
					TK.setTkurl(url);
					String time = driver.findElement(By.cssSelector("#gd_norInfo > div.gd_top > div > dl:nth-child(5) > dd")).getText();
					System.out.println(time);
					TK.setTktime(time);

					TicketList.add(TK);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				driver.navigate().back();
				// 추가된 앨범 개수 받아올 거
			} catch (Exception e) {
				// TODO: handle exception
			}
			Thread.sleep(500);
			idx++;
		}
		for (Ticket TK : TicketList) {
			String MaxTkCode = tsvc.getMaxTkCode();
			String newTkCode = tsvc.genCode(MaxTkCode);
			TK.setTkcode(newTkCode);
			int insertCount = 0;
			try {
				int insertResult = tsvc.getTicket_Yes24ticket(TK);
				insertCount += insertResult;
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("추가: " + insertCount);
		}
		/*
		 * ArrayList<String> thUrls = new ArrayList<String>(); for (WebElement aLink :
		 * aLinks) { thUrls.add(aLink.getAttribute("href")); }
		 * 
		 */
		driver.quit();
		return mav;
	}

	@RequestMapping(value = "/getTicket_11bunga")
	public ModelAndView getTicket_11bunga() throws IOException, InterruptedException {
		ModelAndView mav = new ModelAndView();
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver-win64\\chromedriver.exe");
		ArrayList<String> InterticketUrls = get11bungaTicketUrls();
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		ArrayList<Ticket> TicketList = new ArrayList<Ticket>();

		for (String item : InterticketUrls) {
			driver.get(item);
			try {
				Ticket TK = new Ticket();
				WebElement dateElement = driver.findElement(By.cssSelector("#layBodyWrap > div > div > div.l_product_cont_wrap.l_ticket_cont_wrap > div > div.l_product_view_wrap > div.l_product_summary > div.l_product_side_info > div.c_product_info_title > dl > div:nth-child(1) > dd"));
				String date = dateElement.getText();
				System.out.println(date);
				TK.setTkdate(date);

				String title = driver.findElement(By.cssSelector("#layBodyWrap > div > div > div.l_product_cont_wrap.l_ticket_cont_wrap > div > div.l_product_view_wrap > div.l_product_summary > div.l_product_side_info > div.c_product_info_title > h1")).getText();
				System.out.println(title);
				TK.setTktitle(title);

				String place = driver.findElement(By.cssSelector("#layBodyWrap > div > div > div.l_product_cont_wrap.l_ticket_cont_wrap > div > div.l_product_view_wrap > div.l_product_summary > div.l_product_side_info > div.c_product_info_title > dl > div:nth-child(2) > dd")).getText();
				System.out.println(place);
				TK.setTkplace(place);

				String img = driver.findElement(By.cssSelector("#\\30  > img")).getAttribute("src");
				System.out.println(img);
				TK.setTkimg(img);

				String artist = driver.findElement(By.cssSelector("#layBodyWrap > div > div > div.l_product_cont_wrap.l_ticket_cont_wrap > div > div.l_product_view_wrap > div.l_product_summary > div.l_product_side_info > div.c_product_info_title > dl > div:nth-child(6) > dd")).getText();
				System.out.println(artist);
				TK.setTkartist(artist);

				String url = item;
				System.out.println(url);
				TK.setTkurl(url);
				String time = driver.findElement(By.cssSelector("#layBodyWrap > div > div > div.l_product_cont_wrap.l_ticket_cont_wrap > div > div.l_product_view_wrap > div.l_product_summary > div.l_product_side_info > div.c_product_info_title > dl > div:nth-child(4) > dd")).getText();
				System.out.println(time);
				TK.setTktime(time);

				TicketList.add(TK);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		// 추가된 앨범 개수 받아올 거
		for (Ticket TK : TicketList) {
			String MaxTkCode = tsvc.getMaxTkCode();
			String newTkCode = tsvc.genCode(MaxTkCode);
			TK.setTkcode(newTkCode);
			int insertCount = 0;
			try {
				int insertResult = tsvc.getTicket_11bungaTicket(TK);
				insertCount += insertResult;
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("추가: " + insertCount);
		}
		driver.quit();
		return mav;
	}

	private ArrayList<String> get11bungaTicketUrls() {
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//		options.addArguments("headless");
		WebDriver driver = new ChromeDriver();
		String InterTicketUrl = "https://ticket.11st.co.kr/Product/List?genreId=14124";
		driver.get(InterTicketUrl);
		List<WebElement> aLinks = driver.findElements(By.cssSelector("#form1 > div > div > div > div > div.tk_result_list > div.tk_content > div.tk_section.play > ul > li > a"));
		ArrayList<String> thUrls = new ArrayList<String>();
		for (WebElement aLink : aLinks) {
			thUrls.add(aLink.getAttribute("href"));
		}
		driver.quit();
		return thUrls;
	}

	@RequestMapping(value = "/choosSite")
	public ModelAndView choosSite(String siteVal , HttpSession session) {
		System.out.println("사이트선택"+siteVal);
		
		session.setAttribute("SerchState", "Y");
		ModelAndView mav = new ModelAndView();
		ArrayList<HashMap<String, String>> TkList_map = tsvc.getTicketList_ChooseSite(siteVal);
		System.out.println(TkList_map);
		mav.addObject("TkListMap", TkList_map);
		mav.setViewName("Basic/TicketPage");

		return mav;
	}
	
	
}
