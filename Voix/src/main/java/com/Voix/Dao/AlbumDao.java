package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.Voix.Dto.Album;
import com.Voix.Dto.Cart;
import com.Voix.Dto.Order;

public interface AlbumDao {

	ArrayList<HashMap<String, String>> selectAlbum_map();

	ArrayList<Album> selectAlbumInfo_map(String altitle);

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

}