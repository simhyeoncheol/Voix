package com.Voix.Dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.Voix.Dto.Chart;

public interface ChartDao {

	ArrayList<HashMap<String, String>> selectChart_map();

	ArrayList<Chart> selectChartInfo_map(String sgcode);

}