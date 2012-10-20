package com.gej.graphics;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import com.gej.util.ImageTool;

public class GCursor {
    
    public static Cursor DEFAULT = Cursor.getDefaultCursor();
    
    public static Cursor HAND = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    
    public static Cursor CROSSHAIR = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    
    public static Cursor INVISIBLE = Toolkit.getDefaultToolkit().createCustomCursor(ImageTool.getEmptyImage(1, 1), new Point(0, 0), "Invisible Cursor");
    
    public static Cursor MOVE_CURSOR = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    
    public static Cursor FROM_IMAGE(Image img){
        return Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), img.toString());
    }

}
