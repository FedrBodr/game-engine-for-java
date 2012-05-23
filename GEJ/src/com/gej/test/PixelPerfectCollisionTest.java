package com.gej.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.graphics.Background;
import com.gej.graphics.GFont;
import com.gej.graphics.GFontAdvanced;
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
	
	GFont font = null;
	
	@Override
	public void initResources(){
		font = GFontAdvanced.getFont(loadImage("ImageFonts/font_blue.png"), "ImageFonts/DefFontDescriptor.txt");
		obj1 = new GObject(loadImage("resources/bouncy_ball.png"));
		obj2 = new GObject(loadImage("resources/enemy_ball.png"));
		obj2.setX(150);
		obj2.setY(150);
		Background.setBackground(ImageTool.getColoredImage(Color.LIGHT_GRAY, Global.WIDTH, Global.WIDTH));
		///////////////////////////////////////////////////////////////////
		Global.USE_PIXELPERFECT_COLLISION = true;
		Global.TITLE                      = "Pixel Perfect Collision Test";
		Global.HIDE_CURSOR                = true;
	}
	
	@Override
	public void update(long elapsedTime){
		Global.TITLE = "Pixel perfect collision detection : FPS : " + Global.FRAMES_PER_SECOND;
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_T)){
			Global.USE_PIXELPERFECT_COLLISION = !Global.USE_PIXELPERFECT_COLLISION;
		}
		obj1.setX(input.getMouseX());
		obj1.setY(input.getMouseY());
		if (obj1.isCollidingWith(obj2)){
			obj2.moveToContact(obj1);
		}
	}
	
	@Override
	public void render(Graphics2D g){
		Background.render(g);
		obj1.render(g);
		obj2.render(g);
		String status = "OFF";
		if (Global.USE_PIXELPERFECT_COLLISION){
			status = "ON";
		}
		String status2 = "FALSE";
		if (obj1.isCollidingWith(obj2)){
			status2 = "TRUE";
		}
		font.renderText("COLLISION : " + status2, g, 15, 15);
		font.renderText("PPCD IS " + status, g, 15, 45);
		font.renderText("PRESS T TO TOGGLE", g, 15, 75);
	}
	
	public static void main(String[] args){
		GWindow.setup(new PixelPerfectCollisionTest());
	}

}
