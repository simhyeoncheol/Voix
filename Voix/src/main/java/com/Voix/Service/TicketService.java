package com.Voix.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voix.Dao.TicketDao;
import com.Voix.Dto.Ticket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class TicketService {
	@Autowired
	private TicketDao tdao;

	public ArrayList<HashMap<String, String>> getTicketList_map() {

		return tdao.selectTicket_map();
	}

	public Ticket getTkInfo(String tkcode) {
		System.out.println("SERVICE - 티켓정보 출력");
		return tdao.getTkInfo(tkcode);
	}

	public String getMapXY(String tkplace) throws IOException {
		System.out.println("SERVICE - getMapXY 호출");
		StringBuilder urlBuilder = new StringBuilder("https://dapi.kakao.com/v2/local/search/keyword");
		urlBuilder.append("?" + URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(tkplace, "UTF-8"));

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "KakaoAK de39587ee41403602625b2b26329f02c");
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
		JsonElement xyjson = JsonParser.parseString(sb.toString()).getAsJsonObject().get("documents").getAsJsonArray().get(0).getAsJsonObject();
		Gson gson = new Gson();
		String result = gson.toJson(xyjson);
		return result;
	}

	public String genCode(String currentCode) {
		System.out.println("genCode()호출: " + currentCode);
		String strCode = currentCode.substring(0,2);
		int numCode = Integer.parseInt(currentCode.substring(2));
		
		
		String newCode = strCode + String.format("%04d", numCode+1);
		System.out.println(newCode);
		return newCode;
	}


	public int registReview(String restate, String recontent, String rewriter) {
		System.out.println("service - registReview()");
		String maxRvCode = tdao.selectMaxReCode();
		String recode = genCode(maxRvCode);
		int registReview = tdao.registReview(recode, restate, recontent, rewriter);
		System.out.println(registReview);
		return registReview;
	}

	public ArrayList<HashMap<String, String>> selectReviewList(String tkcode) {
		System.out.println("selectReivewList  호출");
		ArrayList<HashMap<String, String>> Resultre = tdao.selectReviewList(tkcode);
		return Resultre;
	}

	public int deleteReview(String recode) {
		System.out.println("SERVEICE - deleteReview 호출");
		return tdao.deleteReview(recode);
	}
	public ArrayList<Ticket> getTkTitle(String tkplace) {
		System.out.println("SERVICE getTkTitle");
		System.out.println(tkplace);
		ArrayList<Ticket> result = null;
		try {
			result = tdao.getTkTitle(tkplace);			
		} catch (Exception e) {

		}
		System.out.println(result);
		return result;
	}
	public ArrayList<HashMap<String, String>> selectTitle(String searchKeyword) {
		ArrayList<HashMap<String, String>> searchList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> titleList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();
			try {
				titleList = tdao.selectSearch_Title(searchKeyword);				
			} catch (Exception e) {
			}
			System.out.println(titleList);
			try {
				contentList = tdao.selectSearch_Content(searchKeyword);				
			} catch (Exception e) {
			}
			searchList.addAll(titleList);
			searchList.addAll(contentList);
		return searchList;
	}
	public HashMap<String, String> selectMainTicket(String t) {
		HashMap<String, String> result = tdao.selectMainTicket();
		return result;
	}
	public int likeTicket(String like, String mid) {
		System.out.println("SERVICE - 티켓 찜");
		return tdao.likeTicket(like,mid);
	}
	public int getTicket_melon(Ticket tK) throws IOException {
		System.out.println("SERVICE - getTicket_melon 호출");
		return tdao.getTicket_melon(tK);
	}

	public String getMaxTkCode() {
		return tdao.getMaxTkCode();
	}

	public int getTicket_Interticket(Ticket TK) {
		// TODO Auto-generated method stub
		return tdao.getTicket_Interticket(TK);
	}

	public int getTicket_Yes24ticket(Ticket TK) {
		// TODO Auto-generated method stub
		return tdao.getTicket_Yes24ticket(TK);
	}

	public int getTicket_11bungaTicket(Ticket TK) {
		// TODO Auto-generated method stub
		return tdao.getTicket_11bungaTicket(TK);
	}

	public ArrayList<HashMap<String, String>> getTicketList_ChooseSite(String siteVal) {
		System.out.println("서비스 사이트선택");
		return tdao.getTicketList_ChooseSite(siteVal);
	}

	public ArrayList<String> getLikedTicketList(String mid) {
		System.out.println("SERVICE - 찜 조회");
		return tdao.getLikedTicketList(mid);
	}
	
	public int unlikeTicket(String like, String mid) {
		System.out.println("SERVICE - 찜 취소");
		return tdao.unlikeTicket(like,mid);
	}

	public ArrayList<HashMap<String, String>> selectTitle(String searchKeyword, String siteValue) {
		System.out.println("ㅗ"+siteValue);
		System.out.println("ㅗ"+searchKeyword);
		ArrayList<HashMap<String, String>> searchList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> titleList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();
			try {
				titleList = tdao.selectSearch_TitleSite(searchKeyword,siteValue);				
			} catch (Exception e) {
			}
			System.out.println(titleList);
			try {
				contentList = tdao.selectSearch_ContentSite(searchKeyword,siteValue);				
			} catch (Exception e) {
			}
			searchList.addAll(titleList);
			searchList.addAll(contentList);
		return searchList;
	}
	


}
