package com.gej.graphics;

import java.awt.Image;

import com.gej.util.GUtil;

/**
 * This class is used to load the fonts from their respective font-descriptor
 * files. They are created like this.
 * 
 * <pre>
 * GFont font = GFontAdvanced.getFont(Game.loadImage(&quot;&lt;path-to-image&gt;&quot;), &quot;&lt;path-to-descriptor&gt;&quot;);
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class GFontAdvanced {

    /**
     * Creates a new GFont object by parsing the data from a font-descriptor.
     * Just type the characters as in the same order of the image file. Note
     * that all the characters should be in the same width and size.
     * 
     * @param img The image-file containing the characters
     * @param font_descriptor The font-descriptor file
     * @return The created GFont object
     */
    public static GFont getFont(Image img, String font_descriptor){
        try {
            // Load the lines from the descriptor file
            String[] lines = GUtil.loadLinesFromFile(font_descriptor);
            int rows = 0;
            int cols = 0;
            String data = "";
            // Parse the lines
            for (int i = 0; i < lines.length; i++) {
                rows++;
                cols = Math.max(cols, lines[i].length());
                data += lines[i];
            }
            // Create and return the font object
            return new GFont(img, rows, cols, data);
        } catch (Exception e) {
        }
        return null;
    }

}
