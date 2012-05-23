package com.gej.map;

import com.gej.object.GObject;

/**
 * 
 * 
 * @author Sri Harsha Chilakapati
 */
public interface MapLoader {

	public GObject getObject(char c, int x, int y);
	
	public Tile getTile(char c, int x, int y);
	
}
