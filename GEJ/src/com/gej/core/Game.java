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
		setDoubleBuffered(true);
		initResources();
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		while (running){
			currTime = System.currentTimeMillis();
			long elapsedTime = currTime - startTime;
			update(elapsedTime);
			repaint();
			startTime = System.currentTimeMillis();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g){
		Graphics2D g2D = (Graphics2D)g;
		g2D.setColor(getBackground());
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.setColor(getForeground());
		render(g2D);
		g2D.dispose();
	}
	
	public void disposeWindow(){
		window.getWindow().dispose();
	}
	
	public void setFullScreen(boolean bool){
		disposeWindow();
		window = new GWindow(this, bool);
	}
	
	public synchronized void initResources() {}
	
	public synchronized void render(Graphics2D g){}
	
	public synchronized void update(long elapsedTime) {}
	
	public Image loadImage(String name){
		return new ImageIcon(this.getClass().getClassLoader().getResource(name)).getImage();
	}
		
}
