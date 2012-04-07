package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.core.Game;
import com.gej.graphics.Animation;
import com.gej.object.GObject;

public class AnimationTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629799669553516690L;
	
	GObject animObject = null;
	Image background = null;
	
	@Override
	public void initResources(){
		window.setTitle("AnimationTest");
		setFPS(100);
		// The images
		Image image1 = loadImage("resources/box.png");
		Image image2 = loadImage("resources/box2.png");
		Animation anim = new Animation(new Image[]{image1, image2}, 250);
		animObject = new GObject(anim);
		background = loadImage("resources/back.png");
		setFullScreen(true);
	}
	
	@Override
	public void update(long elapsedTime){
		animObject.update(elapsedTime);
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		g.drawImage(animObject.getImage(), 100, 100, null);
	}
	
	public static void main(String[] args){
		new AnimationTest();
	}

}
