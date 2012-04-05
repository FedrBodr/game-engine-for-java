package com.gej.test;

import java.awt.Graphics2D;

import com.gej.core.Game;

public class WindowTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5228879012918053301L;
	
	{
		window.setTitle("Test");
		setFullScreen(true);
	}
	
	@Override
	public void initResources() {
		// No need to initialize
	}

	@Override
	public void render(Graphics2D g) {
		// No rendering
	}
	
	public static void main(String[] args){
		new WindowTest();
	}

}
