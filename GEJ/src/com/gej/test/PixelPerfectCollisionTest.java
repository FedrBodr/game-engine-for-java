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

public class PixelPerfectCollisionTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2907358085969161764L;
	
	GObject obj1 = null;
	GObject obj2 = null;
	
	@Override
	public void initResources(){
		obj1 = new GObject(loadImage("resources/bouncy_ball.png"));
		obj2 = new GObject(loadImage("resources/enemy_ball.png"));
		obj2.setX(150);
		obj2.setY(150);
		Background.setBackground(ImageTool.getColoredImage(Color.LIGHT_GRAY, Global.WIDTH, Global.WIDTH));
		///////////////////////////////////////////////////////////////////
		Global.USE_PIXELPERFECT_COLLISION = true;
		Global.TITLE                      = "Pixel Perfect Collision Test";
	}
	
	@Override
	public void update(long elapsedTime){
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_T)){
			Global.USE_PIXELPERFECT_COLLISION = !Global.USE_PIXELPERFECT_COLLISION;
		}
		obj1.setX(input.getMouseX());
		obj1.setY(input.getMouseY());
	}
	
	@Override
	public void render(Graphics2D g){
		Background.render(g);
		obj1.render(g);
		obj2.render(g);
		g.drawString("Collision :  " + obj1.isCollidingWith(obj2), 15, 15);
		if (Global.USE_PIXELPERFECT_COLLISION){
			g.drawString("Pixel Perfect collision detection is on", 15, 30);
		} else {
			g.drawString("Pixel Perfect collision detection is off", 15, 30);
		}
		g.drawString("Press 'T' to toggle", 15, 45);
	}
	
	public static void main(String[] args){
		GWindow.setup(new PixelPerfectCollisionTest());
	}

}
