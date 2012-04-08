package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.gej.core.Game;
import com.gej.input.GInput;
import com.gej.object.GAction;
import com.gej.object.GObject;

public class MazeTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5952719987943361653L;
	
	ArrayList<GObject> walls = null;
	GObject player = null;
	
	Image background = null;
	
	GInput input = null;
	GAction left = null;
	GAction right = null;
	GAction up = null;
	GAction down = null;
	
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
		input = new GInput(this);
		left = new GAction("LEFT");
		input.mapToKey(left, KeyEvent.VK_LEFT);
		right = new GAction("RIGHT");
		input.mapToKey(right, KeyEvent.VK_RIGHT);
		up = new GAction("UP");
		input.mapToKey(up, KeyEvent.VK_UP);
		down = new GAction("DOWN");
		input.mapToKey(down, KeyEvent.VK_DOWN);
		setFPS(150);
		window.setTitle("MazeTest");
	}
	
	@Override
	public void update(long elapsedTime){
		boolean canMoveUp = true;
		boolean canMoveDown = true;
		boolean canMoveLeft = true;
		boolean canMoveRight = true;
		for (int i=0; i<walls.size(); i++){
			GObject wall = walls.get(i);
			if (player.isTopCollision(wall)){
				canMoveUp = false;
			}
			if (player.isBottomCollision(wall)){
				canMoveDown = false;
			}
			if (player.isLeftCollision(wall)){
				canMoveLeft = false;
			}
			if (player.isRightCollision(wall)){
				canMoveRight = false;
			}
		}
		if (up.isPressed() && canMoveUp){
			player.setY((float)(player.getY() - 0.1 * elapsedTime));
		}
		if (down.isPressed() && canMoveDown){
			player.setY((float)(player.getY() + 0.1 * elapsedTime));
		}
		if (left.isPressed() && canMoveLeft){
			player.setX((float)(player.getX() - 0.1 * elapsedTime));
		}
		if (right.isPressed() && canMoveRight){
			player.setX((float)(player.getX() + 0.1 * elapsedTime));
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
		new MazeTest();
	}
	
}
