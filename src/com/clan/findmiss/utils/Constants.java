package com.clan.findmiss.utils;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.Map;

public class Constants {

	//private Map<Integer,int[][]> mapPoint=new HashMap<Integer, int[][]>();
	@SuppressLint("UseSparseArrays")
	public static Map<Integer,int[][]> getMapData() {
		Map<Integer,int[][]> mapPoint=new HashMap<Integer, int[][]>();
		int info[][] = { { 210, 75}, { 288, 215},{ 220, 180}, { 215, 270},{ 235, 330}};
		mapPoint.put(1, info);
		info=new int[][]{ { 40, 32}, { 58, 190},{ 198, 268}};
		mapPoint.put(2, info);
		info=new int[][]{ { 17, 160}, { 165, 174},{ 173, 352}};
		mapPoint.put(3, info);
		info=new int[][]{ { 40, 32}, { 58, 190},{ 198, 268},{ 17, 160}, { 165, 174},{ 173, 352}};
		mapPoint.put(4, info);
		return mapPoint;
	}
}
