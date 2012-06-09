package com.gej.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.core.Global;
import com.gej.util.ImageTool;

/**
 * This class is used to represent the current background of the current map the game is running. To
 * prevent the user from instantiating this class, it is declared as abstract. To set an image as
 * background, use
 * 
 * <pre>
 * Background.setBackground(myImage);
 * </pre>
 * 
 * You may create interesting backgrounds by using the ImageTool class
 * @see ImageTool
 * @author Sri Harsha Chilakapati
 */
public abstract class Background {
    
    private static Image background = ImageTool.getEmptyImage(Global.WIDTH, Global.HEIGHT);
    
    /**
     * Sets an image as the background
     * @param img The background image
     */
    public static void setBackground(Image img){
        background = img;
    }
    
    /**
     * Renders the current background
     * @param g The graphics context
     */
    public static void render(Graphics2D g){
        g.drawImage(background, 0, 0, null);
    }
    
    /**
     * Gets the current background
     * @return The current background image
     */
    public static Image getBackground(){
        return background;
    }
    
}
