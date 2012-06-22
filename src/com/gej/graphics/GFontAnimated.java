package com.gej.graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.gej.core.Updateable;

/**
 * An implementation of animation to animate game fonts. You
 * add some GFont's to this class and update this. According
 * to the time elapsed, the correct font is chosen. When you
 * invoke the renderText() method, it is rendered with the
 * current font.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GFontAnimated implements Updateable {
    
    // The list of fonts
    private ArrayList<GFont> fonts = null;
    // Duration of each and every font
    private ArrayList<Integer> durations = null;
    
    // Index of current font
    private int currFontIndex = 0;
    
    // Time elapsed since last font change
    private int duration = 0;
    
    /**
     * Constructs an empty animated font
     */
    public GFontAnimated(){
        fonts = new ArrayList<GFont>();
        durations = new ArrayList<Integer>();
    }
    
    /**
     * Constructs an animated font from an array of fonts and duration
     * @param fonts The array of fonts
     * @param duration The duration of each font
     */
    public GFontAnimated(GFont[] fonts, int duration){
        this();
        for (int i=0; i<fonts.length; i++){
            addFont(fonts[i], duration);
        }
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void update(long elapsedTime){
        duration += elapsedTime;
        if (duration >= durations.get(currFontIndex)) {
            duration = 0;
            currFontIndex++;
            if (currFontIndex >= fonts.size()) {
                currFontIndex = 0;
            }
        }
    }
    
    /**
     * Renders text with the current font
     * @param txt The text to be rendered
     * @param g The graphics context
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public synchronized void renderText(String txt, Graphics2D g, int x, int y){
        getFont().renderText(txt, g, x, y);
    }
    
    /**
     * Gets the current font in the order
     * @return The current font in the order
     */
    public synchronized GFont getFont(){
        return fonts.get(currFontIndex);
    }
    
    /**
     * Adds a font to the font list
     * @param font The GFont object
     * @param duration The duration of the font
     */
    public synchronized void addFont(GFont font, int duration){
        fonts.add(font);
        durations.add(duration);
    }
    
    /**
     * {@inheritDoc}
     */
    public GFontAnimated clone(){
        GFontAnimated fanim = new GFontAnimated();
        for (int i=0; i<fonts.size(); i++){
            fanim.addFont(fonts.get(i), durations.get(i));
        }
        return fanim;
    }

}
