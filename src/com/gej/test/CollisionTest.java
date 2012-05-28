package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.object.GObject;

public class CollisionTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609095432386666625L;
	
	ArrayList<GObject> objects = null;
	
	Image background = null;
	
	@Override
	public void initResources(){
		// Create the game resources
		objects = new ArrayList<GObject>();
		// Create the objects
		GObject object = new GObject(loadImage("resources/box.png"));
		GObject object2 =new GObject(loadImage("resources/box2.png"));
		// Set their positions
		object.setX(0f);
		object.setY(0f);
		object2.setX(getWidth()-object2.getWidth());
		object2.setY(getHeight()-object2.getHeight());
		// Set their velocities
		object.setVelocityX(0.25f);
		object.setVelocityY(0.25f);
		object2.setVelocityX(-0.25f);
		object2.setVelocityY(-0.25f);
		// Add them to the list
		objects.add(object);
		objects.add(object2);
		// Load the background
		background = loadImage("resources/back.png");
		// Setting the frame rate
		Global.FRAMES_PER_SECOND = 150;
	}
	
	@Override
	public void update(long elapsedTime){
		for (GObject object : objects){
			object.update(elapsedTime);
			object.move(elapsedTime);
			for (GObject object2 : objects){
				if (object.isCollidingWith(object2) && object!=object2){
					object.bounce(object2);
					//object2.bounce(object);
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		for (GObject object : objects){
			g.drawImage(object.getImage(), Math.round(object.getX()), Math.round(object.getY()), null);
		}
	}
	
	public static void main(String[] args){
		GWindow.setup(new CollisionTest());
	}

}
