package com.gej.map;

import com.gej.object.GObject;

/**
 * This interface is used by the Map class to load
 * the objects dynamically on demand. They are created
 * by using the character used to represent them in
 * the map file or in the MapInfo object.
 * 
 * @author Sri Harsha Chilakapati
 */
public interface MapLoader {

	/**
	 * Called by the Map to load objects dynamically. The character
	 * which is encountered in the map is passed as an argument along
	 * with the position (pixel) which can be used to align the object
	 * to the map's grid.
	 * @param c The encountered character
	 * @param x The x-position
	 * @param y The y-position
	 * @return An object for the map created by the implemented
	 */
	public GObject getObject(char c, int x, int y);
	
	/**
	 * Called by the Map to load tiles dynamically. The character
	 * which is encountered in the map is passed as an argument along
	 * with the position (pixel) which can be used to align the tile
	 * to the map's grid.
	 * @param c The encountered character
	 * @param x The x-position
	 * @param y The y-position
	 * @return A tile for the map created by the implemented
	 */
	public Tile getTile(char c, int x, int y);
	
}
