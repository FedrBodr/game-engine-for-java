package com.gej.object;

import java.awt.Image;

import com.gej.graphics.Animation;

public class GObject {
	
	Animation anim = null;
	
	public float x = 0;
	public float y = 0;
	
	public float dx = 0;
	public float dy = 0;
	
	public GObject(Animation anim){
		this.anim = anim;
	}
	
	public GObject(Image image){
		this(new Animation(new Image[]{image}, 1000));
	}
	
	public void update(long elapsedTime){
		x = x + dx * elapsedTime;
		y = y + dy * elapsedTime;
		anim.update(elapsedTime);
	}
	
	public int getWidth(){
		return anim.getImage().getWidth(null);
	}
	
	public int getHeight(){
		return anim.getImage().getHeight(null);
	}
	
	public Image getImage(){
		return anim.getImage();
	}

}
