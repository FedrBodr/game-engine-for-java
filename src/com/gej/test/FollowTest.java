package com.gej.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.graphics.Background;
import com.gej.input.GKeyBoard;
import com.gej.object.GObject;
import com.gej.util.ImageTool;

public class FollowTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3729401998330195025L;
	
	GObject obj1 = null;
	
	@Override
	public void initResources(){
		Background.setBackground(ImageTool.getColoredImage(Color.GRAY, Global.WIDTH, Global.HEIGHT));
		obj1 = new GObject(loadImage("resources/box.png"));
	}
	
	@Override
	public void update(long elapsedTime){
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		obj1.moveTo(input.getMouseX(), input.getMouseY(), 0.15f, elapsedTime);
	}
	
	@Override
	public void render(Graphics2D g){
		Background.render(g);
		obj1.render(g);
	}
	
	public static void main(String[] args){
		GWindow.setup(new FollowTest());
	}

}
