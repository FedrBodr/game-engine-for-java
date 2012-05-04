package com.gej.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.util.ImageTool;

/**
 * Represents a game font made from a single sprite sheet.
 * Uses Images to represent alphabets
 * 
 * @author Sri Harsha Chilakapati
 */
public class GFont {
	
	private Image[] images    = null;
	private char[]  alphabets = null;
	
	/**
	 * Constructs a GFont object with a main image, numRows,
	 * numColumns and the order of alphabets in the image.
	 * @see ImageTool
	 * @param img The main image
	 * @param rows The number of rows
	 * @param cols The number of columns
	 * @param order The order of alphabets in the image
	 */
	public GFont(Image img, int rows, int cols, char[] order){
		images = ImageTool.splitImage(img, rows, cols);
		alphabets = order;
	}
	
	/**
	 * Renders the images to form the letters in the given string
	 * on to a graphic context with a 2D position. Note that '\n'
	 * could be used to represent a new line.
	 * @param text The string to be drawn
	 * @param x The starting x-coordinate
	 * @param y The starting y-coordinate
	 * @param g The graphic context
	 */
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
