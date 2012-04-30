package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.gej.core.Global;
import com.gej.object.GObject;

public class MapView {
		
	public static int OffSetX = 0;
	public static int OffSetY = 0;

	public static void follow(GObject obj){
		OffSetX = Math.round(Global.WIDTH/2 - obj.getX() - Map.TILE_SIZE);
		OffSetX = Math.min(OffSetX, 0);
		OffSetX = Math.max(OffSetX, Global.WIDTH - Map.getWidth());
		///////////////////////////////////////////////////////////////////
		OffSetY = Math.round(Global.HEIGHT/2 - obj.getY() - Map.TILE_SIZE);
		OffSetY = Math.min(OffSetY, 0);
		OffSetY = Math.max(OffSetY, Global.HEIGHT - Map.getHeight());
	}
	
	public static Rectangle getVisibleRect(){
		return new Rectangle(0, 0, Global.WIDTH, Global.HEIGHT);
	}
	
	public static void render(Graphics2D g){
		try {
			Map.renderMap(g, OffSetX, OffSetY, getVisibleRect());
		} catch (NullPointerException e){}
	}
	
	public static void renderObject(Graphics2D g, GObject obj){
		try {
			g.drawImage(obj.getImage(), Math.round(obj.getX())+OffSetX, Math.round(obj.getY())+OffSetY, null);
		} catch (NullPointerException e){}
	}
	
	public static boolean isVisible(GObject obj){
		boolean bool = false;
		Rectangle objRect = new Rectangle(Math.round(obj.getX())+OffSetX, Math.round(obj.getY())+OffSetY, obj.getWidth(), obj.getHeight());
		bool = getVisibleRect().intersects(objRect);
		return bool;
	}
	
}
