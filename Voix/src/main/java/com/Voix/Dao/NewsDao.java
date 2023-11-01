package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.Voix.Dto.News;

public interface NewsDao {

	ArrayList<HashMap<String, String>> selectNews_map();

	News getNwInfo(String nwcode);

	int newsRegistReview(@Param("recode")String recode, @Param("restate")String restate, @Param("recontent")String recontent, @Param("rewriter")String rewriter);

	String selectMaxReCode();

	ArrayList<HashMap<String, String>> selectReviewList(String nwcode);

	int deleteNewsReview(String recode);

	int likeNews(@Param("like")String like,@Param("mid")String mid);

	ArrayList<HashMap<String, String>> selectSearch_Title(String searchKeyword);

	ArrayList<HashMap<String, String>> selectSearch_Content(String searchKeyword);

	ArrayList<HashMap<String, String>> selectNewsHitList();

	String selectMaxNwCode();

	int insertNews(News news);

	HashMap<String, String> selectMainNews(String t);
	
	int UpdateNewsBigHit(String nwcode);

	//찜조회
	ArrayList<String> getLikedNewsList(@Param("mid") String mid);
	//찜삭제
	int unlikeNews(@Param("like")String like,@Param("mid")String mid);

	//public List<News> getListWithPaging(paging paging);
	public int countBoard();

	//페이징 처리 코드조회
	List<Map<String, Object>> selectBoardList(@Param("pageStart") String startNWCODE, @Param("perPageNum")String endNWCODE);
	
}
