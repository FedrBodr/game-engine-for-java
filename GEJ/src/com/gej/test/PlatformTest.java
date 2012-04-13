package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.input.GInput;
import com.gej.map.Map;
import com.gej.map.MapLoader;
import com.gej.object.GAction;
import com.gej.object.GObject;

public class PlatformTest extends Game implements MapLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5231267119693208693L;
	
	Map map = null;
	
	Image   background = null;
	GObject bouncy = null;
	
	GAction left = null;
	GAction right = null;
	GAction space = null;
	GAction exit = null;
	
	int jump_time = 0;
	boolean jump_started = false;
	
	@Override
	public void initResources(){
		background = loadImage("resources/back_water.png");
		map = Map.loadMap("resources/PlatformTest.txt", this);
		space = new GAction("SPACE");
		left = new GAction("LEFT");
		right = new GAction("RIGHT");
		exit = new GAction("EXIT");
		GInput input = new GInput(this);
		input.mapToKey(space, KeyEvent.VK_SPACE);
		input.mapToKey(left, KeyEvent.VK_LEFT);
		input.mapToKey(right, KeyEvent.VK_RIGHT);
		input.mapToKey(exit, KeyEvent.VK_ESCAPE);
		input.setCursor(GInput.INVISIBLE_CURSOR);
		//
		Global.USE_PIXELPERFECT_COLLISION = true;
		//
		setFPS(150);
		setFullScreen(true);
	}

	@Override
	public GObject getObject(char c, int x, int y) {
		if (c=='D'){
			GObject wall = new GObject(loadImage("resources/dark_floor.png"));
			wall.setSolid(true);
			wall.setX(x);
			wall.setY(y);
			return wall;
		} else if (c=='G'){
			GObject wall = new GObject(loadImage("resources/grass_floor.png"));
			wall.setSolid(true);
			wall.setX(x);
			wall.setY(y);
			return wall;
		} else if (c=='B'){
			bouncy = new GObject(loadImage("resources/bouncy_ball.png"));
			bouncy.setX(x);
			bouncy.setY(y);
		}
		return null;
	}
	
	@Override
	public void update(long elapsedTime){
		if (exit.isPressed()){
			System.exit(0);
		}
		float nx = bouncy.getX();
		float ny = bouncy.getY();
		if (jump_started && jump_time<=1000){
			ny = ny - 0.15f * elapsedTime;
			jump_time += elapsedTime;
			if (!map.isCollisionFree(nx, ny, bouncy)){
				jump_started = false;
			}
		} else {
			ny = ny + 0.15f * elapsedTime;
			jump_started = false;
		}
		if (space.isPressed() && !jump_started){
			if (!map.isCollisionFree(nx, ny+5, bouncy)){
				jump_time = 0;
				jump_started = true;
			}
		}
		if (left.isPressed()){
			nx = nx - 0.15f * elapsedTime;
		}
		if (right.isPressed()){
			nx = nx + 0.15f * elapsedTime;
		}
		if (map.isCollisionFree(nx, bouncy.getY(), bouncy)){
			bouncy.setX(nx);
		}
		if (map.isCollisionFree(bouncy.getX(), ny, bouncy)){
			bouncy.setY(ny);
		}
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		map.renderMap(g);
		g.drawImage(bouncy.getImage(), Math.round(bouncy.getX()), Math.round(bouncy.getY()), null);
	}
	
	public static void main(String[] args){
		new PlatformTest();
	}

}
