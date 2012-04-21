package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.graphics.Animation;
import com.gej.graphics.ImageTool;
import com.gej.input.GInput;
import com.gej.map.Map;
import com.gej.map.MapLoader;
import com.gej.map.MapView;
import com.gej.map.Tile;
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
	Bouncy bouncy = null;
	
	// Input actions
	GAction left  = null;
	GAction right = null;
	GAction space = null;
	GAction reset = null;
	GAction f4    = null;
	GAction exit  = null;
	
	// Private game variables
	int numCoins = 0;
	int collectedCoins = 0;
	
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
		reset = new GAction("RESET");
		f4    = new GAction("F4");
		exit  = new GAction("EXIT");
		// register them
		GInput input = new GInput(this);
		input.mapToKey(space, KeyEvent.VK_SPACE);
		input.mapToKey(left, KeyEvent.VK_LEFT);
		input.mapToKey(right, KeyEvent.VK_RIGHT);
		input.mapToKey(reset, KeyEvent.VK_R);
		input.mapToKey(f4, KeyEvent.VK_F4);
		input.mapToKey(exit, KeyEvent.VK_ESCAPE);
		// disable the cursor
		input.setCursor(GInput.INVISIBLE_CURSOR);
		Global.FRAMES_PER_SECOND = 150;
	}

	@Override
	public GObject getObject(char c, int x, int y) {
		if (c=='B'){
			// B is the player
			bouncy = new Bouncy(loadImage("resources/bouncy_ball.png"));
			bouncy.setX(x);
			bouncy.setY(y);
		} else if (c=='E'){
			// It's the enemy
			Enemy enemy = new Enemy(loadImage("resources/enemy_ball.png"));
			enemy.setX(x);
			enemy.setY(y);
			return enemy;
		} else if (c=='C'){
			numCoins++;
			return new Coin(x, y);
		} else if (c=='c'){
			return new Coin(x, y);
		} else if (c=='O'){
			return new Door(x, y);
		}
		return null;
	}

	@Override
	public Tile getTile(char c, int x, int y) {
		if (c=='D'){
			// The D and G are walls but with different sprites
			return new Tile(loadImage("resources/dark_floor.png"), x, y);
		} else if (c=='G'){
			return new Tile(loadImage("resources/grass_floor.png"), x, y);
		} else {
			return null;
		}
	}
	
	@Override
	public void update(long elapsedTime){
		// Make view center the player
		view.follow(bouncy);
		// If exit is pressed, quit the game
		if (exit.isPressed()){
			System.exit(0);
		}
		if (reset.isPressed()){
			resetMap();
		}
		if (f4.isPressed()){
			Global.FULLSCREEN = !Global.FULLSCREEN;
		}
		bouncy.update(elapsedTime);
		// Update game objects
		ArrayList<GObject> objects = map.getObjects();
		for (int i=0; i<objects.size(); i++){
			GObject obj = objects.get(i);
			if (obj!=null && obj.isAlive()){
				obj.update(elapsedTime);
				if (obj.isCollidingWith(bouncy)){
					bouncy.collision(obj);
				}
			}
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
		g.drawString("FPS: " + Global.FRAMES_PER_SECOND, 15, 15);
	}
	
	public void resetMap(){
		map = Map.loadMap("resources/PlatformTest.txt", this);
		view  = new MapView(map);
	}
	
	// Start the game
	public static void main(String[] args){
		GWindow.setup(new PlatformTest());
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	public class Bouncy extends GObject {
		
		// The time the player had jumped in the current jump
		int jump_time = 0;
		// A jump is started but not ended
		boolean jump_started = false;
		
		public Bouncy(Image img) {
			super(img);
		}
		
		public void update(long elapsedTime){
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
				if (!map.isTileCollisionFree(nx, ny, bouncy)){
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
				if (!map.isTileCollisionFree(nx, ny+5, bouncy)){
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
			if (!map.isObjectCollisionFree(nx, ny, bouncy)){
				if (map.getCollidingObject(nx, ny, bouncy.getWidth(), bouncy.getHeight()) instanceof Door){
					if (numCoins==collectedCoins){
						setX(nx);
						setY(ny);
					}
				}
			}
			// Move to the new x-position only if the position is collision free
			if (map.isTileCollisionFree(nx, bouncy.getY(), this)){
				setX(nx);
			}
			// Move to the new y-position only if the position is collision free
			if (map.isTileCollisionFree(bouncy.getX(), ny, this)){
				setY(ny);
			}
		}
		
		public void collision(GObject other){
			if (other instanceof Enemy && other.isAlive()){
				resetMap();
			} else if (other instanceof Coin && other.isAlive()){
				collectedCoins++;
				other.destroy();
			}
		}
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public class Enemy extends GObject {

		public Enemy(Image img) {
			super(img);
		}
		
		public void update(long elapsedTime){
			if (isAlive()){
				if (view.isVisible(this)){
					float nx = getX() + 0.11f * elapsedTime;
					float ny = getY() + 0.15f * elapsedTime;
					boolean bool1 = false;
					boolean bool2 = false;
					if (map.isTileCollisionFree(nx, getY(), this)){
						setX(nx);
					} else {
						bool1 = true;
					}
					if (map.isTileCollisionFree(getX(), ny, this)){
						setY(ny);
					} else {
						bool2 = true;
					}
					if (bool1 && bool2){
						destroy();
					}
				}
			}
		}
		
	}	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public class Coin extends GObject {
		
		public Coin(int x, int y){
			super(new Animation(ImageTool.splitImage(loadImage("resources/coin.png"), 1, 32), 100));
			setX(x);
			setY(y);
		}
		
	}	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public class Door extends GObject {
		
		public Door(int x, int y){
			super(loadImage("resources/dark_floor.png"));
			setX(x);
			setY(y);
		}
		
		@Override
		public void update(long elapsedTime){
			if (collectedCoins==numCoins){
				destroy();
			}
		}
		
		@Override
		public Image getImage(){
			return getAnimation().getImage();
		}
		
	}
	
}
