package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.Voix.Dto.Album;
import com.Voix.Dto.Cart;

public interface AlbumDao {

	ArrayList<HashMap<String, String>> selectAlbum_map();

	ArrayList<Album> selectAlbumInfo_map(String altitle);

	ArrayList<Cart> insertCart_map(String caalcode, String loginId);

	String selectMaxCaCode();

	int insertCart(Cart crt);

	ArrayList<HashMap<String, String>> selectCartList(String loginId);

}