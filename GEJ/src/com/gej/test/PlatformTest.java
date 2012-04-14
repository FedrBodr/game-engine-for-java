package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.gej.core.Game;
import com.gej.input.GInput;
import com.gej.map.Map;
import com.gej.map.MapLoader;
import com.gej.map.MapView;
import com.gej.object.GAction;
import com.gej.object.GObject;

public class PlatformTest extends Game implements MapLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5231267119693208693L;
	
	// The map and mapview objects
	Map map = null;
	MapView view = null;
	
	// Game resources
	Image   background = null;
	GObject bouncy = null;
	
	// Input actions
	GAction left  = null;
	GAction right = null;
	GAction space = null;
	GAction exit  = null;
	
	// The time the player had jumped in the current jump
	int jump_time = 0;
	// A jump is started but not ended
	boolean jump_started = false;
	
	@Override
	public void initResources(){
		// load the background
		background = loadImage("resources/back_water.png");
		// load the map and create a mapview
		map = Map.loadMap("resources/PlatformTest.txt", this);
		view  = new MapView(map);
		// Create the input actions
		space = new GAction("SPACE");
		left  = new GAction("LEFT");
		right = new GAction("RIGHT");
		exit  = new GAction("EXIT");
		// register them
		GInput input = new GInput(this);
		input.mapToKey(space, KeyEvent.VK_SPACE);
		input.mapToKey(left, KeyEvent.VK_LEFT);
		input.mapToKey(right, KeyEvent.VK_RIGHT);
		input.mapToKey(exit, KeyEvent.VK_ESCAPE);
		// disable the cursor
		input.setCursor(GInput.INVISIBLE_CURSOR);
		// set the fps and run in fullscreen mode
		setFPS(150);
		setFullScreen(true);
	}

	@Override
	public GObject getObject(char c, int x, int y) {
		if (c=='D'){
			// The D and G are walls but with different sprites
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
			// B is the player
			bouncy = new GObject(loadImage("resources/bouncy_ball.png"));
			bouncy.setX(x);
			bouncy.setY(y);
		}
		return null;
	}
	
	@Override
	public void update(long elapsedTime){
		// Make view center the player
		view.follow(bouncy);
		// If exit is pressed, quit the game
		if (exit.isPressed()){
			System.exit(0);
		}
		// The present positions of the player
		float nx = bouncy.getX();
		float ny = bouncy.getY();
		// If the jump has been started and till not yet completed
		// move the player up
		if (jump_started && jump_time<=1000){
			ny = ny - 0.15f * elapsedTime;
			// Increase the jump time
			jump_time += elapsedTime;
			// If there is a wall in the jump path, end the jump
			if (!map.isCollisionFree(nx, ny, bouncy)){
				jump_started = false;
			}
		} else {
			// The player is not in the jump. So apply gravity
			ny = ny + 0.15f * elapsedTime;
			// Stop the jump
			jump_started = false;
		}
		// If space is pressed and the player is not in any jump,
		// and there is a wall below the player, start the jump
		if (space.isPressed() && !jump_started){
			if (!map.isCollisionFree(nx, ny+5, bouncy)){
				jump_time = 0;
				jump_started = true;
			}
		}
		// If the left has been pressed, move the player left
		if (left.isPressed()){
			nx = nx - 0.15f * elapsedTime;
		}
		// If he pressed right, move him right
		if (right.isPressed()){
			nx = nx + 0.15f * elapsedTime;
		}
		// Move to the new x-position only if the position is collision free
		if (map.isCollisionFree(nx, bouncy.getY(), bouncy)){
			bouncy.setX(nx);
		}
		// Move to the new y-position only if the position is collision free
		if (map.isCollisionFree(bouncy.getX(), ny, bouncy)){
			bouncy.setY(ny);
		}
		// Update the view
		if (view!=null){
			view.update(elapsedTime);
		}
	}
	
	@Override
	public void render(Graphics2D g){
		// Draw the background
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		// Draw the view
		if (view!=null){
			view.render(g);
		}
	}
	
	// Start the game
	public static void main(String[] args){
		new PlatformTest();
	}

}
