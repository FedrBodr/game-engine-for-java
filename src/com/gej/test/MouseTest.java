package com.gej.test;

import java.awt.Graphics2D;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GMouse;

public class MouseTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4656481028476571381L;
	
	public void render(Graphics2D g){
		g.drawString("(" + GMouse.MOUSE_X + ", " + GMouse.MOUSE_Y + ")", 15, 30);
	}
	
	public static void main(String[] args){
		GWindow.setup(new MouseTest());
	}

}
