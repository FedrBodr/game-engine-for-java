package com.gej.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.gej.util.ImageTool;

/**
 * Represents a game font made from a single sprite sheet. Uses Images to
 * represent alphabets. You can use the GFontAdvanced class to load the fonts
 * from the font descriptors like this.
 * 
 * <pre>
 * GFont font = GFontAdvanced.getFont(Game.loadImage(&quot;&lt;path-to-image&gt;&quot;), &quot;&lt;path-to-descriptor&gt;&quot;);
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class GFont {

    /** The array of split images */
    private BufferedImage[] imgs;
    /** The array of the order of characters */
    private char[] characters;

    /**
     * Constructs a GFont object with a main image, numRows, numColumns and the
     * order of alphabets in the image.
     * 
     * @see ImageTool
     * @param img The main image
     * @param rows The number of rows
     * @param cols The number of columns
     * @param order The order of alphabets in the image
     */
    public GFont(Image img, int rows, int cols, char[] order) {
        imgs = ImageTool.splitImage(img, rows, cols);
        characters = order;
    }

    /**
     * Constructs a GFont object with a main image, numRows, numColumns and the
     * order of alphabets in the image.
     * 
     * @see ImageTool
     * @param img The main image
     * @param rows The number of rows
     * @param cols The number of columns
     * @param order The order of alphabets in the image
     */
    public GFont(Image img, int rows, int cols, String order) {
        this(img, rows, cols, order.toCharArray());
    }

    /**
     * This is the method which we use to draw text on the window. Note that the
     * maximum length of the string is 40 cols.
     * 
     * @param txt - The text to be drawn
     * @param g - The Graphics2D object
     * @param x - The starting x position
     * @param y - The starting y position
     */
    public void renderText(String txt, Graphics2D g, int x, int y){
        // The x value of the character
        int x_pos = x;
        // The y value of the character
        int y_pos = y;
        // Start looping and draw the text
        for (int i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) != ' ') {
                // If the character is not a space, we'll draw it
                for (int j = 0; j < characters.length; j++) {
                    // Check for the right image
                    if (txt.charAt(i) == characters[j]) {
                        // Draw the image
                        g.drawImage(imgs[j], x_pos, y_pos, null);
                        // Increase the x position
                        x_pos += imgs[j].getWidth(null);
                    }
                }
            } else if (txt.charAt(i) == '\n') {
                y_pos = y_pos + imgs[i].getHeight(null);
            } else {
                // If the character is a space, we'll just increase
                // x position so we don't waste any memory
                x_pos = x_pos + imgs[i].getWidth(null);
            }
        }
    }

}
