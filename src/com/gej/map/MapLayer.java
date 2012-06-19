package com.gej.map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.gej.object.GObject;

/**
 * A layer of the map. Used to draw the objects in the order of depth.
 * 
 * @author Sri Harsha Chilakapati
 */
public class MapLayer {
	
    // The collection of objects
	ArrayList<GObject> objects = null;
	
	/**
	 * Constructs a new MapLayer object
	 */
	public MapLayer(){
		objects = new ArrayList<GObject>();
	}
	
	/**
	 * Adds an object to this layer
	 * @param obj The object to be added
	 */
	public void addObject(GObject obj){
		if (obj!=null){
			objects.add(obj);
		}
	}
	
	/**
	 * Renders this layer at the top-left corner of the screen
	 * @param g The graphics context
	 */
	public void render(Graphics2D g){
		render(g, 0, 0);
	}
	
	/**
	 * Renders this layer at a specific position of the screen
	 * @param g The graphics context
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void render(Graphics2D g, int x, int y){
		try {
			for (int i=0; i<objects.size(); i++){
			    GObject obj = objects.get(i);
		        if (!obj.isAlive()){
		            objects.remove(i);
		        } else {
		            if (MapView.isVisible(obj)){
                        int obj_x = Math.round(obj.getX()) + x;
                        int obj_y = Math.round(obj.getY()) + y;
                        g.drawImage(obj.getImage(), obj_x, obj_y, null);
                    }
		        }
			}
		} catch (Exception e){}
	}

}
