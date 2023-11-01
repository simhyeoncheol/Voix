package com.Voix.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voix.Dao.BlogDao;
import com.Voix.Dto.Blog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class BlogService {

	@Autowired
	private BlogDao bdao;
	
	@Autowired
	private TicketService tsvc;
	
	public HashMap<String, String> selectMainBlog(String themaStr) {
		return bdao.selectMainBlog();
	}


	public ArrayList<HashMap<String, String>> getBlogList_map() {

		return bdao.selectBlog_map();
	}


	public Blog getbgInfo(String bgcode) {
		System.out.println("SERVICE - 블로그정보 출력");
		return bdao.getBlogInfo(bgcode);
	}


	public ArrayList<HashMap<String, String>> selectReviewList(String bgcode) {
		System.out.println("selectReivewList  호출");
		ArrayList<HashMap<String, String>> Resultre = bdao.selectReviewList(bgcode);
		return Resultre;
	}


	public int BlogRegistReview(String restate, String recontent, String rewriter) {
		System.out.println("service - registReview()");
		String maxRvCode = bdao.selectMaxReCode();
		String recode = tsvc.genCode(maxRvCode);
		int registReview = bdao.BlogRegistReview(recode, restate, recontent, rewriter);
		System.out.println(registReview);
		return registReview;
	}


	public int deleteBlogReview(String recode) {
		System.out.println("SERVEICE - deleteReview 호출");
		return bdao.deleteBlogReview(recode);
	}


	public int likeBlog(String like, String mid) {
		System.out.println("SERVICE- 블로그 찜");
		return bdao.likeBlog(like,mid);
	}


	public ArrayList<HashMap<String, String>> selectBlogHitList() {
		System.out.println("조회수 목록 조회");
		return bdao.selectBlogHitList();
	}


	public int UpdateBlogBigHit(String bgcode) {
		return bdao.UpdateBlogBigHit(bgcode);
	}

	
	public ArrayList<String> getLikedBlogList(String mid) {
		System.out.println("SERVEICE - 찜 조회");
		 return bdao.getLikedBlogList(mid);
	}
	
	public int unlikeBlog(String like, String mid) {
		 System.out.println("SERVEICE - 찜 삭제");
		 return bdao.unlikeBlog(like, mid);
	}

	public int countBoardListTotal() {
		System.out.println("SERVICE - 페이징 총 조회");
		return bdao.countBoard();
	}


	public List<Map<String, Object>> selectBoardList(String startBGCODE, String endBGCODE) {
		System.out.println("SERVICE - 페이징 넘길 때 코드");
		return bdao.selectBoardList(startBGCODE, endBGCODE);
	}
public String chatbotAPI(String inputText,String mid) {
		String secretKey = "cUlaRmRJSlNVYWVXY0NMb2tFdUtxUldEVWdRa1pPRU4=";
		String apiURL = "https://7mfycmpb8n.apigw.ntruss.com/VOIX/VOIX/";

		String chatbotMessage = ""; // 응답 메세지
		try {
			// String apiURL = "https://ex9av8bv0e.apigw.ntruss.com/custom_chatbot/prod/";

			URL url = new URL(apiURL);

			String message = getReqMessage(inputText);
			System.out.println("##" + message);

			String encodeBase64String = makeSignature(message, secretKey);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json;UTF-8");
			con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

			// post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(message.getBytes("UTF-8"));
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			BufferedReader br;

			if (responseCode == 200) { // Normal call
				System.out.println(con.getResponseMessage());
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				con.disconnect();
				System.out.println(sb.toString());
				String data1 = JsonParser.parseString(sb.toString())
						.getAsJsonObject().get("bubbles").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject().get("description").getAsString();
				System.out.println(data1);
				chatbotMessage = data1;
			} else { // Error occurred
				chatbotMessage = con.getResponseMessage();
			}
		} catch (Exception e) {
			System.out.println("ex"+e);
		}
		return chatbotMessage;
	}

	public static String makeSignature(String message, String secretKey) {

		String encodeBase64String = "";

		try {
			byte[] secrete_key_bytes = secretKey.getBytes("UTF-8");

			SecretKeySpec signingKey = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
			return encodeBase64String;

		} catch (Exception e) {
			System.out.println(e);
		}

		return encodeBase64String;

	}

	public static String getReqMessage(String voiceMessage) {

		String requestBody = "";

		try {

			JsonObject obj_gson = new JsonObject();

			long timestamp = new Date().getTime();

			System.out.println("##" + timestamp);
			obj_gson.addProperty("version", "v2");
			obj_gson.addProperty("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
//=> userId is a unique code for each chat user, not a fixed value, recommend use UUID. use different id for each user could help you to split chat history for users.
			obj_gson.addProperty("timestamp", timestamp);

			JsonObject bubbles_obj_gson = new JsonObject();

			JsonObject data_obj_gson = new JsonObject();
			data_obj_gson.addProperty("description", voiceMessage);

			bubbles_obj_gson.addProperty("type", "text");
			bubbles_obj_gson.add("data", data_obj_gson);

			JsonArray bubbles_array_gson = new JsonArray();
			bubbles_array_gson.add(bubbles_obj_gson);

			obj_gson.add("bubbles", bubbles_array_gson);

			obj_gson.addProperty("event", "send");

			requestBody = new Gson().toJson(obj_gson);

		} catch (Exception e) {
			System.out.println("## Exception : " + e);
		}
		return requestBody;
	}

	public String jsonToString(String jsonResultStr) {
		String resultText = "";
		// API 호출 결과 받은 JSON 형태 문자열에서 텍스트 추출
		// JSONParser 사용하지 않음

		JsonObject jsonObj_gson = JsonParser.parseString(jsonResultStr).getAsJsonObject();
		JsonArray chatArray_gson = jsonObj_gson.get("bubbles").getAsJsonArray();
		System.out.println("chatArray_gson.size : " + chatArray_gson.size());
		
		if (chatArray_gson.size() > 0) {
			JsonObject tempObj_gson = chatArray_gson.get(0).getAsJsonObject();
			JsonObject dataObj_gson = tempObj_gson.get("data").getAsJsonObject();
			if (dataObj_gson != null) {
				resultText += dataObj_gson.get("description").getAsString();
			}
		} else {
			System.out.println("없음");
		}
		return resultText;
	}


	public ArrayList<HashMap<String, String>> selectTitle(String searchKeyword) {
		ArrayList<HashMap<String, String>> searchList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> titleList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();
			try {
				titleList = bdao.selectSearch_Title(searchKeyword);				
			} catch (Exception e) {
			}
			System.out.println(titleList);
			try {
				contentList = bdao.selectSearch_Content(searchKeyword);				
			} catch (Exception e) {
			}
			searchList.addAll(titleList);
			searchList.addAll(contentList);
		return searchList;
	}
	
}
