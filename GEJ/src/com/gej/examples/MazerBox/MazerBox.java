package com.gej.examples.MazerBox;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GKeyBoard;
import com.gej.map.Map;
import com.gej.map.MapLoader;
import com.gej.map.Tile;
import com.gej.object.GObject;

public class MazerBox extends Game implements MapLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050992487378026282L;
	
	Image player_box = null;
	Image wall_box = null;
	Image background = null;
		
	GObject player = null;
	
	@Override
	public void initResources(){
		// Images
		background = loadImage("resources/back.png");
		player_box = loadImage("resources/box.png");
		wall_box   = loadImage("resources/box2.png");
		// Map
		Map.loadMap("resources/MazerBox.txt", this);
	}
	
	@Override
	public void update(long elapsedTime){
		// Check if exit is pressed
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		// The position of the player
		float nx = player.getX();
		float ny = player.getY();
		// The constant speed
		float speed = 0.15f;
		// Check the new positions
		if (GKeyBoard.isPressed(KeyEvent.VK_UP)){
			ny = ny - speed * elapsedTime;
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_DOWN)){
			ny = ny + speed * elapsedTime;
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_LEFT)){
			nx = nx - speed * elapsedTime;
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_RIGHT)){
			nx = nx + speed * elapsedTime;
		}
		// If the new position is collision free, move the player
		if (Map.isObjectCollisionFree(nx, player.getY(), player)){
			player.setX(nx);
		}
		if (Map.isObjectCollisionFree(player.getX(), ny, player)){
			player.setY(ny);
		}
	}
	
	@Override
	public void render(Graphics2D g){
		// Draw the background
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		// Draw the map
		Map.renderMap(g);
		// Draw the player
		g.drawImage(player.getImage(), Math.round(player.getX()), Math.round(player.getY()), null);
	}

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
		GWindow.setup(new MazerBox());
	}

	public Tile getTile(char c, int x, int y) {
		// It won't use tiles
		return null;
	}
	
}
