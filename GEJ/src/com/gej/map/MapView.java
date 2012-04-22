package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.gej.core.Global;
import com.gej.core.Updateable;
import com.gej.object.GObject;

public class MapView implements Updateable {
	
	Map map = null;
	GObject follower = null;
	
	public int OffSetX = 0;
	public int OffSetY = 0;

	public MapView(Map map){
		this.map = map;
		Global.UPDATEABLES.add(this);
	}
	
	public void follow(GObject obj){
		follower = obj;
	}
	
	public Rectangle getVisibleRect(){
		return new Rectangle(0, 0, Global.WIDTH, Global.HEIGHT);
	}
	
	public void update(long elapsedTime){
		if (follower != null){
				OffSetX = Math.round(Global.WIDTH/2 - follower.getX() - map.TILE_SIZE);
				OffSetX = Math.min(OffSetX, 0);
				OffSetX = Math.max(OffSetX, Global.WIDTH - map.getWidth());
				///////////////////////////////////////////////////////////////////
				OffSetY = Math.round(Global.HEIGHT/2 - follower.getY() - map.TILE_SIZE);
				OffSetY = Math.min(OffSetY, 0);
				OffSetY = Math.max(OffSetY, Global.HEIGHT - map.getHeight());
		}
	}
	
	public void render(Graphics2D g){
		try {
			map.renderMap(g, OffSetX, OffSetY, getVisibleRect());
			if (follower!=null){
				g.drawImage(follower.getImage(), Math.round(follower.getX()) + OffSetX, Math.round(follower.getY()) + OffSetY, null);
			}
		} catch (NullPointerException e){}
	}
	
	public boolean isVisible(GObject obj){
		boolean bool = false;
		Rectangle objRect = new Rectangle(Math.round(obj.getX())+OffSetX, Math.round(obj.getY())+OffSetY, obj.getWidth(), obj.getHeight());
		bool = getVisibleRect().intersects(objRect);
		return bool;
	}
	
}
