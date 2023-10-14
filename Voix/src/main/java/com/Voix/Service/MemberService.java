package com.Voix.Service;

import java.io.File;
import java.io.IOException;
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
		String savePath = session.getServletContext().getRealPath("/resources/users/me"); // 파일을 저장할 경로

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
	public Member getLoginMemberInfo_kakao(String id) {
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
		String savePath = session.getServletContext().getRealPath("/resources/users/me"); // 파일을 저장할 경로

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

}