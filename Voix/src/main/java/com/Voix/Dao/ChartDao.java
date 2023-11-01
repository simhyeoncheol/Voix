package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.Voix.Dto.Chart;
import com.Voix.Dto.Song;

public interface ChartDao {

	ArrayList<HashMap<String, String>> selectChart_map();

	ArrayList<Chart> selectChartInfo_map(String sgcode);

	ArrayList<HashMap<String, String>> selectSearch_Title(String searchKeyword);

	ArrayList<HashMap<String, String>> selectSearch_Content(String searchKeyword);

	HashMap<String, String> selectMainChart();

	String selectMaxSgCode();

	int insertSong(Song song);

	int countBoard();
	
	List<Map<String, Object>> selectBoardList(@Param("pageStart") String startSGCODE, @Param("perPageNum")String endSGCODE);

	String selectMaxReCode();

	int ChartRegistReview(@Param("recode")String recode, @Param("restate")String restate, @Param("recontent")String recontent, @Param("rewriter")String rewriter);

	ArrayList<HashMap<String, String>> selectReviewList(String sgcode);

	int deleteReview(String recode);
	
	ArrayList<String> getLikedSongList(@Param("mid") String mid);
	
	ArrayList<HashMap<String, String>> getPlayList(@Param("mid") String mid);
	
	int unlikeSong(@Param("like")String like,@Param("mid")String mid);
	
	int likeSong(@Param("like")String like,@Param("mid")String mid);
}
