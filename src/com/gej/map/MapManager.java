package com.gej.map;

import java.util.ArrayList;

/**
 * A manager to control the order of maps to be loaded.
 * Has the flexibility to add new maps and to load a null
 * map. Can also be used to go to previous map or next map.
 * 
 * @author Sri Harsha Chilakapati
 */
public class MapManager {
	
	// The collection of maps
	private static ArrayList<MapInfo> maps        = new ArrayList<MapInfo>();
	/** The id of the current map */
	public static int                currentMap  = 0;
	
	/**
	 * Adds a new map next to the current map.
	 * @param map The MapInfo object containing the information about the map
	 */
	public static void addMap(MapInfo map){
		maps.add(map);
	}
	
	/**
	 * Goes to the first map.
	 */
	public static void startFirstMap(){
		currentMap = 0;
		reloadMap();
	}
	
	/**
	 * Returns true if there is another map in order
	 * @return Whether a map is present to be loaded next
	 */
	public static boolean hasNextMap(){
		if (maps.size()>currentMap+1){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if a map exists before this map
	 * @return Whether a map is present to be loaded before.
	 */
	public static boolean hasPreviousMap(){
		if (currentMap>0){
			return true;
		}
		return false;
	}
	
	/**
	 * Loads the next map in the order.
	 */
	public static void loadNextMap(){
		if (hasNextMap()){
			currentMap++;
			Map.loadMap(maps.get(currentMap));
		}
	}
	
	/**
	 * Loads the previous map in the order.
	 */
	public static void loadPreviousMap(){
		if (hasPreviousMap()){
			currentMap--;
			Map.loadMap(maps.get(currentMap));
		}
	}
	
	/**
	 * Reloads the current map
	 */
	public static void reloadMap(){
		Map.loadMap(maps.get(currentMap));
	}
	
	/**
	 * Loads a null map. Equivalent to calling Map.clearObjects()
	 */
	public static void loadNullMap(){
		Map.clearObjects();
	}
	
	/**
	 * Loads a map with a specified index.
	 * @param mapNum The number of map in order
	 */
	public static void loadMap(int mapNum){
		if (maps.size()<mapNum){
			currentMap = mapNum;
			Map.loadMap(maps.get(currentMap));
		}
	}

}
