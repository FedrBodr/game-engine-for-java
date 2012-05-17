package com.gej.map;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.gej.util.ImageTool;

public class Tile {
	
	private Image img = null;
	private BufferedImage bimg = null;
	private int x = 0;
	private int y = 0;
	
	public Tile(Image img, int x, int y){
		this.img = img;
		bimg = ImageTool.toBufferedImage(img);
		this.x   = x;
		this.y   = y;
	}
	
	public Image getImage(){
		return img;
	}
	
	public BufferedImage getBufferedImage(){
		return bimg;
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
