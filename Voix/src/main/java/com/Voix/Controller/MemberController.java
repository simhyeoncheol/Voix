package com.Voix.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Voix.Dto.Member;
import com.Voix.Service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService msvc;

	@RequestMapping(value = "/LoginPage")
	public ModelAndView LoginPage() {
		System.out.println("/LoginPage 요청");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/Member/LoginPage");
		return mav;
	}

	@RequestMapping(value = "/IdFindPage")
	public ModelAndView IdFindPage() {
		System.out.println("/IdFindPage 요청");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/Member/IdFindPage");
		return mav;
	}

	@RequestMapping(value = "/PwFindPage")
	public ModelAndView PwFindPage() {
		System.out.println("/PwFindPage 요청");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/Member/PwFindPage");
		return mav;
	}

	@RequestMapping(value = "/JoinPage")
	public ModelAndView JoinPage() {
		System.out.println("/JoinPage 요청");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/Member/JoinPage");
		return mav;
	}

	@RequestMapping(value = "/memberIdCheck")
	public @ResponseBody String memberIdcheck(String inputId) {
		System.out.println("아이디 중복 확인 요청");
		System.out.println("중복 확인 아이디" + inputId);
		String checkResult = msvc.idCheck(inputId);

		return checkResult;
	}

	@RequestMapping(value = "/Join")
	public ModelAndView Login(String Id, String RePw, String Name, String Address, String DetailAddress, String memailId, String memailDomain, MultipartFile mfile, HttpSession session) {
		System.out.println("회원가입처리");
		ModelAndView mav = new ModelAndView();
		Member mem = new Member();
		mem.setMid(Id);
		mem.setMpw(RePw);
		mem.setMname(Name);
		mem.setMaddr(Address + " " + DetailAddress);
		mem.setMemail(memailId + "@" + memailDomain);
		mem.setMfile(mfile);
		System.out.println(mem);
		int Join = 0;
		try {
			Join = msvc.getinsertMemberJoin_comm(mem, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Join > 0) {
			System.out.println("성공");
			mav.setViewName("/Member/LoginPage");
		} else {
			System.out.println("실패");
			mav.setViewName("/Member/JoinPage");
		}
		return mav;
	}

	@RequestMapping(value = "/emailIdCheck")
	public @ResponseBody String emailIdCheck(String inputId, String memailId, String memailDomain) {
		String CheckResult = msvc.emailIdCheck(inputId, memailId, memailDomain);
		System.out.println(CheckResult);

		if (CheckResult == null) {
			System.out.println("아이디 이메일이 맞지않음");
			return null;
		} else {
			return CheckResult;
		}

	}

	@RequestMapping(value = "/updatePw")
	public ModelAndView updatePw(String mid, String mpw, RedirectAttributes ra) {
		ModelAndView mav = new ModelAndView();

		System.out.println(mpw);
		System.out.println(mid);
		int upadateResult = msvc.upadtePw(mpw, mid);
		if (upadateResult > 0) {
			mav.setViewName("redirect:/LoginPage");
			ra.addFlashAttribute("msg", "비밀번호가 변경 되었습니다.");
		} else {
			mav.setViewName("redirect:/");
			ra.addFlashAttribute("msg", "비밀번호 변경에 실패했습니다.");
		}
		return mav;
	}

	@RequestMapping("/mailCheck")
	public @ResponseBody String mailCheck(String email) {
		System.out.println("이메일 인증 요청");
		System.out.println(email);
		return msvc.mailCheck(email);
	}

	@RequestMapping("/FindId")
	public @ResponseBody String FindId(String email) {
		System.out.println("id 찾기");
		System.out.println(email);

		return msvc.FindId(email);
	}

	@RequestMapping(value = "/MyInfoPage")
	public ModelAndView MyInfoPage(HttpSession session, RedirectAttributes ra) {
		System.out.println("/MyInfoPage 요청");
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");

		Member memberInfo = msvc.memberInfo(loginId);
		mav.addObject("mInfo", memberInfo);

		ArrayList<HashMap<String, String>> newsLike = msvc.newsLike(loginId);
		mav.addObject("newsLikeList", newsLike);

		ArrayList<HashMap<String, String>> albumsLike = msvc.albumsLike(loginId);
		mav.addObject("albumsLikeList", albumsLike);

		ArrayList<HashMap<String, String>> ticketsLike = msvc.ticketsLike(loginId);
		mav.addObject("ticketsLikeList", ticketsLike);

		ArrayList<HashMap<String, String>> songsLike = msvc.songsLike(loginId);
		mav.addObject("songsLikeList", songsLike);

		ArrayList<HashMap<String, String>> blogLike = msvc.blogLike(loginId);
		mav.addObject("blogLikeList", blogLike);

		ArrayList<HashMap<String, String>> newsReview = msvc.newsReview(loginId);
		mav.addObject("newsReviewList", newsReview);
		
		ArrayList<HashMap<String, String>> blogReview = msvc.blogReview(loginId);
		mav.addObject("blogReviewList", blogReview);
		
		ArrayList<HashMap<String, String>> albumsReview = msvc.albumsReview(loginId);
		mav.addObject("albumsReviewList", albumsReview);
		
		ArrayList<HashMap<String, String>> ticketReview = msvc.ticketReview(loginId);
		mav.addObject("ticketReviewList", ticketReview);
		
		ArrayList<HashMap<String, String>> songsReview = msvc.songsReview(loginId);
		mav.addObject("songsReviewList", songsReview);

		ArrayList<HashMap<String, String>> AlbumOrderList = msvc.AlbumOrderList(loginId);
		mav.addObject("AlbumOrderList", AlbumOrderList);

		mav.setViewName("/Member/MyInfoPage");
		return mav;

	}

	// 로그인
	@RequestMapping(value = "/memberLogin")
	public ModelAndView memberLogin(String mid, String mpw, HttpSession session, RedirectAttributes ra) {
		System.out.println("로그인 처리 요청-/memberLogin");
		ModelAndView mav = new ModelAndView();

		// 1.로그인할 아이디, 비밀번호 파라메터 확인
		System.out.println(mid);
		System.out.println(mpw);

		// 2.SERVICE = 로그인 회원정보 조회 호출
		Member loginMember = msvc.getLoginMemberInfo(mid, mpw);

		if (loginMember == null) {
			System.out.println("로그인 실패");
			ra.addFlashAttribute("msg", "아이디 또는 비밀번호 일치하지 않습니다.");
			mav.setViewName("redirect:/LoginPage");

		} else {
			System.out.println("로그인 성공");
			session.setAttribute("loginId", loginMember.getMid());
			session.setAttribute("loginPw", loginMember.getMpw());
			session.setAttribute("loginProfile", loginMember.getMimg());
			session.setAttribute("loginState", loginMember.getMstate());
			session.setAttribute("loginName", loginMember.getMname());

			ra.addFlashAttribute("msg", "로그인 성공.");
			mav.setViewName("redirect:/");
		}
		return mav;
	}

	// 카카오로그인
	@RequestMapping(value = "/memberLogin_kakao")
	// ajax = ReponseBody
	public @ResponseBody String memberLogin_kakao(String id, HttpSession session) {
		System.out.println("카카오 로그인 요청");
		System.out.println("카카오 id: " + id);
		// Member, MemberService, MemberDao
		Member loginMember = msvc.getLoginMemberInfo(id);
		if (loginMember == null) {
			System.out.println("카카오 계정 정보 없음");
			return "N";
		} else {
			System.out.println("카카오 계정 정보 있음");
			System.out.println("로그인 처리");
			session.setAttribute("loginId", loginMember.getMid());
			session.setAttribute("loginName", loginMember.getMname());
			session.setAttribute("loginProfile", loginMember.getMimg());
			session.setAttribute("loginState", loginMember.getMstate());
			return "Y";
		}

	}

	@RequestMapping(value = "/memberJoin_kakao")
	public @ResponseBody String memberJoin_kakao(Member member) {
		System.out.println("카카오 계정 - 회원가입요청 - memberJoin_kakao");
		System.out.println(member);
		int result = msvc.registMember_kakao(member);
		return result + "";

	}

	@RequestMapping(value = "/memberLogout")
	public String memberLogout(HttpSession session, RedirectAttributes ra) {
		session.invalidate();
		ra.addFlashAttribute("msg", "로그아웃 되었습니다.");
		return "redirect:/";
	}

	// 회원정보 수정
	@RequestMapping(value = "/MyInfoUpdate")
	public ModelAndView MyInfoUpdate(HttpSession session, RedirectAttributes ra) {
		System.out.println("/MyInfoUpdate 요청");
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");

		Member memberInfo = msvc.memberInfo(loginId);
		mav.addObject("mInfo", memberInfo);

		mav.setViewName("/Member/MyInfoUpdate");
		return mav;
	}

	@RequestMapping(value = "/memberModify")
	public ModelAndView memberModify(Member member, String mid, String mpw, String mname, String address, String detailAddress, String memail, MultipartFile mfile, String mstate, RedirectAttributes ra, HttpSession session) throws IllegalStateException, IOException {
		System.out.println("회원정보수정요청");
		ModelAndView mav = new ModelAndView();
		Member mem = new Member();
		mem.setMid(mid);
		mem.setMpw(mpw);
		mem.setMname(mname);
		mem.setMaddr(address + " " + detailAddress);
		mem.setMemail(memail);
		mem.setMfile(mfile);
		mem.setMstate(mstate);
		System.out.println(mem);
		int updateResult = msvc.modifyMemberInfo(mem, session);
		if (updateResult > 0) {
			System.out.println("회원정보 수정 성공");
			session.setAttribute("loginProfile", mem.getMimg());
			ra.addFlashAttribute("msg", "회원정보가 수정 되었습니다");
		} else {
			System.out.println("회원정보 수정 실패");
			ra.addFlashAttribute("msg", "회원정보가 수정을 실패했습니다");
		}
		mav.setViewName("redirect:/MyInfoPage");
		return mav;
	}

	@RequestMapping(value = "/PwUpdatePage")
	public ModelAndView PwUpdatePage(HttpSession session) {
		System.out.println("/PwUpdatePage 요청");
		ModelAndView mav = new ModelAndView();
		String loginId = (String) session.getAttribute("loginId");
		Member memberInfo = msvc.memberInfo(loginId);
		mav.addObject("mInfo", memberInfo);
		mav.setViewName("/Member/PwUpdatePage");
		return mav;
	}
	Member mem = null;
	@RequestMapping(value = "/naverResult")
	public ModelAndView naverResult(String code, String state, HttpSession session) throws IOException {
		ModelAndView mav = new ModelAndView();
		String getToken = msvc.getToken(code, state);
		System.out.println("getToken : " + getToken);
		Member navergetInfo = msvc.memberlogin_naver(getToken);
		Member loginInfo = null;
		try {
			loginInfo = msvc.getLoginMemberInfo(navergetInfo.getMid());
		} catch (Exception e) {
		}

		if (loginInfo != null) {
			session.setAttribute("loginId", navergetInfo.getMid());
			session.setAttribute("loginProfile", navergetInfo.getMimg());
			session.setAttribute("loginState", navergetInfo.getMstate());
			session.setAttribute("loginName", navergetInfo.getMname());
		} else {
			mem = navergetInfo;
		}
		mav.addObject("N", loginInfo);
		mav.setViewName("/Member/naverLoginResult");
		return mav;
	}
	
	@RequestMapping(value="/memberJoin_naver")
	public @ResponseBody int memberJoin_naver() {
		System.out.println(mem);
		int insertResult = msvc.insertNaverLogin(mem);
		return insertResult;
	}

	@RequestMapping(value = "/naverPopup")
	public String naverPopup() throws UnsupportedEncodingException {
		String clientId = "uqIAhwBYmZxJrA3aR_ze";// 애플리케이션 클라이언트 아이디값";
		String redirectURI = URLEncoder.encode("http://localhost:8080/naverResult", "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state;

		return "redirect:" + apiURL;
	}	
	@RequestMapping(value="/PayHistory")
	public ModelAndView PayHistory(HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		String mid = session.getAttribute("loginId").toString();
		ArrayList<HashMap<String, String>> OrderInfo = msvc.getOrderInfo(mid);
		mav.addObject("OrderInfo",OrderInfo);
		mav.setViewName("/Member/PayHistoryPage");
				
		return mav;
		
	}

}
