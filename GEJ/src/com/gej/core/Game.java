package com.gej.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class is the main class for any game. You should extend this class
 * to write a game.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class Game extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934394613281562786L;
		
	// Private variables
	private boolean running = false;
    	
	private HashMap<String, Image> cache = null;
	
	/**
	 * Constructs a new Game with default values.
	 * No need to use the constructor, as it is
	 * automatically called by the constructor of
	 * the sub classes.
	 */
	public Game(){
		running = true;
		cache = new HashMap<String, Image>();
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setDoubleBuffered(true);
		Thread th = new Thread(this);
		th.start();
	}
	
		
	/**
	 * Starts the game and acts as a game loop.
	 * The default FPS value is 100. But you can
	 * change it in your code by using the Global class.
	 */
	public void run(){
		initResources();
		long startTime = System.nanoTime()/1000000;
		long currTime = startTime;
		while (running){
			long elapsedTime = currTime - startTime;
			startTime = System.nanoTime()/1000000;
			update(elapsedTime);
			for (int i=0; i<Global.UPDATEABLES.size(); i++){
				Updateable upd = Global.UPDATEABLES.get(i);
				upd.update(elapsedTime);
			}
			repaint();
			try {
				Thread.sleep(1000/Global.FRAMES_PER_SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currTime = System.nanoTime()/1000000;
		}
	}
	
	public void paint(Graphics g){
		try {
			Graphics2D g2D = (Graphics2D)g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setColor(getBackground());
			g2D.fillRect(0, 0, getWidth(), getHeight());
			g2D.setColor(getForeground());
			render(g2D);
			paintComponents(g2D);
			g2D.dispose();
		} catch(NullPointerException e){
			
		}
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
		Image img = null;
		if (cache.containsKey(name)){
			img = cache.get(name);
		} else {
			img =  new ImageIcon(this.getClass().getClassLoader().getResource(name)).getImage();
			cache.put(name, img);
		}
		return img;
	}
		
}
