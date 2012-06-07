package com.gej.test;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GKeyBoard;

public class KeyTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2785542933032354956L;

	@Override
	public void update(long elapsedTime) {
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawString("Left : " + GKeyBoard.isPressed(KeyEvent.VK_LEFT), 10, 20);
		g.drawString("Right : " + GKeyBoard.isPressed(KeyEvent.VK_RIGHT), 10,
		        50);
		g.drawString("Press ESCAPE to exit", 10, 80);
	}

	public static void main(String[] args) {
		GWindow.setup(new KeyTest());
	}

}
