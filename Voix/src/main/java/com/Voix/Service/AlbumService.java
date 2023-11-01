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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

	@Autowired
	private TicketService tsvc;

	public ArrayList<HashMap<String, String>> getAlbumtList_map() {
		return adao.selectAlbum_map();
	}

	public ArrayList<Album> getAlbumInfoList(String alcode) {
		return adao.selectAlbumInfo_map(alcode);
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
			JsonObject re = (JsonObject) JsonParser.parseString(response);
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
		requestParams.put("partner_order_id", odcode); /* recode */
		requestParams.put("partner_user_id", loginId); /* loginId */
		requestParams.put("pg_token", pg_token);

		String result = null;
		try {
			String response = kakaoResponse_json(requestUrl, requestParams);
			result = response;
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result == null) {

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
	
	public int likeAlbum(String like, String mid) {
		System.out.println("SERVICE- 엘범 찜");
		return adao.likeAlbum(like,mid);
	}
	
	public ArrayList<HashMap<String, String>> selectReviewList(String alcode) {
		System.out.println("selectReivewList  호출");
		ArrayList<HashMap<String, String>> Resultre = adao.selectReviewList(alcode);
		return Resultre;
	}

	public Album getAlInfo(String alcode) {
		System.out.println("SERVICE - 티켓정보 출력");
		return adao.getNwInfo(alcode);
	}

	public int albumRegistReview(String restate, String recontent, String rewriter) {
		System.out.println("service - registReview()");
		String maxRvCode = adao.selectMaxReCode();
		String recode = tsvc.genCode(maxRvCode);
		int registReview = adao.albumRegistReview(recode, restate, recontent, rewriter);
		System.out.println(registReview);
		return registReview;
	}

	public int deleteReview(String recode) {
		System.out.println("SERVEICE - deleteReview 호출");
		return adao.deleteReview(recode);
	}

	public HashMap<String, String> selectMainAlbum(String themaStr) {
		HashMap<String, String> result = adao.selectMainAlbum();
		return result;
	}

	public int addYes24Pop() throws IOException {
		System.out.println("Service - addYes24Pop()호출");

		ArrayList<Album> alList = new ArrayList<Album>();

		for (int pageNumger = 1; pageNumger <= 3; pageNumger++) {
			// YES24 팝송 앨범 정보 수정
			// 1. Jsoup 사용해서 접속할 첫 페이지
			String yes24ChartUrl = "https://www.yes24.com/24/Category/More/003001002?ElemNo=95&ElemSeq=1&PageNumber=" + pageNumger;
			Document yes24ChartDoc = Jsoup.connect(yes24ChartUrl).get();
			Elements urlItems = yes24ChartDoc.select("div.cCont_listArea > ul > li > div > p > span > span > a");

			// 2. Jsoup 사용해서 접속할 두번째 페이지
			for (Element urlItem : urlItems) {
				String detailUrl = "https://www.yes24.com" + urlItem.attr("href");
				Document detailDoc = Jsoup.connect(detailUrl).get();

				Album album = new Album();

				String alimg = detailDoc.select("#yDetailTopWrap > div.topColLft > div > span > em > img").attr("src");
				System.out.println("앨범이미지: " + alimg);
				album.setAlimg(alimg);

				String altitle = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoTop > div > h2").text();
				System.out.println("앨범제목: " + altitle);
				album.setAltitle(altitle);

				String alprice = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoBot > div.gd_infoTbArea > div.gd_infoTb > table > tbody > tr:nth-child(1) > td > span > em.yes_m").text().replace(",", "").replace("원", "");
				int alpriceAsInt = Integer.parseInt(alprice);
				System.out.println("판매가: " + alpriceAsInt);
				album.setAlprice(alpriceAsInt);

				String alsaleprice = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoBot > div.gd_infoTbArea > div.gd_infoTb > table > tbody > tr.accentRow > td > span > em.yes_m").text().replace(",", "").replace("원", "");
				int alsalepriceAsInt = Integer.parseInt(alsaleprice);
				System.out.println("할인가: " + alsalepriceAsInt);
				album.setAlsaleprice(alsalepriceAsInt);

				String alartist = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoTop > span.gd_pubArea > span.gd_auth > a").text();
				System.out.println("아티스트: " + alartist);
				album.setAlartist(alartist);

				String aldate = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoTop > span.gd_pubArea > span.gd_date").text().replace(" ", "-").replace("년", "").replace("월", "").replace("일", "");
				System.out.println("발매일: " + aldate);
				album.setAldate(aldate);

				String alinfo = detailDoc.select("#infoset_introduce > div.infoSetCont_wrap  div.infoWrap_txt:first-child > div.infoWrap_txtInner > textarea").text().replace("<b>", "").replace("</b>", "").replace("<br>", "").replace("<br/>", "").replace("<B>", "").replace("<STRONG>", "").replace("</STRONG>", "").replace("<FONT color=blue size=4>", "").replace("</FONT>", "").replace("<font color=red>", "").replace("</font>", "");
				if (alinfo.isEmpty()) {
					alinfo = "앨범정보가 없습니다.";
				}
				System.out.println("앨범정보: " + alinfo);
				album.setAlinfo(alinfo);

				String alscore = detailDoc.select("#spanGdRating > a > em").text();
				if (alscore.isEmpty()) {
					alscore = "0.0";
				}
				// .을 기준으로 문자열 분리
				String[] scoreParts = alscore.split("\\.");
				// 첫 번째 요소를 정수로 변환
				int scoreAsInt = Integer.parseInt(scoreParts[0]);
				System.out.println("앨범별점: " + scoreAsInt);
				album.setAlscore(scoreAsInt);

				String allist = detailDoc.select("div.gd_discList").text();
				System.out.println("수록곡: " + allist);
				album.setAllist(allist);

//		String algenre = detailDoc.select("").text();
//		System.out.println("장르: " + algenre);
//		이건 팝송, 가요로 따로 나눌거임 => 지금은 팝송으로 ㄱㄱ

//		String allist = detailDoc.select("").text();
//		System.out.println("수록곡: " + allist);
//		Yes24니까 다 Y로 들어갈거임

				System.out.println();
				alList.add(album);

				System.out.println("현재 alList 크기: " + alList.size());
			}
		}

		// DB
		// ALCODE를 만들기 위해서 ALCODE 최대값 조회
		String maxAlCode = adao.selectMaxAlCode(); // A0000
		System.out.println("maxAlCode: " + maxAlCode);

		// ALCODE 생성 후 INSERT
		int insertCount = 0;
		for (Album al : alList) {
			// 1. 앨범코드 생성
			String alcode = genCode(maxAlCode); // 0001
			maxAlCode = alcode;
			System.out.println("alcode: " + alcode);
			al.setAlcode(alcode);

			try {
				int insertResult = adao.insertAlbumsPop(al);
				insertCount += insertResult;
				maxAlCode = alcode; // maxAlCode 업데이트
				System.out.println("insertCount1= " + insertCount);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("insertCount2= " + insertCount);
				continue;
			}
		}
		return insertCount;
	}

	public int addYes24Kpop() throws IOException {
		System.out.println("Service - addYes24Pop()호출");

		ArrayList<Album> alList = new ArrayList<Album>();

		for (int pageNumger = 1; pageNumger <= 3; pageNumger++) {
			// YES24 팝송 앨범 정보 수정
			// 1. Jsoup 사용해서 접속할 첫 페이지
			String yes24ChartUrl = "https://www.yes24.com/24/Category/More/003001001?ElemNo=92&ElemSeq=1&PageNumber=" + pageNumger;
			Document yes24ChartDoc = Jsoup.connect(yes24ChartUrl).get();
			Elements urlItems = yes24ChartDoc.select("div.cCont_listArea > ul > li > div > p > span > span > a");

			// 2. Jsoup 사용해서 접속할 두번째 페이지
			for (Element urlItem : urlItems) {
				String detailUrl = "https://www.yes24.com" + urlItem.attr("href");
				Document detailDoc = Jsoup.connect(detailUrl).get();

				Album album = new Album();

				String alimg = detailDoc.select("#yDetailTopWrap > div.topColLft > div > span > em > img").attr("src");
				if (alimg.isEmpty()) {
					continue;
				}
				System.out.println("앨범이미지: " + alimg);
				album.setAlimg(alimg);

				String altitle = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoTop > div > h2").text();
				System.out.println("앨범제목: " + altitle);
				album.setAltitle(altitle);

				String alprice = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoBot > div.gd_infoTbArea > div.gd_infoTb > table > tbody > tr:nth-child(1) > td > span > em.yes_m").text().replace(",", "").replace("원", "");
				int alpriceAsInt = Integer.parseInt(alprice);
				System.out.println("판매가: " + alpriceAsInt);
				album.setAlprice(alpriceAsInt);

				String alsaleprice = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoBot > div.gd_infoTbArea > div.gd_infoTb > table > tbody > tr.accentRow > td > span > em.yes_m").text().replace(",", "").replace("원", "");
				int alsalepriceAsInt = Integer.parseInt(alsaleprice);
				System.out.println("할인가: " + alsalepriceAsInt);
				album.setAlsaleprice(alsalepriceAsInt);

				String alartist = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoTop > span.gd_pubArea > span.gd_auth > a").text();
				System.out.println("아티스트: " + alartist);
				album.setAlartist(alartist);

				String aldate = detailDoc.select("#yDetailTopWrap > div.topColRgt > div.gd_infoTop > span.gd_pubArea > span.gd_date").text().replace(" ", "-").replace("년", "").replace("월", "").replace("일", "");
				System.out.println("발매일: " + aldate);
				album.setAldate(aldate);

				String alinfo = detailDoc.select("#infoset_introduce > div.infoSetCont_wrap  div.infoWrap_txt:first-child > div.infoWrap_txtInner > textarea").text().replace("<b>", "").replace("</b>", "").replace("<br>", "").replace("<br/>", "").replace("<B>", "").replace("<STRONG>", "").replace("</STRONG>", "").replace("<FONT color=blue size=4>", "").replace("</FONT>", "").replace("<font color=red>", "").replace("</font>", "");
				if (alinfo.isEmpty()) {
					alinfo = "앨범정보가 없습니다.";
				}
				System.out.println("앨범정보: " + alinfo);
				album.setAlinfo(alinfo);

				String alscore = detailDoc.select("#spanGdRating > a > em").text();
				if (alscore.isEmpty()) {
					alscore = "0.0";
				}
				// .을 기준으로 문자열 분리
				String[] scoreParts = alscore.split("\\.");
				// 첫 번째 요소를 정수로 변환
				int scoreAsInt = Integer.parseInt(scoreParts[0]);
				System.out.println("앨범별점: " + scoreAsInt);
				album.setAlscore(scoreAsInt);

				String allist = detailDoc.select("div.gd_discList").text();
				if (allist.isEmpty()) {
					allist = "본 상품은 CD/LP가 아닙니다.(USB 또는 포토카드)";
				}
				System.out.println("수록곡: " + allist);
				album.setAllist(allist);

//				String algenre = detailDoc.select("").text();
//				System.out.println("장르: " + algenre);
//				이건 팝송, 가요로 따로 나눌거임 => 이건 가요

//				String allist = detailDoc.select("").text();
//				System.out.println("수록곡: " + allist);
//				Yes24니까 다 Y로 들어갈거임

				System.out.println();
				alList.add(album);

				System.out.println("현재 alList 크기: " + alList.size());
			}
		}

		// DB
		// ALCODE를 만들기 위해서 ALCODE 최대값 조회
		String maxAlCode = adao.selectMaxAlCode(); // A0000
		System.out.println("maxAlCode: " + maxAlCode);

		// ALCODE 생성 후 INSERT
		int insertCount = 0;
		for (Album al : alList) {
			// 1. 앨범코드 생성
			String alcode = genCode(maxAlCode); // 0001
			maxAlCode = alcode;
			System.out.println("alcode: " + alcode);
			al.setAlcode(alcode);

			try {
				int insertResult = adao.insertAlbumsKpop(al);
				insertCount += insertResult;
				maxAlCode = alcode; // maxAlCode 업데이트
				System.out.println("insertCount1= " + insertCount);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("insertCount2= " + insertCount);
				continue;
			}
		}
		return insertCount;
	}
	public ArrayList<String> getLikedAlbumList(String mid) {
		   System.out.println("SERVICE - 찜조회");
			return adao.getLikedAlbumList(mid);
		}

	public int unlikeAlbum(String like, String mid) {
		System.out.println("SERVICE - 찜취소");
		return adao.unlikeAlbum(like,mid);
	}


}
