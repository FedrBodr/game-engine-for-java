package com.gej.map;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {
	
	private Image img = null;
	private int x = 0;
	private int y = 0;
	
	public Tile(Image img, int x, int y){
		this.img = img;
		this.x   = x;
		this.y   = y;
	}
	
	public Image getImage(){
		return img;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
	}

}
