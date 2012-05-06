package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.graphics.Animation;
import com.gej.graphics.Background;
import com.gej.input.GKeyBoard;
import com.gej.object.GObject;

public class AnimationTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5629799669553516690L;
	
	GObject animObject = null;
	JButton button = new JButton("Click me to remove me");
	
	@Override
	public void initResources(){
		// The images
		Image image1 = loadImage("resources/box.png");
		Image image2 = loadImage("resources/box2.png");
		Animation anim = new Animation(new Image[]{image1, image2}, 250);
		animObject = new GObject(anim);
		Background.setBackground(loadImage("resources/back.png"));
		addComponent(button, 150, 150);
		Global.FULLSCREEN = true;
	}
	
	@Override
	public void componentClicked(JComponent comp){
		if (comp==button){
			removeComponent(comp);
		} else {
			System.out.println(".....................");
		}
	}
	
	@Override
	public void update(long elapsedTime){
		animObject.update(elapsedTime);
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
	}
	
	@Override
	public void render(Graphics2D g){
		Background.render(g);
		g.drawImage(animObject.getImage(), 100, 100, null);
	}
	
	public static void main(String[] args){
		GWindow.setup(new AnimationTest());
	}

}
