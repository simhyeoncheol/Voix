package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.Voix.Dto.Member;

public interface MemberDao {

	Member selectMemberInfo_mapper(String inputId);

	int getinsertMemberJoin_comm(Member mem);

	String selectemailId(@Param ("inputId") String inputId, @Param ("email") String email);

	String FindId(String email);

	int updatePassword(@Param("mpw")String mpw,@Param("mid") String mid);

	ArrayList<HashMap<String, String>> newsLikeList(String loginId);

	ArrayList<HashMap<String, String>> albumsLikeList(String loginId);

	ArrayList<HashMap<String, String>> ticketsLikeList(String loginId);

	ArrayList<HashMap<String, String>> songsLikeList(String loginId);
	
	ArrayList<HashMap<String, String>> blogLikeList(String loginId);

	public Member loginMember(@Param("mid") String inputId, @Param("inputPw") String inputPw);

	Member selectMemberInfo(String id);

	int insertMember_kakao(Member member);

	int updateMemberInfo(Member member);

	int insertNaverLogin(Member navergetInfo);

	ArrayList<HashMap<String, String>> selectOrderInfo(String mid);

	ArrayList<HashMap<String, String>> newsReviewList(String loginId);

	ArrayList<HashMap<String, String>> blogReviewList(String loginId);

	ArrayList<HashMap<String, String>> albumsReviewList(String loginId);

	ArrayList<HashMap<String, String>> ticketReviewList(String loginId);

	ArrayList<HashMap<String, String>> songsReviewList(String loginId);

	ArrayList<HashMap<String, String>> AlbumOrderList(String loginId);
}
