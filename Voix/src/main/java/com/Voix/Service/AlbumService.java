package com.Voix.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voix.Dao.AlbumDao;
import com.Voix.Dto.Album;
import com.Voix.Dto.Cart;
import com.Voix.Dto.Order;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Service
public class AlbumService {
	@Autowired
	private AlbumDao adao;

	public ArrayList<HashMap<String, String>> getAlbumtList_map() {
		
		return adao.selectAlbum_map();
	}

	public ArrayList<Album> getAlbumInfoList(String altitle) {
		
		return adao.selectAlbumInfo_map(altitle);
	}
	public Album getAlbumInfo_alcode(String alcode) {
		System.out.println("SERVICE getAlbumInfo_alcode");
		return adao.getAlbumInfo_alcode(alcode);
	}
	public int insertOdInfo(Order odInfo) {
		System.out.println("SERVICE insertOdinfo");
		int insertOd = adao.insertOdInfo(odInfo);
		return insertOd;
	}
	public String genCode(String currentCode) {
		System.out.println("Album - genCode() 호출: " + currentCode);
		// ㄴcurrentCode = MV00000 가 들어오면 MV00001로 바꿔줄거임 (영문, 숫자 분리할거임)
		// ㄴnewCode = MV00001

		String strCode = currentCode.substring(0, 1); // 영화코드에 영어부분 = MV // cf)substring(0,2) = 0번 인덱스부터 2번 앞까지 사용하겠다. = 0번 인덱스부터 1번 인덱스까지만 사용
		int numCode = Integer.parseInt(currentCode.substring(1)); // 영환코드에 숫자부분 = MV 뒤 5자리

		String newCode = strCode + String.format("%04d", numCode + 1);
		return newCode;
	}
	
	
	public int insetCart(String caalcode, int caqty, String loginId) {
		
		
		
		String maxCaCode = adao.selectMaxCaCode(); 
		System.out.println("maxCaCode: " + maxCaCode);
		Cart cart = new Cart();
		cart.setCaalcode(caalcode);
		cart.setCaqty(caqty);
		cart.setCamid(loginId);
		String cacode = genCode(maxCaCode); 
		System.out.println("cacode: " + cacode);
		cart.setCacode(cacode);
		int insertCount = 0;
			try {
				int insertResult = adao.insertCart(cart);
				insertCount += insertResult; // insertCount = insertCount + insertResult
				maxCaCode = cacode; // MV00001
			} catch (Exception e) {
			} // try catch End
			return insertCount;
		} // for End

		
	

	public ArrayList<HashMap<String, String>> CartList(String loginId) {
		// TODO Auto-generated method stub
		return adao.selectCartList(loginId);
	}

	public int DeleteCartList(String cacode) {
		
		return adao.DeleteCartList(cacode);
	}
	public String kakaoPay_ready(Order odInfo, HttpSession session) {
		System.out.println("service kakaoPay_ready()");
		System.out.println(odInfo);
		String requestUrl = "https://kapi.kakao.com/v1/payment/ready";
		HashMap<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("partner_order_id", odInfo.getOdcode());
		requestParams.put("partner_user_id", odInfo.getOdmid());
		requestParams.put("item_name", "앨범결제");
		requestParams.put("quantity", odInfo.getOdqty());
		requestParams.put("total_amount", odInfo.getOdprice());
		requestParams.put("tax_free_amount", "0");
		requestParams.put("approval_url", "http://localhost:8080/kakaoPay_approval");
		requestParams.put("cancel_url", "http://localhost:8080/kakaoPay_cancel");
		requestParams.put("fail_url", "http://localhost:8080/kakaoPay_fail");

		String result = null;
		try {
			String response = kakaoResponse_json(requestUrl, requestParams);
			/* tid, next_redirect_pc_url */
			JsonObject re = (JsonObject)JsonParser.parseString(response);
			String tid = re.get("tid").getAsString();
			String nextUrl = re.get("next_redirect_pc_url").getAsString();
			System.out.println("tid : " + tid);
			session.setAttribute("tid", tid);
			session.setAttribute("odcode", odInfo.getOdcode());
			System.out.println("nextUrl : " + nextUrl);
			result = nextUrl;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result; // 결제 QR 페이지 URL
	}

	private String kakaoResponse_json(String requestUrl, HashMap<String, String> requestParams) throws IOException {
		System.out.println("SERVICE kakaoResponse_json() 호출");
		StringBuilder urlBuilder = new StringBuilder(requestUrl); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("cid", "UTF-8") + "=TC0ONETIME"); /* 가맹점 코드 */
		for (String key : requestParams.keySet()) {
			urlBuilder.append("&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(requestParams.get(key), "UTF-8")); /* 요청 파라메터 */
		}

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "KakaoAK 2a9875678cefa0641477312f89b71e56");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
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
		System.out.println(sb.toString());

		return sb.toString();
	}

	public String kakaoPay_approval(String tid, String pg_token, String odcode, String loginId) {
		System.out.println("service kakaoPay_approval()");
		String requestUrl = "https://kapi.kakao.com/v1/payment/approve";
		HashMap<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("tid", tid);
		requestParams.put("partner_order_id", odcode);  /* recode */
		requestParams.put("partner_user_id", loginId);    /* loginId */
		requestParams.put("pg_token", pg_token);
		
		String result = null;
		try {
			String response = kakaoResponse_json(requestUrl, requestParams);
			result = response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result == null) {
			
		}
		
		return result;
	}

	public int cancelReserve(String odcode) {
		return adao.deleteReserve(odcode);
		
	}

	public String odcodeseting() {
		String MaxOdcode = adao.getmaxOdcode();
		String addodcode = genCode(MaxOdcode);
		return addodcode;
	}
	public ArrayList<HashMap<String, String>> selectTitle(String searchKeyword) {
		ArrayList<HashMap<String, String>> searchList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> titleList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();
			try {
				titleList = adao.selectSearch_Title(searchKeyword);				
			} catch (Exception e) {
			}
			System.out.println(titleList);
			try {
				contentList = adao.selectSearch_Content(searchKeyword);				
			} catch (Exception e) {
			}
			searchList.addAll(titleList);
			searchList.addAll(contentList);
		return searchList;
	} 
	


}