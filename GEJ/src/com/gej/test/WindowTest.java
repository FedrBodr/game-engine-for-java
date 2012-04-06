package com.gej.test;

import java.awt.Graphics2D;

import com.gej.core.Game;

public class WindowTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5228879012918053301L;
	
	@Override
	public void initResources() {
		window.setTitle("Window Test");
		setFullScreen(true);
		setFPS(75);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawString("FPS : "+getFPS(), 15, 30);
		g.drawString("Delay : "+(1000/getFPS()), 15, 60);
		g.drawString("WindowTest : This program creates a window with refresh rate of 75 FPS", 15, 90);
	}
	
	public static void main(String[] args){
		new WindowTest();
	}

}
