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

}