package com.Voix.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voix.Dao.ChartDao;
import com.Voix.Dto.Chart;
import com.Voix.Dto.Song;

@Service
public class ChartService {

	@Autowired
	private ChartDao cdao;

	@Autowired
	private TicketService tsvc;

	public ArrayList<HashMap<String, String>> getChartList_map() {
		
		return cdao.selectChart_map();
	}

	public ArrayList<Chart> getChartInfoList(String sgcode) {
		
		return cdao.selectChartInfo_map(sgcode);
	}
	public ArrayList<HashMap<String, String>> selectTitle(String searchKeyword) {
		ArrayList<HashMap<String, String>> searchList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> titleList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> contentList = new ArrayList<HashMap<String, String>>();
			try {
				titleList = cdao.selectSearch_Title(searchKeyword);				
			} catch (Exception e) {
			}
			System.out.println(titleList);
			try {
				contentList = cdao.selectSearch_Content(searchKeyword);				
			} catch (Exception e) {
			}
			searchList.addAll(titleList);
			searchList.addAll(contentList);
		return searchList;
	}

		public HashMap<String, String> selectMainChart(String themaStr) {
		
		return cdao.selectMainChart();
	}

	public int melonCrawling() throws IOException {
		System.out.println("service - melonCrawling 호출");
		String melonChartUrl ="https://www.melon.com/chart/index.htm";
		Document chartDoc = Jsoup.connect(melonChartUrl).get();
		//1~50위 : #lst50 | 50~100위 : #lst100
		Elements urlItems = chartDoc.select("#lst100 > td:nth-child(5) > div > a");
		
		ArrayList<Song> songList = new ArrayList<Song>();
		for(Element urlItem : urlItems) {
			
			String href = urlItem.attr("href");
			String songId = href.substring(href.indexOf('\'') + 1, href.lastIndexOf('\''));
			String detailUrl = "https://www.melon.com/song/detail.htm?songId=" + songId;
			
			Document detailDoc = Jsoup.connect(detailUrl).get();
			
			Song song = new Song();
			String sgtitle = detailDoc.select("#downloadfrm > div > div > div.entry > div.info > div.song_name").text();
			sgtitle = sgtitle.replace("곡명", "").trim();
			
			song.setSgtitle(sgtitle);
			
			String sgaltitle = detailDoc.select("#downloadfrm > div > div > div.entry > div.meta > dl > dd:nth-child(2) > a").text();
			
			song.setSgaltitle(sgaltitle);
			
			String sgartist = detailDoc.select("#downloadfrm > div > div > div.entry > div.info > div.artist > a > span:nth-child(1)").text();
			
			song.setSgartist(sgartist);
			
			String sgmvurl = detailDoc.select("#conts > div.section_movie > div.service_list_video.type03.d_video_list > ul > li > div.thumb > a").attr("href");
			Pattern pattern = Pattern.compile("'(\\d+)',\\s*'?(\\d+)'?\\s*\\)");
			Matcher matcher = pattern.matcher(sgmvurl);
			String menuId = null;
			String mvId = null;

			while (matcher.find()) {
			    menuId = matcher.group(1);
			    mvId = matcher.group(2); 
			}

			if (menuId != null && mvId != null) {
			   
			    song.setSgmvurl("https://www.melon.com/video/detail2.htm?mvId=" + mvId + "&menuId=" + menuId);
			}else {
				song.setSgmvurl("");
			}
			
			String sginfo1 = detailDoc.select("#downloadfrm > div > div > div.entry > div.meta > dl > dd:nth-child(4)").text();
			String sginfo2 = detailDoc.select("#downloadfrm > div > div > div.entry > div.meta > dl > dd:nth-child(6)").text();
			 String sginfo = "발매일:" + sginfo1 + " | " + "장르:" + sginfo2;
			
			song.setSginfo(sginfo);
			
			String sgimg = detailDoc.select("#d_song_org > a > img").attr("src");
			
			song.setSgimg(sgimg);
			
			String sglyric = detailDoc.select("#d_video_summary").text();
			
			 if (sglyric.length() > 2000) {
		            sglyric = sglyric.substring(0, 2000);
		        }

		        song.setSglyric(sglyric);

		        songList.add(song);
		}
		
		String maxSgcode = cdao.selectMaxSgCode();
		
		
		int insertCount = 0;
		for(Song song : songList) {
		
			String sgcode = genCode(maxSgcode);
			
			song.setSgcode(sgcode);;
			System.out.println(song);
			
				int insertResult = cdao.insertSong(song);
				insertCount += insertResult;
				maxSgcode = sgcode;
			
				
		}
		return insertCount;
	}
	
	public String genCode(String currentCode) {
		
		String strCode = currentCode.substring(0,2);
		int numCode = Integer.parseInt(currentCode.substring(2));
		
		
		String newCode = strCode + String.format("%03d", numCode+1);
		return newCode;
	}

	public int countBoardListTotal() {
		return cdao.countBoard();
	}

	public List<Map<String, Object>> selectBoardList(String startSGCODE, String endSGCODE) {
		return cdao.selectBoardList(startSGCODE, endSGCODE);
	}

	public int ChartRegistReview(String restate, String recontent, String rewriter) {
		System.out.println("service - registReview()");
		String maxRvCode = cdao.selectMaxReCode();
		String recode = tsvc.genCode(maxRvCode);
		int registReview = cdao.ChartRegistReview(recode, restate, recontent, rewriter);
		System.out.println(registReview);
		return registReview;
	}

	public ArrayList<HashMap<String, String>> selectReviewList(String sgcode) {
		System.out.println("selectReivewList  호출");
		ArrayList<HashMap<String, String>> Resultre = cdao.selectReviewList(sgcode);
		return Resultre;
	}

	public int deleteReview(String recode) {
		System.out.println("SERVEICE - deleteReview 호출");
		return cdao.deleteReview(recode);
	}
	public int likeSong(String like, String mid) {
		System.out.println("SERVICE- Song 찜");
		return cdao.likeSong(like,mid);
	}	
	public ArrayList<String> getLikedSongList(String mid) {
		System.out.println("SERVEICE - 찜 조회");
		return cdao.getLikedSongList(mid);
	}
	public ArrayList<HashMap<String, String>> getPlayList(String mid) {
		System.out.println("SERVEICE - 픓레이 리스트 조회");
		return cdao.getPlayList(mid);
	}
	public int unlikeSong(String like, String mid) {
		 System.out.println("SERVEICE - 찜 삭제");
		return cdao.unlikeSong(like, mid);
	}

	
}
