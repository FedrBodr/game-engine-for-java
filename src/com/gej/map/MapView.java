package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.gej.core.Global;
import com.gej.object.GObject;
import com.gej.object.Tile;

/**
 * Implements the view of the map so that a map can scroll indefinitely. The offsets are calculated
 * automatically when the static method follow() is called by the object in it's respective update()
 * method.
 * @author Sri Harsha Chilakapati
 */
public class MapView {
    
    /** The x-Offset */
    public static int OffSetX = 0;
    /** The y-Offset */
    public static int OffSetY = 0;
    
    /**
     * Calculates the offset values so that an object will be centered in the view. Instead of
     * rendering the map, use the MapView's render method so that only the visible portion of the
     * map is drawn.
     * @param obj The object which is to be centered.
     */
    public static void follow(GObject obj){
        OffSetX = Math.round(Global.WIDTH / 2 - obj.getX() - Map.TILE_SIZE);
        OffSetX = Math.min(OffSetX, 0);
        OffSetX = Math.max(OffSetX, Global.WIDTH - Map.getWidth());
        ////////////////////////////////////////////////////////////////////
        OffSetY = Math.round(Global.HEIGHT / 2 - obj.getY() - Map.TILE_SIZE);
        OffSetY = Math.min(OffSetY, 0);
        OffSetY = Math.max(OffSetY, Global.HEIGHT - Map.getHeight());
    }
    
    /**
     * Returns a rectangle which is the visible portion of the game. Note that the offset values are
     * not included in the objects.
     * @return The visible rectangle.
     */
    public static Rectangle getVisibleRect(){
        return new Rectangle(0, 0, Global.WIDTH, Global.HEIGHT);
    }
    
    /**
     * Render's the map with this view. Only the visible objects and tiles are rendered.
     * @param g The Graphics context
     */
    public static void render(Graphics2D g){
        try{
            Map.renderMap(g, OffSetX, OffSetY, getVisibleRect());
        } catch (NullPointerException e){
        }
    }
    
    /**
     * Render's an object with respect to this view.
     * @param g The graphics context
     * @param obj The object to be rendered
     */
    public static void renderObject(Graphics2D g, GObject obj){
        try{
            g.drawImage(obj.getImage(), Math.round(obj.getX()) + OffSetX, Math.round(obj.getY()) + OffSetY, null);
        } catch (NullPointerException e){
        }
    }
    
    /**
     * Checks if an object is visible or not according to the view.
     * @param obj The object to be checked
     * @return True if visible, else false.
     */
    public static boolean isVisible(GObject obj){
        int x1 = 0;
        int y1 = 0;
        int x2 = (int)obj.getX() + OffSetX;
        int y2 = (int)obj.getY() + OffSetY;
        if (x1 < x2 + obj.getWidth()){
            if (x2 < x1 + Global.WIDTH){
                if (y1 < y2 + obj.getHeight()){
                    if (y2 < y1 + Global.HEIGHT){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if a tile is visible or not according to the view.
     * @param tile The tile to be checked
     * @return True if visible, else false.
     */
    public static boolean isVisible(Tile tile){
        int x1 = 0;
        int y1 = 0;
        int x2 = (int)tile.getX() + OffSetX;
        int y2 = (int)tile.getY() + OffSetY;
        if (x1 < x2 + tile.getImage().getWidth(null)){
            if (x2 < x1 + Global.WIDTH){
                if (y1 < y2 + tile.getImage().getHeight(null)){
                    if (y2 < y1 + Global.HEIGHT){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
