package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;

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

}