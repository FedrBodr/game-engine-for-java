package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.graphics.Animation;
import com.gej.graphics.Background;
import com.gej.graphics.GFont;
import com.gej.graphics.GFontAdvanced;
import com.gej.input.GKeyBoard;
import com.gej.map.Map;
import com.gej.map.MapLoader;
import com.gej.map.MapView;
import com.gej.map.Tile;
import com.gej.object.GObject;
import com.gej.util.ImageTool;

public class PlatformTest extends Game implements MapLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5231267119693208693L;

	GFont fpsfont = null;
	
	@Override
	public void initResources(){
		// load the background
		Background.setBackground(ImageTool.resize(loadImage("resources/back_water.png"), Global.WIDTH, Global.HEIGHT));
		// load the font
		fpsfont = GFontAdvanced.getFont(loadImage("ImageFonts/font_blue.png"), "ImageFonts/DefFontDescriptor.txt");
		// load the Map and create a MapView
		Map.loadMap(48, "resources/PlatformTest.txt", this);
		// configure the game
		Global.USE_PIXELPERFECT_COLLISION = true;
		//Global.FULLSCREEN                 = true;
		Global.HIDE_CURSOR                = true;
	}

	public GObject getObject(char c, int x, int y) {
		if (c=='B'){
			// B is the player
			Bouncy bouncy = new Bouncy(loadImage("resources/bouncy_ball.png"));
			bouncy.setX(x);
			bouncy.setY(y);
			return bouncy;
		} else if (c=='E'){
			// It's the enemy
			Enemy enemy = new Enemy(loadImage("resources/enemy_ball.png"));
			enemy.setX(x);
			enemy.setY(y);
			return enemy;
		} else if (c=='C'){
			return new Coin(x, y);
		}else if (c=='O'){
			return new Door(x, y);
		}
		return null;
	}

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
		// If exit is pressed, quit the game
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_R)){
			resetMap();
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_F4)){
			Global.FULLSCREEN = !Global.FULLSCREEN;
		}
	}
	
	@Override
	public void render(Graphics2D g){
		// Draw the Map
		Background.render(g);
		MapView.render(g);
		fpsfont.renderText("FPS: " + Global.FRAMES_PER_SECOND, g, 15, 15);
	}
	
	public void resetMap(){
		Map.loadMap(48, "resources/PlatformTest.txt", this);
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
		// The player is on ground
		boolean onGround     = true;
		
		public Bouncy(Image img) {
			super(img);
		}
		
		public void update(long elapsedTime){
			MapView.follow(this);
			// The present positions of the player
			float nx = getX();
			float ny = getY();
			// If the jump has been started and till not yet completed
			// move the player up
			if (jump_started && jump_time<=1000){
				ny = ny - 0.15f * elapsedTime;
				// Increase the jump time
				jump_time += elapsedTime;
				// If there is a wall in the jump path, end the jump
				if (!Map.isTileCollisionFree(nx, ny, this)){
					jump_started = false;
				}
			} else if (!onGround){
				// The player is not in the jump. So apply gravity
				ny = ny + 0.15f * elapsedTime;
				// Stop the jump
				jump_started = false;
			}
			// If space is pressed and the player is not in any jump,
			// and there is a wall below the player, start the jump
			if ((GKeyBoard.isPressed(KeyEvent.VK_SPACE) || GKeyBoard.isPressed(KeyEvent.VK_UP)) && !jump_started){
				if (!Map.isTileCollisionFree(nx, ny+5, this)){
					jump_time = 0;
					jump_started = true;
					onGround = false;
				}
			}
			// If the left has been pressed, move the player left
			if (GKeyBoard.isPressed(KeyEvent.VK_LEFT)){
				nx = nx - 0.15f * elapsedTime;
			}
			// If he pressed right, move him right
			if (GKeyBoard.isPressed(KeyEvent.VK_RIGHT)){
				nx = nx + 0.15f * elapsedTime;
			}
			// Move to the new x-position only if the position is collision free
			if (Map.isTileCollisionFree(nx, getY(), this)){
				setX(nx);
			}
			// Move to the new y-position only if the position is collision free
			if (Map.isTileCollisionFree(getX(), ny, this)){
				setY(ny);
			} else {
				// We move down. Line the player with the bottom tile
				if (ny>getY() && !onGround){
					try {
						setY(Map.getTileCollidingPoint(getX(), ny, getWidth(), getHeight()).y - getHeight() + 1);
						onGround = true;
					} catch (Exception e){}
				}
			}
		}
		
		public void collision(GObject other){
			if (other instanceof Enemy && other.isAlive()){
				resetMap();
			} else if (other instanceof Coin && other.isAlive()){
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
				if (MapView.isVisible(this)){
					float nx = getX() + 0.11f * elapsedTime;
					float ny = getY() + 0.15f * elapsedTime;
					boolean bool1 = false;
					boolean bool2 = false;
					if (Map.isTileCollisionFree(nx, getY(), this)){
						setX(nx);
					} else {
						bool1 = true;
					}
					if (Map.isTileCollisionFree(getX(), ny, this)){
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
		
	}
	
}
