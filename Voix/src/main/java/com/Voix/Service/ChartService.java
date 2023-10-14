package com.Voix.Service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Voix.Dao.ChartDao;
import com.Voix.Dto.Chart;

@Service
public class ChartService {

	@Autowired
	private ChartDao cdao;

	public ArrayList<HashMap<String, String>> getChartList_map() {
		
		return cdao.selectChart_map();
	}

	public ArrayList<Chart> getChartInfoList(String sgcode) {
		
		return cdao.selectChartInfo_map(sgcode);
	}
	
}