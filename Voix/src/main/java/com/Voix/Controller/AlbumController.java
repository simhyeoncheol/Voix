package com.Voix.Controller;

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

		System.out.println(AlbumList_map);
		mav.addObject("AlbumListMap", AlbumList_map);
		mav.setViewName("Basic/AlbumPage");
		return mav;
	}

	@RequestMapping(value = "/AlbumInfoPage")
	public ModelAndView NewsInfoPage(String altitle) {
		ModelAndView mav = new ModelAndView();
		ArrayList<Album> AlbumInfoList = asvc.getAlbumInfoList(altitle);
		Album ALInfo = AlbumInfoList.get(0);
		System.out.println(AlbumInfoList);
		mav.addObject("ALInfo", ALInfo);
		mav.addObject("AlbumInfoList", AlbumInfoList);

		mav.setViewName("BasicInfo/AlbumInfoPage");
		return mav;
	}

	@RequestMapping(value = "/CartPage")
	public ModelAndView CartPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");
				ArrayList<HashMap<String,String>> CartList = asvc.CartList(loginId);
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
			}else {
				System.out.println("장바구니 추가 실패");
				ra.addFlashAttribute("msg","항목을 장바구니에 추가하는데 실패하였습니다. 다시 시도해주세요");
				mav.setViewName("redirect:/AlbumInfoPage");
			}
		}
		return mav;
	}
	@RequestMapping(value = "/DeleteCartList", method = RequestMethod.POST)
	public ModelAndView DeleteCartList(@RequestParam(value = "CartCheck") String[] CartCheck) {
		ModelAndView mav = new ModelAndView();
		
		for(int i = 0; i < CartCheck.length; i++) {
			System.out.println(CartCheck[i]);
			asvc.DeleteCartList(CartCheck[i]);
		}
		
		mav.setViewName("redirect:/CartPage");
		return mav;
	}
	
	@RequestMapping(value = "/PayPage")
	public ModelAndView PayPage(String[] selAl,HttpSession session,RedirectAttributes ra) {
		ModelAndView mav = new ModelAndView();
		String mid = (String) session.getAttribute("loginId");
		if (mid == null) {
			System.out.println("로그인 안함");
			ra.addFlashAttribute("msg", "결제는 로그인후 이용 가능합니다.");
			
			mav.setViewName("redirect:/LoginPage");
			
		} else if (mid != null) {
			System.out.println("로그인  함");
			ArrayList<Album> alInfoList = new ArrayList<Album>();
			for(String al : selAl) {
				String[] al_Spilt = al.split("_");
				String alcode = al_Spilt[0];
				String alqty = al_Spilt[1];
				
				if(al_Spilt.length > 2) {
					String cacode = null;
					cacode = al.split("_")[2];
					int deleteResult = asvc.DeleteCartList(cacode);
					System.out.println(deleteResult);
				}
				Album alInfo = asvc.getAlbumInfo_alcode(alcode);
				alInfo.setAlqty( Integer.parseInt(alqty)  );
				alInfoList.add(alInfo);
			}
			System.out.println("결제페이지이동 완료");
			
			mav.addObject("mid",mid);
			mav.addObject("alList",alInfoList);
			mav.setViewName("Basic/PayPage");
		}
		return mav;
	}
	
	@RequestMapping(value="/kakaoPay_ready")
	public @ResponseBody String kakaoPay_ready(String odmid,String odaddr,String orList,HttpSession session) {
		System.out.println("카카오 결제 준비 요청 - / kakaoPay_ready");
		//odInfo.setOdcode(odcode);
		
		Gson gson = new Gson();
		JsonArray orList_arr = JsonParser.parseString(orList).getAsJsonArray();
		Order odInfo = new Order();
		odInfo.setOdcode("앨범결제");
		odInfo.setOdmid(odmid);
		int totalPrice = 0;
		int totalQty = 0;
		String[] codeList = new String[orList_arr.size()];
		int i = 0;
		for( JsonElement od :  orList_arr ) {
			Order oddto = gson.fromJson(od, Order.class);
			oddto.setOdcode(asvc.odcodeseting());
			oddto.setOdaddr(odaddr);
			oddto.setOdmid(odmid);
			System.out.println(oddto);
			totalPrice +=  od.getAsJsonObject().get("odprice").getAsInt();
			totalQty +=  od.getAsJsonObject().get("odqty").getAsInt(); 
			codeList[i]=oddto.getOdcode();
			i++;
		
			int insertOd = asvc.insertOdInfo(oddto);
		}
		for(String testStr : codeList) {
			System.out.println("code : " + testStr);
		}
		session.setAttribute("codeList",codeList );
		odInfo.setOdprice(totalPrice+"");
		odInfo.setOdqty(totalQty+"");
		
		String result = asvc.kakaoPay_ready(odInfo, session);
		return result;
	}

	@RequestMapping(value = "/kakaoPay_approval")
	public ModelAndView kakaoPay_approval(String pg_token, HttpSession session) {
		System.out.println("카카오 결제 승인 요청");
		ModelAndView mav = new ModelAndView();
		System.out.println("pg_token : " + pg_token);
		String tid = (String)session.getAttribute("tid");
		System.out.println("tid : " + tid);
		ArrayList<String> odcodeList = new ArrayList<String>();
		session.setAttribute("odcodeList",odcodeList);
		System.out.println(session.getAttribute("odcodeList"));
		String odcode = (String)session.getAttribute("odcode");
		String loginId = (String)session.getAttribute("loginId");
		String result = asvc.kakaoPay_approval(tid, pg_token, odcode, loginId ); 
		if( result == null) {
		System.out.println("결제 오류");
		String[] codeList = (String[]) session.getAttribute("codeList");
		for(int i =0; i < codeList.length; i++) {
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
		for(int i =0; i < codeList.length; i++) {
			System.out.println(codeList[i]);
			asvc.cancelReserve(codeList[i]);
		}
		session.removeAttribute("codeList");
		mav.addObject("payResult", "N");
		mav.setViewName("/Basic/PayResult");
		return mav;
		}
	
	
	

}