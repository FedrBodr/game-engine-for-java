package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.gej.core.Global;
import com.gej.object.GObject;
import com.gej.object.Tile;
import com.gej.util.GUtil;

/**
 * This class represents a map (tile-based of course!) which is
 * currently playing the game. You could use the MapManager class
 * to control the order of maps. Note that this class is completely
 * static. A map requires MapInfo objects if you does't want to load
 * from file ( so nobody can edit your levels )
 * 
 * @author Sri Harsha Chilakapati
 */
public class Map {

	// The map data (used for debugging)
	private static char[][] mapdata = null;
	
	/** The tile-size of this map */
	public static int TILE_SIZE = 0;
	// Map's width and height (in tiles)
	private static int MAP_WIDTH = 0;
	private static int MAP_HEIGHT = 0;
	
	// Collection of all the game objects and tiles
	private static ArrayList<GObject> objects = null;
	private static ArrayList<Tile>    tiles   = null;
	
	/** 
	 * Returns a tile number containing the following pixel
	 * @param px The pixel's position
	 * @return The tile-position
	 */
	public static float toTiles(float px){
		return TILE_SIZE / px;
	}
	
	/**
	 * Returns the first pixel of the tile
	 * @param t The tile number
	 * @return The pixel position
	 */
	public static float toPixels(float t){
		return TILE_SIZE * t;
	}
	
	/**
	 * Returns the bounds of tile at a specified position
	 * @param x The x-position of the tile ( in tiles )
	 * @param y The y-position of the tile ( in tiles )
	 * @return A rectangle with the bounds
	 */
	public static Rectangle getTileBounds(int x, int y){
		return new Rectangle(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	/**
	 * Returns the character in the map file at the specified
	 * tile position of the object. ( Note that it is only used
	 * for debugging ).
	 * @param x The x-position of the tile
	 * @param y The y-position of the tile
	 * @return The character represented by the tile
	 */
	public static char getTileAt(int x, int y){
		return mapdata[x][y];
	}
	
	/**
	 * Loads the map from a MapInfo object.
	 * @param info The object containing information on the map
	 */
	public static void loadMap(MapInfo info){
		String[] lines = info.MapData.split("\n");
		TILE_SIZE = info.tileSize;
		loadMap(lines, info.loader);
	}
	
	/**
	 * Loads a map from an array of lines by using the MapLoader
	 * @param lines The array of string containing the lines
	 * @param loader The MapLoader to be used
	 */
	public static void loadMap(String[] lines, MapLoader loader){
		// Clear all the objects used before
		if (objects!=null){
			objects.clear();
		} else {
			objects = new ArrayList<GObject>();
		}
		if (tiles!=null){
			tiles.clear();
		} else {
			tiles = new ArrayList<Tile>();
		}
		// Reset the map view
		MapView.OffSetX = 0;
		MapView.OffSetY = 0;
		// Reset the map's height and width
		MAP_WIDTH = 0;
		MAP_HEIGHT = 0;
		// Try parsing the file
		try {
			for (int i=0; i<lines.length; i++){
				MAP_HEIGHT++;
				MAP_WIDTH = Math.max(MAP_WIDTH, lines[i].length());
			}
			mapdata = new char[MAP_WIDTH][MAP_HEIGHT];
			// Iterate and add all the objects and tiles
			for (int i=0; i<lines.length; i++){
				for (int j=0; j<lines[i].length(); j++){
					mapdata[j][i] = lines[i].charAt(j);
					objects.add(loader.getObject(mapdata[j][i], j*TILE_SIZE, i*TILE_SIZE));
					tiles.add(loader.getTile(mapdata[j][i], j*TILE_SIZE, i*TILE_SIZE));
				}
			}
		} catch (Exception e){}
	}
	
	/**
	 * Loads a map from a file in the jar file.
	 * @param tileSize The tile-size of the map
	 * @param mapname The file-name of the map
	 * @param loader The MapLoader
	 */
	public static void loadMap(int tileSize, String mapname, MapLoader loader){
		TILE_SIZE = tileSize;
		String[] lines = GUtil.loadLinesFromFile(mapname);
		loadMap(lines, loader);
	}
	
	/**
	 * Clears all the objects from the map. Destroy's them.
	 */
	public static void clearObjects(){
		objects.clear();
		tiles.clear();
	}

	/**
	 * Get the colliding object at a specific position and width and height
	 * @param x The x-position
	 * @param y The y-position
	 * @param width The width
	 * @param height The height
	 * @return The colliding GObject. Returns null if nothing found
	 */
	public static GObject getCollidingObject(float x, float y, int width, int height){
		GObject obj = null;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), width, height);
		for (int i=0; i<objects.size(); i++){
			try {
				GObject object = objects.get(i);
				if (bounds.intersects(object.getBounds())){
					obj = object;
				}
			} catch (NullPointerException e){}
		}
		return obj;
	}
	
