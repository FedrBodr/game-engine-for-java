package com.gej.map;

import com.gej.util.GUtil;

public class MapInfo {
	
	public String    MapData = null;
	public int       tileSize = 32;
	
	public MapLoader loader = null;
	
	public static MapInfo LoadFromFile(String MapName, MapLoader loader, int tileSize){
		MapInfo info = new MapInfo();
		String[] data = GUtil.loadLinesFromFile(MapName);
		for (int i=0; i<data.length; i++){
			info.MapData += data[i] + "\n";
		}
		info.loader = loader;
		info.tileSize = tileSize;
		return info;
	}
	
	public void setMapLoader(MapLoader loader){
		this.loader = loader;
	}

}
