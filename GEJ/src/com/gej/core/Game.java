package com.gej.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class is the main class for any game. You should extend this class
 * to write a game.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class Game extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934394613281562786L;
	
	/** The GWindow object for this game */
	protected GWindow window = null;
	
	// Private variables
	private boolean fullscreen = false;
	private boolean running = false;
    
	private int fps = 50;
	private int delay = 1000/fps;
	
	/**
	 * Constructs a new Game with default values.
	 * No need to use the constructor, as it is
	 * automatically called by the constructor of
	 * the sub classes.
	 */
	public Game(){
		running = true;
		window = new GWindow(this, fullscreen);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setDoubleBuffered(true);
		initResources();
		run();
	}
	
	/**
	 * Returns the current FPS value of this game.
	 * @return The current FPS value
	 */
	public int getFPS(){
		return 1000/delay;
	}
	
	/**
	 * Sets the number of frames to be rendered by this game
	 * @param fps The FPS value
	 */
	public void setFPS(int fps){
		this.fps = fps;
		delay = 1000/fps;
	}
	
	/**
	 * Starts the game and acts as a game loop.
	 * The default FPS value is 50. But you can
	 * change it in your code by using the
	 * setFPS() method.
	 */
	public void run(){
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		while (running){
			currTime = System.currentTimeMillis();
			long elapsedTime = currTime - startTime;
			delay = 1000/fps;
			try {
				update(elapsedTime);
				repaint();
				startTime = System.currentTimeMillis();
				Thread.sleep(delay);
			} catch (InterruptedException | NullPointerException e) {
				// Do nothing, but try again
			}
		}
	}
	
	/** Paint and render the game */
	public void paint(Graphics g){
		Graphics2D g2D = (Graphics2D)g;
		g2D.setColor(getBackground());
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.setColor(getForeground());
		render(g2D);
		g2D.dispose();
	}
	
	/**
	 * Dispose the game's GWindow. Equivalent to
	 * <code>window.getWindow().dispose()</code>
	 */
	public void disposeWindow(){
		window.getWindow().dispose();
	}
	
	/**
	 * Sets the full screen state of the game.
	 * @param bool true for full screen else false.
	 */
	public void setFullScreen(boolean bool){
		disposeWindow();
		window = new GWindow(this, bool);
	}
	
	/**
	 * Initialize the resources
	 */
	public void initResources() {}
	
	/**
	 * Render the game. Draw all your objects etc., in
	 * this game.
	 * @param g The graphics context
	 */
	public void render(Graphics2D g){}
	
	/**
	 * Use this method to update your game. You could
	 * check input, collision between GObjects, and move
	 * them in this method.
	 * @param elapsedTime The time elapsed in the current frame
	 */
	public void update(long elapsedTime) {}
	
	/**
	 * Gets you image to load from the root of your jar file
	 * @param name The name of your image
	 * @return The loaded image.
	 */
	public Image loadImage(String name){
		return new ImageIcon(this.getClass().getClassLoader().getResource(name)).getImage();
	}
		
}
