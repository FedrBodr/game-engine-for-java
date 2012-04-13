package com.gej.map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.gej.core.Global;
import com.gej.object.GObject;

public class MapView {
	
	Map map = null;
	GObject follower = null;
	
	public int OffSetX = 0;
	public int OffSetY = 0;

	public MapView(Map map){
		this.map = map;
	}
	
	public void follow(GObject obj){
		follower = obj;
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
			ArrayList<GObject> objects = map.getObjects();
			for (int i=0; i<objects.size(); i++){
				GObject object = objects.get(i);
				if (object!=follower && object!=null){
					g.drawImage(object.getImage(), Math.round(object.getX()) + OffSetX, Math.round(object.getY()) + OffSetY, null);
				}
				g.drawImage(follower.getImage(), Math.round(follower.getX()) + OffSetX, Math.round(follower.getY()) + OffSetY, null);
			}
		} catch (NullPointerException e){}
	}
	
}
