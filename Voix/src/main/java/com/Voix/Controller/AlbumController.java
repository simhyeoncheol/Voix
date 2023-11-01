package com.Voix.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Voix.Dto.Album;
import com.Voix.Dto.Order;
import com.Voix.Service.AlbumService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
public class AlbumController {

	@Autowired
	private AlbumService asvc;

	@RequestMapping(value = "/AlbumPage")
	public ModelAndView AlbumPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		ArrayList<HashMap<String, String>> AlbumList_map = asvc.getAlbumtList_map();
		session.setAttribute("sideState", "N");
		session.setAttribute("rankState", "AB");
		session.setAttribute("SerchState", "Y");
		System.out.println(AlbumList_map);
		mav.addObject("AlbumListMap", AlbumList_map);
		mav.setViewName("Basic/AlbumPage");
		return mav;
	}

	@RequestMapping(value = "/AlbumInfoPage")
	public ModelAndView NewsInfoPage(String alcode, HttpSession session) { 
		ModelAndView mav = new ModelAndView();
		ArrayList<Album> AlbumInfoList = asvc.getAlbumInfoList(alcode);
		Album ALInfo = AlbumInfoList.get(0);
		System.out.println(AlbumInfoList);
		mav.addObject("ALInfo", ALInfo);
		mav.addObject("AlbumInfoList", AlbumInfoList);
		HashMap<String, String> AlbumList_map = new HashMap<String, String>(); 

		// 현재 사용자가 어떤 엘범을 '찜'햇는지 가져옴
		String loginId = (String) session.getAttribute("loginId");
		System.out.println("loginId:" + loginId);
		if (loginId != null) {
			ArrayList<String> likedAlbumList = asvc.getLikedAlbumList(loginId);
			System.out.println("likedAlbumList" + likedAlbumList);
			// 엘범 목록을 반복하면서 '찜' 상태에 따라 이미지 URL 설정
			String alcode1 = ALInfo.getAlcode();
			System.out.println("alcode1" + alcode1);
			boolean isLiked = likedAlbumList.contains(alcode1);
			AlbumList_map.put("ALLIKED", String.valueOf(isLiked));		
			System.out.println("AlbumList_map:!!!!!!!!!!"+AlbumList_map);
			mav.addObject("AlbumInfoList1", AlbumList_map);

			ArrayList<HashMap<String, String>> reviewList = asvc.selectReviewList(alcode);
			mav.addObject("reviewList", reviewList);

			mav.setViewName("BasicInfo/AlbumInfoPage");
			return mav;
		}
		mav.setViewName("BasicInfo/AlbumInfoPage");
			return mav;
		
	}

	@RequestMapping(value="/likeAlbum")
	public @ResponseBody int likeAlbum(String like, HttpSession session) {
		System.out.println("엘범 찜 기능");
		String mid = session.getAttribute("loginId").toString();
		System.out.println("엘범- 아이디 확인:"+mid);
		System.out.println("엘범-   찜 확인:"+like);
		// 사용자가 이미 해당 엘범을 '찜'했는지 확인
		ArrayList<String> likedAlbumList = asvc.getLikedAlbumList(mid);
				if (likedAlbumList.contains(like)) {
					// 이미 '찜'한 경우 '찜' 취소
					int result = asvc.unlikeAlbum(like, mid);
					System.out.println(like);
					System.out.println(mid);
					if (result > 0) {
						System.out.println("엘범 '찜' 취소 완료");
						return 0;
					} else {
						System.out.println("엘범 '찜' 취소 실패");
						return -1; 
					}
				} else {
					// '찜'하지 않은 경우 '찜' 처리
					int result = asvc.likeAlbum(like, mid);
					if (result > 0) {
						System.out.println("엘범 '찜' 완료");
						return 1;
					} else {
						System.out.println("엘범 '찜' 실패");
						return -1; 
					}
				}
			}

		
	@RequestMapping(value = "/albumRegistReview")
	public ModelAndView registReview(String restate, String recontent, HttpSession session, RedirectAttributes ra) {
		String rewriter = (String) session.getAttribute("loginId");
		int registResult = asvc.albumRegistReview(restate, recontent, rewriter);
		ModelAndView mav = new ModelAndView();
		ra.addFlashAttribute("msg", "댓글이 등록 되었습니다.");
		mav.setViewName("redirect:/AlbumInfoPage?alcode=" + restate);
		return mav;
	}

	@RequestMapping(value = "/albumDeleteReview")
	public ModelAndView deleteReivew(String recode, String alcode, RedirectAttributes ra) {
		System.out.println("리뷰 삭제 요청");
		ModelAndView mav = new ModelAndView();
		int Result = asvc.deleteReview(recode);
		ra.addFlashAttribute("msg", "댓글 삭제 완료 되었습니다.");
		mav.setViewName("redirect:/AlbumInfoPage?alcode=" + alcode);

		return mav;
	}

	@RequestMapping(value = "/CartPage")
	public ModelAndView CartPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");
		ArrayList<HashMap<String, String>> CartList = asvc.CartList(loginId);
		System.out.println(CartList);
		mav.addObject("CartList", CartList);
		mav.setViewName("Basic/CartPage");
		return mav;
	}

	@RequestMapping(value = "/InsertCartPage")
	public ModelAndView InsertCartPage(String caalcode, int caqty, HttpSession session, RedirectAttributes ra) {
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");
		if (loginId == null) {
			System.out.println("로그인 안함");
			/*
			 * 아이디 또는 비밀번호 일치하지 않습니다 출력 로그인 페이지로 이동
			 */
			ra.addFlashAttribute("msg", "장바구니는 로그인후 이용 가능합니다.");
			mav.setViewName("redirect:/LoginPage");
		} else if (loginId != null) {
			System.out.println("로그인 함");
			int insertCart = asvc.insetCart(caalcode, caqty, loginId);
			if (insertCart > 0) {
				System.out.println("장바구니 추가 완료");
				mav.setViewName("redirect:/CartPage");
			} else {
				System.out.println("장바구니 추가 실패");
				ra.addFlashAttribute("msg", "항목을 장바구니에 추가하는데 실패하였습니다. 다시 시도해주세요");
				mav.setViewName("redirect:/AlbumInfoPage");
			}
		}
		return mav;
	}

	@RequestMapping(value = "/DeleteCartList", method = RequestMethod.POST)
	public ModelAndView DeleteCartList(@RequestParam(value = "CartCheck") String[] CartCheck) {
		ModelAndView mav = new ModelAndView();

		for (int i = 0; i < CartCheck.length; i++) {
			System.out.println(CartCheck[i]);
			asvc.DeleteCartList(CartCheck[i]);
		}

		mav.setViewName("redirect:/CartPage");
		return mav;
	}

	@RequestMapping(value = "/PayPage")
	public ModelAndView PayPage(String[] selAl,HttpSession session, RedirectAttributes ra) {
		ModelAndView mav = new ModelAndView();
		String mid = (String) session.getAttribute("loginId");
		if (mid == null) {
			System.out.println("로그인 안함");
			ra.addFlashAttribute("msg", "결제는 로그인후 이용 가능합니다.");

			mav.setViewName("redirect:/LoginPage");

		} else if (mid != null) {
			System.out.println("로그인  함");
			ArrayList<Album> alInfoList = new ArrayList<Album>();
			for (String al : selAl) {
				String[] al_Spilt = al.split("_");
				String alcode = al_Spilt[0];
				String alqty = al_Spilt[1];
				System.out.println(alqty);

				if (al_Spilt.length > 2) {
					String cacode = null;
					cacode = al.split("_")[2];
					int deleteResult = asvc.DeleteCartList(cacode);
					System.out.println(deleteResult);
				}
				Album alInfo = asvc.getAlbumInfo_alcode(alcode);
				alInfo.setAlqty(Integer.parseInt(alqty));
				alInfoList.add(alInfo);
				
			}
			System.out.println("결제페이지이동 완료");
			
			mav.addObject("mid", mid);
			mav.addObject("alList", alInfoList);
			mav.setViewName("Basic/PayPage");
		}
		return mav;
	}

	@RequestMapping(value = "/kakaoPay_ready")
	public @ResponseBody String kakaoPay_ready(String odmid, String odaddr, String orList, HttpSession session) {
		System.out.println("카카오 결제 준비 요청 - / kakaoPay_ready");
		// odInfo.setOdcode(odcode);

		Gson gson = new Gson();
		JsonArray orList_arr = JsonParser.parseString(orList).getAsJsonArray();
		Order odInfo = new Order();
		odInfo.setOdcode("앨범결제");
		odInfo.setOdmid(odmid);
		int totalPrice = 0;
		int totalQty = 0;
		String[] codeList = new String[orList_arr.size()];
		int i = 0;
		for (JsonElement od : orList_arr) {
			Order oddto = gson.fromJson(od, Order.class);
			oddto.setOdcode(asvc.odcodeseting());
			oddto.setOdaddr(odaddr);
			oddto.setOdmid(odmid);
			System.out.println(oddto);
	        int price = od.getAsJsonObject().get("odprice").getAsInt();
	        int qty = od.getAsJsonObject().get("odqty").getAsInt();
	        
	        totalPrice += price * qty; // 가격과 수량을 곱한 값을 더함
	        totalQty += qty;
			codeList[i] = oddto.getOdcode();
			i++;

			int insertOd = asvc.insertOdInfo(oddto);
		}
		for (String testStr : codeList) {
			System.out.println("code : " + testStr);
		}
		session.setAttribute("codeList", codeList);
		odInfo.setOdprice(totalPrice + "");
		odInfo.setOdqty(totalQty + "");
		System.out.println(totalQty);
		System.out.println(totalPrice);
		String result = asvc.kakaoPay_ready(odInfo, session);
		return result;
	}

	@RequestMapping(value = "/kakaoPay_approval")
	public ModelAndView kakaoPay_approval(String pg_token, HttpSession session) {
		System.out.println("카카오 결제 승인 요청");
		ModelAndView mav = new ModelAndView();
		System.out.println("pg_token : " + pg_token);
		String tid = (String) session.getAttribute("tid");
		System.out.println("tid : " + tid);
		ArrayList<String> odcodeList = new ArrayList<String>();
		session.setAttribute("odcodeList", odcodeList);
		System.out.println(session.getAttribute("odcodeList"));
		String odcode = (String) session.getAttribute("odcode");
		String loginId = (String) session.getAttribute("loginId");
		String result = asvc.kakaoPay_approval(tid, pg_token, odcode, loginId);
		if (result == null) {
			System.out.println("결제 오류");
			String[] codeList = (String[]) session.getAttribute("codeList");
			for (int i = 0; i < codeList.length; i++) {
				System.out.println(codeList[i]);
				asvc.cancelReserve(codeList[i]);
			}
			mav.addObject("payResult", "N");
		} else {
			System.out.println("결제 승인");
			mav.addObject("payResult", "Y");
		}
		session.removeAttribute("mid");
		session.removeAttribute("odcode");
		mav.setViewName("/Basic/PayResult");
		return mav;
	}

	@RequestMapping(value = "/kakaoPay_cancel")
	public ModelAndView kakaoPay_cancel(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String[] codeList = (String[]) session.getAttribute("codeList");
		for (int i = 0; i < codeList.length; i++) {
			System.out.println(codeList[i]);
			asvc.cancelReserve(codeList[i]);
		}
		session.removeAttribute("codeList");
		mav.addObject("payResult", "N");
		mav.setViewName("/Basic/PayResult");
		return mav;
	}

	// 팝송 앨범 크롤링
	@RequestMapping(value = "/getYes24Pop")
	public ModelAndView getYes24Pop() throws IOException {
		System.out.println("Yes24 앨범(팝송) 정보 수집 요청");

		// 추가된 앨범 개수 받아올 거
		int addCount = asvc.addYes24Pop();
		System.out.println("추가: " + addCount);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");

		return mav;
	}

	// 가요 앨범 크롤링
	@RequestMapping(value = "/getYes24Kpop")
	public ModelAndView getYes24Kpop() throws IOException {
		System.out.println("Yes24 앨범(가요) 정보 수집 요청");

		// 추가된 앨범 개수 받아올 거
		int addCount = asvc.addYes24Kpop();
		System.out.println("추가: " + addCount);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");

		return mav;
	}
	

	
	

}
