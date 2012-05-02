package com.gej.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.core.Global;
import com.gej.util.ImageTool;

public class Background {
	
	private static Image background = ImageTool.getEmptyImage(Global.WIDTH, Global.HEIGHT);
	
	public static void setBackground(Image img){
		background = img;
	}
	
	public static void render(Graphics2D g){
		g.drawImage(background, 0, 0, null);
	}
	
	public static Image getBackground(){
		return background;
	}

}
