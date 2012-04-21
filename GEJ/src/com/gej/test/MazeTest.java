package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.object.GObject;

public class MazeTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5952719987943361653L;
	
	ArrayList<GObject> walls = null;
	GObject player = null;
	
	Image background = null;
	
	@Override
	public void initResources(){
		background = loadImage("resources/back.png");
		walls = new ArrayList<GObject>();
		int x=0; int y=0;
		String level = "WWWWWWWWWW\n"+
	                   "W       PW\n"+
			           "W WWWWWWWW\n"+
	                   "W        W\n"+
			           "WWWWWWWW W\n"+
	                   "W        W\n"+
			           "WWWWWWWWWW";
		for (int i=0; i<level.length(); i++){
			char ch = level.charAt(i);
			if (ch=='W'){
				GObject wall = new GObject(loadImage("resources/box2.png"));
				wall.setX(x);
				wall.setY(y);
				walls.add(wall);
				x += 64;
			} else if (ch==' '){
				x += 64;
			} else if (ch=='P'){
				player = new GObject(loadImage("resources/box.png"));
				player.setX(x);
				player.setY(y);
				x += 64;
			} else if (ch=='\n'){
				x = 0;
				y += 64;
			}
		}
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		for (int i=0; i<walls.size(); i++){
			GObject wall = walls.get(i);
			g.drawImage(wall.getImage(), Math.round(wall.getX()), Math.round(wall.getY()), null);
		}
		g.drawImage(player.getImage(), Math.round(player.getX()), Math.round(player.getY()), null);
	}
	
	public static void main(String[] args){
		GWindow.setup(new MazeTest());
	}
	
}
