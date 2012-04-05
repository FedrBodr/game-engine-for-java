package com.gej.core;

import java.awt.Graphics2D;

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

	public Game(){
		
	}
	
	public abstract void initResources();
	
	public abstract void render(Graphics2D g);
	
	public void update(int elapsedTime){
		// Do nothing
	}
	
}
