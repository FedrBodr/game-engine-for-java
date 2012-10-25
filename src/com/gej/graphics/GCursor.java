package com.gej.graphics;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import com.gej.util.ImageTool;

/**
 * Contains some default cursors and ability to create custom cursors from images.
 * 
 * @author Sri Harsha Chilakapati
 */
public final class GCursor {
    
    // Cannot instantiate this class
    private GCursor(){}
    
    /** The default cursor */
    public static Cursor DEFAULT = Cursor.getDefaultCursor();
    
    /** The current cursor */
    public static Cursor CURRENT = DEFAULT;
    
    /** The hand cursor */
    public static Cursor HAND = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    
    /** The invisible cursor */
    public static Cursor INVISIBLE = fromImage(ImageTool.getEmptyImage(1, 1));
    
    /**
     * Creates a custom cursor from an image
     */
    public static Cursor fromImage(Image img){
        return Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), img.toString());
    }
    
    /**
     * Set's the game cursor
     */
    public static void setCursor(Cursor c){
        CURRENT = c;
    }

}
