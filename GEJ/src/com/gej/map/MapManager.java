package com.gej.map;

import java.util.ArrayList;

public class MapManager {
	
	private static ArrayList<MapInfo> maps       = new ArrayList<MapInfo>();
	private static int               currentMap  = 0;
	
	public static void addMap(MapInfo map){
		maps.add(map);
	}
	
	public static void startFirstMap(){
		currentMap = 0;
		reloadMap();
	}
	
	public static boolean hasNextMap(){
		if (maps.size()>currentMap){
			return true;
		}
		return false;
	}
	
	public static boolean hasPreviousMap(){
		if (currentMap>0){
			return true;
		}
		return false;
	}
	
	public static void loadNextMap(){
		if (hasNextMap()){
			currentMap++;
			Map.loadMap(maps.get(currentMap));
		}
	}
	
	public static void loadPreviousMap(){
		if (hasPreviousMap()){
			currentMap--;
			Map.loadMap(maps.get(currentMap));
		}
	}
	
	public static void reloadMap(){
		Map.loadMap(maps.get(currentMap));
	}
	
	public static void loadNullMap(){
		Map.clearObjects();
	}
	
	public static void loadMap(int mapNum){
		if (maps.size()<mapNum){
			currentMap = mapNum;
			Map.loadMap(maps.get(currentMap));
		}
	}

}
