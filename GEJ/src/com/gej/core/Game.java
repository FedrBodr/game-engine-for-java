package com.gej.core;

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
public abstract class Game extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934394613281562786L;
	
	protected GWindow window = null;
	
	protected boolean fullscreen = false;
	boolean running = false;
	
	public Game(){
		Thread th = new Thread(this);
		th.start();
		running = true;
		window = new GWindow(this, fullscreen);
		setIgnoreRepaint(true);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		initResources();
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		while (running){
			long elapsedTime = currTime - startTime;
			currTime += elapsedTime;
			update(elapsedTime);
			Graphics2D g = (Graphics2D)getGraphics();
			if (g!=null){
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(getForeground());
				render(g);
				g.dispose();
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disposeWindow(){
		window.getWindow().dispose();
	}
	
	public void setFullScreen(boolean bool){
		disposeWindow();
		window = new GWindow(this, bool);
	}
	
	public abstract void initResources();
	
	public abstract void render(Graphics2D g);
	
	public void update(long elapsedTime){
		// Do nothing
	}
	
	public Image loadImage(String name){
		return new ImageIcon(this.getClass().getClassLoader().getResource(name)).getImage();
	}
		
}