	/**
	 * Returns a Tile object which is colliding at a specific position, width and height
	 * @param x The x-position
	 * @param y The y-position
	 * @param width The width
	 * @param height The height
	 * @return A Tile object which is colliding. Null if nothing is found.
	 */
	public static Tile getCollidingTile(float x, float y, int width, int height){
		Tile tile = null;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), width, height);
		try {
			for (int i=0; i<tiles.size(); i++){
				Tile t = tiles.get(i);
				if (bounds.intersects(t.getBounds())){
					tile = t;
				}
			}
		} catch (NullPointerException e){}
		return tile;
	}
	
	/**
	 * Returns the point position of the tile which is colliding
	 * at a position, width and height.
	 * @param x The x-position
	 * @param y The y-position
	 * @param width The width
	 * @param height The height
	 * @return The Point object of the colliding tile. Null if none collides.
	 */
	public static Point getTileCollidingPoint(float x, float y, int width, int height){
		Point p = null;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), width, height);
		try {
			for (int i=0; i<tiles.size(); i++){
				Tile t = tiles.get(i);
				if (bounds.intersects(t.getBounds())){
					p = new Point(t.getX(), t.getY());
				}
			}
		} catch (NullPointerException e){}
		return p;
	}
	
	/**
	 * Checks if a specific position is collision free in the map.
	 * @param x The x-position of the object
	 * @param y The y-position of the object
	 * @param solid Whether to check only for solid object
	 * @param object The object ( uses image for pixel-perfect collision detection )
	 * @return True if no-collision and false if it collides.
	 */
	public static boolean isObjectCollisionFree(float x, float y, boolean solid, GObject object){
		boolean bool = true;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), object.getWidth(), object.getHeight());
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			if (object!=obj && (obj.isSolid()==solid)){
				try {
					if (bounds.intersects(obj.getBounds())){
						if (obj.isAlive()){
							bool = false;
						}
						if (bool && Global.USE_PIXELPERFECT_COLLISION){
							bool = GUtil.isPixelPerfectCollision(x, y, object.getAnimation().getBufferedImage(), obj.getX(), obj.getY(), obj.getAnimation().getBufferedImage());
							if (bool){
								bool = false;
							}
						}
					}
				} catch (NullPointerException e){}
			}
		}
		return bool;
	}
	
	/**
	 * Checks if a specific position is collision free in the map.
	 * @param x The x-position of the object
	 * @param y The y-position of the object
	 * @param object The object ( uses image for pixel-perfect collision detection )
	 * @return True if no-collision and false if it collides.
	 */
	public static boolean isObjectCollisionFree(float x, float y, GObject object){
		boolean bool = true;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), object.getWidth(), object.getHeight());
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			if (object!=obj){
				try {
					if (bounds.intersects(obj.getBounds())){
						if (obj.isAlive()){
							bool = false;
						}
						if (bool && Global.USE_PIXELPERFECT_COLLISION){
							bool = GUtil.isPixelPerfectCollision(x, y, object.getAnimation().getBufferedImage(), obj.getX(), obj.getY(), obj.getAnimation().getBufferedImage());
							if (bool){
								bool = false;
							}
						}
					}
				} catch (NullPointerException e){}
			}
		}
		return bool;
	}
	
	/**
	 * Checks if a specific position is collision free in the map.
	 * @param x The x-position of the object
	 * @param y The y-position of the object
	 * @param object The object ( uses image for pixel-perfect collision detection )
	 * @return True if no-collision and false if it collides.
	 */
	public static boolean isTileCollisionFree(float x, float y, GObject object){
		boolean bool = true;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), object.getWidth(), object.getHeight());
		for (int i=0; i<tiles.size(); i++){
			Tile tile = tiles.get(i);
			try {
				if (bounds.intersects(tile.getBounds())){
					bool = false;
				}
			} catch (NullPointerException e){}
		}
		return bool;
	}
	
	/**
	 * Returns a list of objects in the current map.
	 * @return The ArrayList containing all the objects
	 */
	public static ArrayList<GObject> getObjects(){
		return objects;
	}
	
	/**
	 * Prints the map to the console. Used to debug
	 * while loading maps
	 */
	public static void printMap(){
		for (int y=0; y<MAP_HEIGHT; y++){
			for (int x=0; x<MAP_WIDTH; x++){
				System.out.print(mapdata[x][y]+" ");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Renders the map on to the graphics context
	 * @param g The graphics context
	 */
	public static void renderMap(Graphics2D g){
		renderMap(g, 0, 0, new Rectangle(0, 0, Global.WIDTH, Global.HEIGHT));
	}
	
	/**
	 * Renders the map at a specific position. visibleRect is the rectangle
	 * passed so that only the visible objects are drawn.
	 * @param g The graphics context
	 * @param x The x-position
	 * @param y The y-position
	 * @param visibleRect The Rectangle object of the visible area.
	 */
	public static void renderMap(Graphics2D g, int x, int y, Rectangle visibleRect){
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			if (obj!=null && obj.isAlive()){
				int obj_x = Math.round(obj.getX()) + x;
				int obj_y = Math.round(obj.getY()) + y;
				g.drawImage(obj.getImage(), obj_x, obj_y, null);
			} else {
				removeObject(obj);
			}
		}
		for (int i=0; i<tiles.size(); i++){
			Tile tile = tiles.get(i);
			if (tile!=null){
				int tileX = tile.getX() + x;
				int tileY = tile.getY() + y;
				if (visibleRect.intersects(new Rectangle(tileX, tileY, tile.getImage().getWidth(null), tile.getImage().getHeight(null)))){
					g.drawImage(tile.getImage(), tileX, tileY, null);
				}
			}
		}
	}
	
	/**
	 * Updates all the objects contained in the map.
	 * @param elapsedTime The time elapsed in the current frame.
	 */
	public static void updateObjects(long elapsedTime){
		for (int i=0; i<objects.size(); i++){
			try {
				GObject obj = objects.get(i);
				if (obj.isAlive()){
					obj.superUpdate(elapsedTime);
					obj.moveHorizontally(elapsedTime);
					checkCollisions(obj, true, false);
					obj.moveVertically(elapsedTime);
					checkCollisions(obj, false, true);
					checkCollisions(obj, false, false);
				} else {
					objects.remove(i);
				}
			} catch (Exception e){
				objects.remove(i);
			}
		}
	}
	
	private static void checkCollisions(GObject obj, boolean horizontal, boolean vertical){
		if (MapView.isVisible(obj)){
			for (int i=0; i<objects.size(); i++){
				try {
					if (objects.get(i).isCollidingWith(obj) && objects.get(i)!=obj && objects.get(i).isAlive()){
						if (horizontal){
							obj.HorizontalCollision(objects.get(i));
						}
						if (vertical){
							obj.VerticalCollision(objects.get(i));
						}
						if (!horizontal && !vertical){
							obj.collision(objects.get(i));
						}
					}
				} catch (Exception e){}
			}
		}
	}
	
	/**
	 * Returns the width of this map.
	 * @return The width of this map ( in pixels )
	 */
	public static int getWidth(){
		return MAP_WIDTH * TILE_SIZE;
	}
	
	/**
	 * Returns the height of this map
	 * @return The height of this map ( in pixels )
	 */
	public static int getHeight(){
		return MAP_HEIGHT * TILE_SIZE;
	}
	
	/**
	 * Removes an object from the map
	 * @param obj The object to be removed
	 */
	public static void removeObject(GObject obj){
		objects.remove(obj);
	}
	
	/**
	 * Adds a new object to the map
	 * @param obj The new object
	 */
	public static void addObject(GObject obj){
		objects.add(obj);
	}
	
}
