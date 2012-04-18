package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.gej.core.Global;
import com.gej.object.GObject;

public class Map {

	char[][] mapdata = null;
	
	int TILE_SIZE = 0;
	int MAP_WIDTH = 0;
	int MAP_HEIGHT = 0;
	
	ArrayList<GObject> objects = null;
	ArrayList<Tile>    tiles   = null;
	
	public Map(){
	}
	
	public Map(char[][] mapdata, int MAP_WIDTH, int MAP_HEIGHT, int TILE_SIZE, ArrayList<GObject> objects, ArrayList<Tile> tiles){
		this.mapdata = mapdata;
		this.MAP_WIDTH = MAP_WIDTH;
		this.MAP_HEIGHT = MAP_HEIGHT;
		this.TILE_SIZE = TILE_SIZE;
		this.objects = objects;
		this.tiles = tiles;
		Global.OBJECTS.clear();
		Global.OBJECTS.addAll(objects);
	}
	
	public float toTileX(float x){
		return TILE_SIZE / x;
	}
	
	public float toTileY(float y){
		return TILE_SIZE / y;
	}
	
	public Rectangle getTileBounds(int x, int y){
		return new Rectangle(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public char getTileAt(int x, int y){
		return mapdata[x][y];
	}
	
	public static Map loadMap(String mapname, MapLoader loader){
		Map map = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(Map.class.getClassLoader().getResourceAsStream(mapname)));
		int tileSize = 32;
		int mapHeight = 0;
		int mapWidth = 0;
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<GObject> objs = new ArrayList<GObject>();
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		try {
			String line = reader.readLine();
			while (line!=null){
				if (line.startsWith("SIZE:")){
					tileSize = Integer.parseInt(line.split(":")[1]);
				} else {
					lines.add(line);
					mapHeight++;
					mapWidth = Math.max(mapWidth, line.length());
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		char[][] mapData = new char[mapWidth][mapHeight];
		for (int i=0; i<lines.size(); i++){
			for (int j=0; j<lines.get(i).length(); j++){
				mapData[j][i] = lines.get(i).charAt(j);
				objs.add(loader.getObject(mapData[j][i], j*tileSize, i*tileSize));
				tiles.add(loader.getTile(mapData[j][i], j*tileSize, i*tileSize));
			}
		}
		map = new Map(mapData, mapWidth, mapHeight, tileSize, objs, tiles);
		return map;
	}

	public GObject getCollidingObject(float x, float y, int width, int height){
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
	
	public boolean isObjectCollisionFree(float x, float y, GObject object){
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
							bool = GObject.isPixelPerfectCollision(obj, Math.round(obj.getX()), Math.round(obj.getY()), object);
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
	
	public boolean isTileCollisionFree(float x, float y, GObject object){
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
	
	public ArrayList<GObject> getObjects(){
		return objects;
	}
	
	public void printMap(){
		for (int y=0; y<MAP_HEIGHT; y++){
			for (int x=0; x<MAP_WIDTH; x++){
				System.out.print(mapdata[x][y]+" ");
			}
			System.out.print("\n");
		}
	}
	
	public void renderMap(Graphics2D g){
		renderMap(g, 0, 0, new Rectangle(0, 0, Global.WIDTH, Global.HEIGHT));
	}
	
	public void renderMap(Graphics2D g, int x, int y, Rectangle visibleRect){
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			if (obj!=null){
				int obj_x = Math.round(obj.getX()) + x;
				int obj_y = Math.round(obj.getY()) + y;
				if (visibleRect.intersects(new Rectangle(obj_x, obj_y, obj.getWidth(), obj.getHeight()))){
					g.drawImage(obj.getImage(), obj_x, obj_y, null);
				}
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
	
	public int getWidth(){
		return MAP_WIDTH * TILE_SIZE;
	}
	
	public int getHeight(){
		return MAP_HEIGHT * TILE_SIZE;
	}
	
	public void removeObject(GObject obj){
		objects.remove(obj);
	}
	
	public void addObject(GObject obj){
		objects.add(obj);
	}
	
}
