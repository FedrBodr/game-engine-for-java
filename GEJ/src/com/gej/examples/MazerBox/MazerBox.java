package com.gej.examples.MazerBox;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GInput;
import com.gej.map.Map;
import com.gej.map.MapLoader;
import com.gej.map.Tile;
import com.gej.object.GAction;
import com.gej.object.GObject;

public class MazerBox extends Game implements MapLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050992487378026282L;
	
	Image player_box = null;
	Image wall_box = null;
	Image background = null;
	
	Map map = null;
	
	GObject player = null;
	
	GAction up    = null;
	GAction down  = null;
	GAction left  = null;
	GAction right = null;
	GAction exit  = null;
	
	@Override
	public void initResources(){
		// Images
		background = loadImage("resources/back.png");
		player_box = loadImage("resources/box.png");
		wall_box   = loadImage("resources/box2.png");
		// Map
		map = Map.loadMap("resources/MazerBox.txt", this);
		// Actions
		up    = new GAction("UP");
		down  = new GAction("DOWN");
		left  = new GAction("LEFT");
		right = new GAction("RIGHT");
		exit  = new GAction("EXIT");
		// Input
		GInput input = new GInput(this);
		input.mapToKey(up, KeyEvent.VK_UP);
		input.mapToKey(down, KeyEvent.VK_DOWN);
		input.mapToKey(left, KeyEvent.VK_LEFT);
		input.mapToKey(right, KeyEvent.VK_RIGHT);
		input.mapToKey(exit, KeyEvent.VK_ESCAPE);
	}
	
	@Override
	public void update(long elapsedTime){
		// Check if exit is pressed
		if (exit.isPressed()){
			System.exit(0);
		}
		// The position of the player
		float nx = player.getX();
		float ny = player.getY();
		// The constant speed
		float speed = 0.15f;
		// Check the new positions
		if (up.isPressed()){
			ny = ny - speed * elapsedTime;
		}
		if (down.isPressed()){
			ny = ny + speed * elapsedTime;
		}
		if (left.isPressed()){
			nx = nx - speed * elapsedTime;
		}
		if (right.isPressed()){
			nx = nx + speed * elapsedTime;
		}
		// If the new position is collision free, move the player
		if (map.isObjectCollisionFree(nx, player.getY(), player)){
			player.setX(nx);
		}
		if (map.isObjectCollisionFree(player.getX(), ny, player)){
			player.setY(ny);
		}
	}
	
	@Override
	public void render(Graphics2D g){
		// Draw the background
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		// Draw the map
		map.renderMap(g);
		// Draw the player
		g.drawImage(player.getImage(), Math.round(player.getX()), Math.round(player.getY()), null);
	}

	@Override
	public GObject getObject(char c, int x, int y) {
		// MapLoader interface. Return the new GObject
		// based on the char present in the map
		if (c=='W'){
			// If the character is 'W', it's a wall
			GObject wall = new GObject(wall_box);
			wall.setX(x);
			wall.setY(y);
			wall.setSolid(true);
			return wall;
		} else if (c=='P'){
			// Now it's the player
			player = new GObject(player_box);
			player.setX(x);
			player.setY(y);
		}
		// Else return null
		return null;
	}
	
	public static void main(String[] args){
		GWindow.setup(new MazerBox(), "");
	}

	@Override
	public Tile getTile(char c, int x, int y) {
		// It won't use tiles
		return null;
	}
	
}
