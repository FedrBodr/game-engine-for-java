package com.gej.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.util.ImageTool;

public class GFont {
	
	private Image[] images    = null;
	private char[]  alphabets = null;
	
	public GFont(Image img, int rows, int cols, char[] order){
		images = ImageTool.splitImage(img, rows, cols);
		alphabets = order;
	}
	
	public void renderText(String text, int x, int y, Graphics2D g){
		try {
			for (int i=0; i<text.length(); i++){
				if (text.charAt(i)=='\n'){
					y += images[0].getHeight(null) + 4;
				} else {
					Image alphabet = null;
					for (int j=0; j<alphabets.length; j++){
						if (alphabets[j]==text.charAt(i)){
							alphabet = images[i];
						}
					}
					g.drawImage(alphabet, x, y, null);
					x += alphabet.getWidth(null) + 1;
				}
			}
		} catch (Exception e){
			// Do nothing
		}
	}

}
