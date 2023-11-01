package com.Voix.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Voix.Dao.MemberDao;
import com.Voix.Dto.Member;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
@Service
public class MemberService {

	@Autowired
	private MemberDao mdao;

	@Autowired
	private JavaMailSenderImpl mailSender;

	// 난수 발생
	private int authNumber;

	public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
	}

	public String idCheck(String inputId) {
		System.out.println("SERVICE - idCheck() 호출");
		Member member_mapper = mdao.selectMemberInfo_mapper(inputId);
		String Result = "N";
		if (member_mapper == null) {
			Result = "Y";
		}

		return Result;
	}

	public String mailCheck(String email) {
		makeRandomNumber();
		String setFrom = "ih0625@naver.com"; // email-config에 설정한 자신의 이메일 주소를 입력
		String toMail = email;
		String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
		String content = "홈페이지를 방문해주셔서 감사합니다." + // html 형식으로 작성 !
				"<br><br>" + "인증 번호는 " + authNumber + "입니다." + "<br>" + "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
		// 이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}

//이메일 전송 메소드

	public void mailSend(String setFrom, String toMail, String title, String content) {
		System.out.println(setFrom + " " + toMail + " " + title + " " + content);
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String FindId(String email) {
		System.out.println("SERVICE - FindId");
		return mdao.FindId(email);
	}

	public int getinsertMemberJoin_comm(Member mem, HttpSession session) throws IllegalStateException, IOException {
		System.out.println("SERVICE - getinsertMemberJoin_comm");

		MultipartFile mfile = mem.getMfile();
		String mprofile = ""; // 파일명 저장할 변수
		String savePath = session.getServletContext().getRealPath("/resources/users/memberProfile"); // 파일을 저장할 경로

		System.out.println(savePath);
		System.out.println(mfile);

		// 첨부파일 유무 확인
		if (!mfile.isEmpty()) {
			System.out.println("첨부파일 있음");
			UUID uuid = UUID.randomUUID();
			String code = uuid.toString();

			mprofile = code + "_" + mfile.getOriginalFilename();

			// 저장할 경로 resources/boardUpload 폴더에 파일저장?
			System.out.println("savePath: " + savePath);
			File newFile = new File(savePath, mprofile);
			mfile.transferTo(newFile);
		}

		System.out.println("파일이름: " + mprofile);
		mem.setMimg(mprofile);
		System.out.println(mem); // 제목, 내용, 작성자, 첨부파일명

		int Result = mdao.getinsertMemberJoin_comm(mem);
		return Result;
	}

	public String emailIdCheck(String inputId, String memailId, String memailDomain) {
		System.out.println("Service -emailIdCheck");
		String email = memailId + "@" + memailDomain;
		System.out.println(email);
		System.out.println(inputId);
		String Result = mdao.selectemailId(inputId, email);

		return Result;
	}

	public int upadtePw(String mpw, String mid) {
		System.out.println("SERVICE - updatePw");
		int result = mdao.updatePassword(mpw, mid);
		return result;
	}

	// 마이인포정보출력

	public ArrayList<HashMap<String, String>> newsLike(String loginId) {
		return mdao.newsLikeList(loginId);
	}

	public ArrayList<HashMap<String, String>> albumsLike(String loginId) {
		return mdao.albumsLikeList(loginId);
	}

	public ArrayList<HashMap<String, String>> ticketsLike(String loginId) {
		return mdao.ticketsLikeList(loginId);
	}

	public ArrayList<HashMap<String, String>> songsLike(String loginId) {
		return mdao.songsLikeList(loginId);
	}
	
	public ArrayList<HashMap<String, String>> blogLike(String loginId) {
		
		return mdao.blogLikeList(loginId);
	}

	public Member memberInfo(String loginId) {
		Member memInfo = mdao.selectMemberInfo(loginId);
		return memInfo;
	}

	// 로그인

	public Member getLoginMemberInfo(String mid, String mpw) {
		System.out.println("MemberService - getLoginMemberInfo()호출");
		Member login = mdao.loginMember(mid, mpw);
		return login;
	}

	// 카카오로그인
	public Member getLoginMemberInfo(String id) {
		System.out.println("service - getLoginMemberInfo_kakao 호출");
		return mdao.selectMemberInfo(id);
	}

	public int registMember_kakao(Member member) {
		System.out.println("service - registMember_kakao 호출");
		return mdao.insertMember_kakao(member);
	}

	// 회원정보수정
	public int modifyMemberInfo(Member member, HttpSession session) throws IllegalStateException, IOException {
		System.out.println("회원정보 수정 요청");

		MultipartFile mfile = member.getMfile();
		String mprofile = ""; // 파일명 저장할 변수
		String savePath = session.getServletContext().getRealPath("/resources/users/memberProfile"); // 파일을 저장할 경로

		System.out.println(savePath);
		System.out.println(mfile);

		// 첨부파일 유무 확인
		if (!mfile.isEmpty()) {
			System.out.println("첨부파일 있음");
			UUID uuid = UUID.randomUUID();
			String code = uuid.toString();

			mprofile = code + "_" + mfile.getOriginalFilename();

			// 저장할 경로 resources/boardUpload 폴더에 파일저장?
			System.out.println("savePath: " + savePath);
			File newFile = new File(savePath, mprofile);
			mfile.transferTo(newFile);
		}

		System.out.println("파일이름: " + mprofile);
		member.setMimg(mprofile);
		System.out.println(member); // 제목, 내용, 작성자, 첨부파일명

		int result = 0;
		try {
			result = mdao.updateMemberInfo(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Member memberlogin_naver(String token) throws IOException {
		System.out.println("SERVICE memberlogin_naver() 호출");
		StringBuilder urlBuilder = new StringBuilder("https://openapi.naver.com/v1/nid/me"); /* URL */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer "+token);
		System.out.println("Response code: " + conn.getResponseCode());

		if (conn.getResponseCode() != 200) {
			return null;
		}

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		Gson gson = new Gson();
		JsonElement json = JsonParser.parseString(sb.toString()).getAsJsonObject().get("response");
		String id = gson.toJson(json.getAsJsonObject().get("id")).substring(0,10);
		String profile_image = gson.toJson(json.getAsJsonObject().get("profile_image"));
		String email = gson.toJson(json.getAsJsonObject().get("email"));
		String name = gson.toJson(json.getAsJsonObject().get("name"));
		Member mem = new Member();
		mem.setMid(id);
		mem.setMimg(profile_image);
		mem.setMemail(email);
		mem.setMname(name);
		return mem;
	}

	public String getToken(String code, String state) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("https://nid.naver.com/oauth2.0/token"); /* URL */
	urlBuilder.append("?" + URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("authorization_code", "UTF-8"));
	urlBuilder.append("&" + URLEncoder.encode("client_id", "UTF-8") + "=" + URLEncoder.encode("uqIAhwBYmZxJrA3aR_ze", "UTF-8"));
	urlBuilder.append("&" + URLEncoder.encode("client_secret", "UTF-8") + "=" + URLEncoder.encode("k8hZ8jGGZA", "UTF-8"));
	urlBuilder.append("&" + URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8"));
	urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(state, "UTF-8"));
	

	URL url = new URL(urlBuilder.toString());
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	System.out.println("Response code: " + conn.getResponseCode());

	if (conn.getResponseCode() != 200) {
		return null;
	}

	BufferedReader rd;
	if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	} else {
		rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	}
	StringBuilder sb = new StringBuilder();
	String line;
	while ((line = rd.readLine()) != null) {
		sb.append(line);
	}
	rd.close();
	conn.disconnect();
	JsonElement token = JsonParser.parseString(sb.toString()).getAsJsonObject().get("access_token");
	Gson gson = new Gson();
	return gson.toJson(token);
	}

	public int insertNaverLogin(Member navergetInfo) {
		System.out.println("SERVICE insertNaverLogin");
		
		return mdao.insertNaverLogin(navergetInfo);
	}
	public ArrayList<HashMap<String, String>> getOrderInfo(String mid) {
		System.out.println("SERVICE getOrderInfo");
		
		
		return mdao.selectOrderInfo(mid);
	}
	
	public ArrayList<HashMap<String, String>> newsReview(String loginId) {
		// TODO Auto-generated method stub
		return mdao.newsReviewList(loginId);
	}

	public ArrayList<HashMap<String, String>> blogReview(String loginId) {
		// TODO Auto-generated method stub
		return mdao.blogReviewList(loginId);
	}

	public ArrayList<HashMap<String, String>> albumsReview(String loginId) {
		// TODO Auto-generated method stub
		return mdao.albumsReviewList(loginId);
	}

	public ArrayList<HashMap<String, String>> ticketReview(String loginId) {
		// TODO Auto-generated method stub
		return mdao.ticketReviewList(loginId);
	}

	public ArrayList<HashMap<String, String>> songsReview(String loginId) {
		// TODO Auto-generated method stub
		return mdao.songsReviewList(loginId);
	}

	public ArrayList<HashMap<String, String>> AlbumOrderList(String loginId) {
		
		return mdao.AlbumOrderList(loginId);
	}

}
