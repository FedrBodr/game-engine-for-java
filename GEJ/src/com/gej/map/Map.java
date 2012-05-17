package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.gej.core.Global;
import com.gej.object.GObject;
import com.gej.util.GUtil;

public class Map {

	private static char[][] mapdata = null;
	
	public static int TILE_SIZE = 0;
	private static int MAP_WIDTH = 0;
	private static int MAP_HEIGHT = 0;
	
	private static ArrayList<GObject> objects = null;
	private static ArrayList<Tile>    tiles   = null;
	
	public static float toTileX(float x){
		return TILE_SIZE / x;
	}
	
	public static float toTileY(float y){
		return TILE_SIZE / y;
	}
	
	public static Rectangle getTileBounds(int x, int y){
		return new Rectangle(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public static char getTileAt(int x, int y){
		return mapdata[x][y];
	}
	
	public static void loadMap(String mapname, MapLoader loader){
		BufferedReader reader = new BufferedReader(new InputStreamReader(Map.class.getClassLoader().getResourceAsStream(mapname)));
		TILE_SIZE = 32;
		MAP_HEIGHT = 0;
		MAP_WIDTH = 0;
		ArrayList<String> lines = new ArrayList<String>();
		Global.UPDATEABLES.clear();
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
		try {
			String line = reader.readLine();
			while (line!=null){
				if (line.startsWith("SIZE:")){
					TILE_SIZE = Integer.parseInt(line.split(":")[1]);
				} else {
					lines.add(line);
					MAP_HEIGHT++;
					MAP_WIDTH = Math.max(MAP_WIDTH, line.length());
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		mapdata = new char[MAP_WIDTH][MAP_HEIGHT];
		for (int i=0; i<lines.size(); i++){
			for (int j=0; j<lines.get(i).length(); j++){
				mapdata[j][i] = lines.get(i).charAt(j);
				objects.add(loader.getObject(mapdata[j][i], j*TILE_SIZE, i*TILE_SIZE));
				tiles.add(loader.getTile(mapdata[j][i], j*TILE_SIZE, i*TILE_SIZE));
			}
		}
	}

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
	
	public static ArrayList<GObject> getObjects(){
		return objects;
	}
	
	public static void printMap(){
		for (int y=0; y<MAP_HEIGHT; y++){
			for (int x=0; x<MAP_WIDTH; x++){
				System.out.print(mapdata[x][y]+" ");
			}
			System.out.print("\n");
		}
	}
	
	public static void renderMap(Graphics2D g){
		renderMap(g, 0, 0, new Rectangle(0, 0, Global.WIDTH, Global.HEIGHT));
	}
	
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
	
	public static int getWidth(){
		return MAP_WIDTH * TILE_SIZE;
	}
	
	public static int getHeight(){
		return MAP_HEIGHT * TILE_SIZE;
	}
	
	public static void removeObject(GObject obj){
		objects.remove(obj);
	}
	
	public static void addObject(GObject obj){
		objects.add(obj);
	}
	
}
