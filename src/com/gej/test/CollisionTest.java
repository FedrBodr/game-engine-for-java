package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.input.GMouse;
import com.gej.object.GObject;

public class CollisionTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609095432386666625L;
	
	GObject obj1 = null;
	GObject obj2 = null;
	
	Image background = null;
	
	@Override
	public void initResources(){
		// Create the objects
		obj1 = new GObject(loadImage("resources/bouncy_ball.png"));
		obj2 = new GObject(loadImage("resources/enemy_ball.png"));
		// Set their positions
		obj1.setX(0f);
		obj1.setY(0f);
		obj2.setX(150);
		obj2.setY(150);
		obj2.setVelocityX(0.05f);
		obj2.setVelocityY(0.05f);
		// Load the background
		background = loadImage("resources/back.png");
		// Setting the frame rate
		Global.FRAMES_PER_SECOND = 150;
	}
	
	@Override
	public void update(long elapsedTime){
		obj1.setX(GMouse.MOUSE_X);
		obj1.setY(GMouse.MOUSE_Y);
		if (obj1.isCollidingWith(obj2)){
			obj2.bounce();
		}
		obj2.move(elapsedTime);
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		obj1.render(g);
		obj2.render(g);
	}
	
	public static void main(String[] args){
		GWindow.setup(new CollisionTest());
	}

}
