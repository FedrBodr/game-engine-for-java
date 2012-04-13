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
	
	public Map(){
		// Do nothing
	}
	
	public Map(char[][] mapdata, int MAP_WIDTH, int MAP_HEIGHT, int TILE_SIZE, ArrayList<GObject> objects){
		this.mapdata = mapdata;
		this.MAP_WIDTH = MAP_WIDTH;
		this.MAP_HEIGHT = MAP_HEIGHT;
		this.TILE_SIZE = TILE_SIZE;
		this.objects = objects;
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
			}
		}
		map = new Map(mapData, mapWidth, mapHeight, tileSize, objs);
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
			} catch (NullPointerException e){
				
			}
		}
		return obj;
	}
	
	public boolean isCollisionFree(float x, float y, GObject object){
		boolean bool = true;
		Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), object.getWidth(), object.getHeight());
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			try {
				if (bounds.intersects(obj.getBounds())){
					if (obj.isSolid()){
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
		renderMap(g, 0, 0);
	}
	
	public void renderMap(Graphics2D g, int x, int y){
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			if (obj!=null){
				g.drawImage(obj.getImage(), Math.round(obj.getX()) + x, Math.round(obj.getY()) + y, null);
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
