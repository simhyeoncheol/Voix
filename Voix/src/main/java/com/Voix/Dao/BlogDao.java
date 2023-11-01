package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.Voix.Dto.Blog;

public interface BlogDao {

	HashMap<String, String> selectMainBlog();

	ArrayList<HashMap<String, String>> selectBlog_map();

	Blog getBlogInfo(String bgcode);

	ArrayList<HashMap<String, String>> selectReviewList(String bgcode);

	String selectMaxReCode();

	int BlogRegistReview(@Param("recode")String recode, @Param("restate")String restate, @Param("recontent")String recontent, @Param("rewriter")String rewriter);

	int deleteBlogReview(String recode);

	int likeBlog(@Param("like")String like,@Param("mid")String mid);

	ArrayList<HashMap<String, String>> selectBlogHitList();

	int UpdateBlogBigHit(String bgcode);
	
	//찜조회
	ArrayList<String> getLikedBlogList(@Param("mid") String mid);
	//찜삭제
	int unlikeBlog(@Param("like")String like,@Param("mid")String mid);

	int countBoard();

	//페이징 처리 코드조회
	List<Map<String, Object>> selectBoardList(@Param("pageStart") String startBGCODE, @Param("perPageNum")String endBGCODE);

	ArrayList<HashMap<String, String>> selectSearch_Title(String searchKeyword);

	ArrayList<HashMap<String, String>> selectSearch_Content(String searchKeyword);
	
	
}
