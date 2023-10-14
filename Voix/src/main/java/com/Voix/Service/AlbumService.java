package com.Voix.Service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voix.Dao.AlbumDao;
import com.Voix.Dto.Album;
import com.Voix.Dto.Cart;


@Service
public class AlbumService {
	@Autowired
	private AlbumDao adao;

	public ArrayList<HashMap<String, String>> getAlbumtList_map() {
		
		return adao.selectAlbum_map();
	}

	public ArrayList<Album> getAlbumInfoList(String altitle) {
		
		return adao.selectAlbumInfo_map(altitle);
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
		// TODO Auto-generated method stub
		return adao.selectCartList(loginId);
	}


}