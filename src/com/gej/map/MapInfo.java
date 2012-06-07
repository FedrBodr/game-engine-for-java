package com.gej.map;

import com.gej.util.GUtil;

/**
 * This class is required to know all the information about the maps and to
 * switch the maps more easily from the MapManager class
 * 
 * @author Sri Harsha Chilakapati
 */
public class MapInfo {

	/** The data of the map. Use '\n' to indicate new rows */
	public String MapData = "";
	/** The default tile-size of this map */
	public int tileSize = 32;

	/** The MapLoader object used to load map from this class */
	public MapLoader loader = null;

	/**
	 * Loads a map from a file in the jar file.
	 * 
	 * @param MapName The name of the map file
	 * @param loader The MapLoader
	 * @param tileSize The tile-size
	 * @return The created MapInfo object.
	 */
	public static MapInfo LoadFromFile(String MapName, MapLoader loader,
	        int tileSize) {
		MapInfo info = new MapInfo();
		String[] data = GUtil.loadLinesFromFile(MapName);
		for (int i = 0; i < data.length; i++) {
			info.MapData += data[i] + "\n";
		}
		info.loader = loader;
		info.tileSize = tileSize;
		return info;
	}

	/**
	 * Set's the MapLoader used by this class
	 * 
	 * @param loader The new MapLoader
	 */
	public void setMapLoader(MapLoader loader) {
		this.loader = loader;
	}

}
