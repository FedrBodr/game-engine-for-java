package com.gej.test;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GInput;
import com.gej.object.GAction;

public class KeyTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2785542933032354956L;
	
	GInput input;
	
	GAction left;
	GAction right;
	GAction exit;

	@Override
	public void initResources() {
		left = new GAction("LEFT");
		right = new GAction("RIGHT");
		exit = new GAction("EXIT");
		input = new GInput(this);
		input.mapToKey(left, KeyEvent.VK_LEFT);
		input.mapToKey(left, KeyEvent.VK_A);
		input.mapToKey(right, KeyEvent.VK_RIGHT);
		input.mapToKey(right, KeyEvent.VK_D);
		input.mapToKey(exit, KeyEvent.VK_ESCAPE);
	}
	
	@Override
	public void update(long elapsedTime){
		if (exit.isPressed()){
			System.exit(0);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawString("Left : " + left.isPressed(), 10, 20);
		g.drawString("Right : " + right.isPressed(), 10, 50);
		g.drawString("Press ESCAPE to exit", 10, 80);
	}
	
	public static void main(String[] args){
		GWindow.setup(new KeyTest());
	}

}
