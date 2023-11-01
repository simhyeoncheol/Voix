package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.Voix.Dto.Album;
import com.Voix.Dto.Cart;
import com.Voix.Dto.Order;

public interface AlbumDao {

	ArrayList<HashMap<String, String>> selectAlbum_map();

	ArrayList<Album> selectAlbumInfo_map(String alcode);

	ArrayList<Cart> insertCart_map(String caalcode, String loginId);

	String selectMaxCaCode();

	int insertCart(Cart crt);

	ArrayList<HashMap<String, String>> selectCartList(String loginId);

	int DeleteCartList(String cacode);

	Album getAlbumInfo_alcode(String alcode);
	
	int insertOdInfo(Order odInfo);

	String getmaxOdcode();

	int deleteReserve(String odcode);
	
	ArrayList<HashMap<String, String>> selectSearch_Title(String searchKeyword);

	ArrayList<HashMap<String, String>> selectSearch_Content(String searchKeyword);

	ArrayList<HashMap<String, String>> selectReviewList(String alcode);

	Album getNwInfo(String alcode);

	String selectMaxReCode();

	int albumRegistReview(@Param("recode")String recode, @Param("restate")String restate, @Param("recontent")String recontent, @Param("rewriter")String rewriter);

	int deleteReview(String recode);

	HashMap<String, String> selectMainAlbum();

	String selectMaxAlCode();

	int insertAlbumsPop(Album al);

	int insertAlbumsKpop(Album al);

	int likeAlbum(@Param("like")String like,@Param("mid")String mid);

	//찜조회
	ArrayList<String> getLikedAlbumList(@Param("mid") String mid);
	//찜삭제
	int unlikeAlbum(@Param("like")String like,@Param("mid")String mid);
	

}
