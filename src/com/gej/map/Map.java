package com.gej.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import com.gej.core.Global;
import com.gej.object.GObject;
import com.gej.object.Tile;
import com.gej.util.GUtil;

/**
 * This class represents a map (tile-based of course!) which is currently
 * playing the game. You could use the MapManager class to control the order of
 * maps. Note that this class is completely static. A map requires MapInfo
 * objects if you does't want to load from file ( so nobody can edit your levels
 * )
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class Map {

    // The map data (used for debugging)
    private static char[][] mapdata = null;
    // The array of tiles (used to decorate the map)
    private static Tile[][] _tiles = null;

    /** The tile-size of this map */
    public static int TILE_SIZE = 0;
    // Map's width and height (in tiles)
    private static int MAP_WIDTH = 0;
    private static int MAP_HEIGHT = 0;

    // Collection of all the game objects and tiles
    private static ArrayList<GObject> objects = null;

    // Layering data
    private static HashMap<Integer, MapLayer> layers = null;

    private static int maxDepth = 0;

    // prevent instantiation
    private Map() {
    }

    /**
     * Initializes the map. Called by the game class.
     */
    public static void initMap(){
        objects = new ArrayList<GObject>();
        layers = new HashMap<Integer, MapLayer>();
    }

    /**
     * Returns a tile number containing the following pixel
     * 
     * @param px The pixel's position
     * @return The tile-position
     */
    public static float toTiles(float px){
        return TILE_SIZE / px;
    }

    /**
     * Returns the first pixel of the tile
     * 
     * @param t The tile number
     * @return The pixel position
     */
    public static float toPixels(float t){
        return TILE_SIZE * t;
    }

    /**
     * Returns the bounds of tile at a specified position
     * 
     * @param x The x-position of the tile ( in tiles )
     * @param y The y-position of the tile ( in tiles )
     * @return A rectangle with the bounds
     */
    public static Rectangle getTileBounds(int x, int y){
        return new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Returns the character in the map file at the specified tile position of
     * the object. ( Note that it is only used for debugging ).
     * 
     * @param x The x-position of the tile
     * @param y The y-position of the tile
     * @return The character represented by the tile
     */
    public static char getTileAt(int x, int y){
        return mapdata[x][y];
    }

    /**
     * Loads the map from a MapInfo object.
     * 
     * @param info The object containing information on the map
     */
    public static void loadMap(MapInfo info){
        String[] lines = info.MapData.split("\n");
        TILE_SIZE = info.tileSize;
        loadMap(lines, info.loader);
    }

    /**
     * Loads a map from an array of lines by using the MapLoader
     * 
     * @param lines The array of string containing the lines
     * @param loader The MapLoader to be used
     */
    public static void loadMap(String[] lines, MapLoader loader){
        // clear objects
        clearObjects();
        // Reset the map view
        MapView.OffSetX = 0;
        MapView.OffSetY = 0;
        // Reset the map's height and width
        MAP_WIDTH = 0;
        MAP_HEIGHT = 0;
        // Reset layering data
        maxDepth = 0;
        // Try parsing the file
        try {
            for (int i = 0; i < lines.length; i++) {
                MAP_HEIGHT++;
                MAP_WIDTH = Math.max(MAP_WIDTH, lines[i].length());
            }
            mapdata = new char[MAP_WIDTH][MAP_HEIGHT];
            _tiles = new Tile[MAP_WIDTH][MAP_HEIGHT];
            // Iterate and add all the objects and tiles
            for (int i = 0; i < lines.length; i++) {
                for (int j = 0; j < lines[i].length(); j++) {
                    mapdata[j][i] = lines[i].charAt(j);
                    GObject obj = loader.getObject(mapdata[j][i], j * TILE_SIZE, i
                            * TILE_SIZE);
                    if (obj != null) {
                        addObject(obj);
                    }
                    _tiles[j][i] = loader.getTile(mapdata[j][i], j * TILE_SIZE, i
                            * TILE_SIZE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a map from a file in the jar file.
     * 
     * @param tileSize The tile-size of the map
     * @param mapname The file-name of the map
     * @param loader The MapLoader
     */
    public static void loadMap(int tileSize, String mapname, MapLoader loader){
        TILE_SIZE = tileSize;
        String[] lines = GUtil.loadLinesFromFile(mapname);
        loadMap(lines, loader);
    }

    /**
     * Clears all the objects from the map. Destroy's them.
     */
    public static void clearObjects(){
        objects.clear();
        layers.clear();
    }

    /**
     * Get the colliding object at a specific position and width and height
     * 
     * @param x The x-position
     * @param y The y-position
     * @param width The width
     * @param height The height
     * @return The colliding GObject. Returns null if nothing found
     */
    public static GObject getCollidingObject(float x, float y, int width, int height){
        GObject obj = null;
        Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), width, height);
        for (int i = 0; i < objects.size(); i++) {
            GObject object = objects.get(i);
            if (bounds.intersects(object.getBounds())) {
                obj = object;
            }
        }
        return obj;
    }

    /**
     * Checks if a specific position is collision free in the map.
     * 
     * @param x The x-position of the object
     * @param y The y-position of the object
     * @param solid Whether to check only for solid object
     * @param object The object ( uses image for pixel-perfect collision
     *            detection )
     * @return True if no-collision and false if it collides.
     */
    public static boolean isObjectCollisionFree(float x, float y, boolean solid, GObject object){
        boolean bool = true;
        Rectangle bounds = new Rectangle(Math.round(x), Math.round(y), object.getWidth(), object.getHeight());
        for (int i = 0; i < objects.size(); i++) {
            GObject obj = objects.get(i);
            if (object != obj && (obj.isSolid() == solid)) {
                if (bounds.intersects(obj.getBounds())) {
                    if (obj.isAlive()) {
                        bool = false;
                    }
                    if (bool && Global.USE_PIXELPERFECT_COLLISION) {
                        bool = GUtil.isPixelPerfectCollision(x, y, object.getAnimation().getBufferedImage(), obj.getX(), obj.getY(), obj.getAnimation().getBufferedImage());
                        if (bool) {
                            bool = false;
                        }
                    }
                }
            }
        }
        return bool;
    }

    /**
     * Checks if a specific position is collision free in the map.
     * 
     * @param x The x-position of the object
     * @param y The y-position of the object
     * @param object The object ( uses image for pixel-perfect collision
     *            detection )
     * @return True if no-collision and false if it collides.
     */
    public static boolean isObjectCollisionFree(float x, float y, GObject object){
        return (isObjectCollisionFree(x, y, true, object) || isObjectCollisionFree(x, y, false, object));
    }

    /**
     * Returns a list of objects in the current map.
     * 
     * @return The ArrayList containing all the objects
     */
    public static ArrayList<GObject> getObjects(){
        return objects;
    }

    /**
     * Prints the map to the console. Used to debug while loading maps
     */
    public static void printMap(){
        System.out.println(asString());
    }

    /**
     * Returns the current map as a string. Used to debug while loading maps
     * 
     * @return The string representation of this map
     */
    public static String asString(){
        String mp = "";
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                mp += mapdata[x][y] + " ";
            }
            mp += "\n";
        }
        return mp;
    }

    /**
     * Renders the map on to the graphics context
     * 
     * @param g The graphics context
     */
    public static void renderMap(Graphics2D g){
        renderMap(g, 0, 0);
    }

    /**
     * Renders the map at a specific position. visibleRect is the rectangle
     * passed so that only the visible objects are drawn.
     * 
     * @param g The graphics context
     * @param x The x-position
     * @param y The y-position
     */
    public static void renderMap(Graphics2D g, int x, int y){
        try {
            // Draw the layers
            for (int i = maxDepth; i >= 0; i--) {
                layers.get(i).render(g, x, y);
            }
            // Draw the tiles
            for (int my = 0; my < MAP_HEIGHT; my++) {
                for (int mx = 0; mx < MAP_WIDTH; mx++) {
                    Tile tile = _tiles[mx][my];
                    if (tile != null) {
                        if (MapView.isVisible(tile)) {
                            g.drawImage(tile.getImage(), tile.getX() + x, tile.getY()
                                    + y, null);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Updates all the objects contained in the map.
     * 
     * @param elapsedTime The time elapsed in the current frame.
     */
    public static void updateObjects(long elapsedTime){
        for (int i = 0; i < objects.size(); i++) {
            GObject obj = objects.get(i);
            if (obj.isAlive()) {
                obj.superUpdate(elapsedTime);
                obj.moveHorizontally();
                checkCollisions(obj, true, false);
                obj.moveVertically();
                checkCollisions(obj, false, true);
                checkCollisions(obj, false, false);
            } else {
                objects.remove(i);
            }
        }
    }

    /*
     * Checks for collisions for an object. Helper method.
     */
    private static final void checkCollisions(GObject obj, boolean hor, boolean vert){
        if (MapView.isVisible(obj)
                || Global.ENABLE_COLLISION_DETECTION_FOR_ALL_OBJECTS) {
            for (int i = 0; i < objects.size(); i++) {
                try {
                    GObject other = objects.get(i);
                    if (other.isAlive()) {
                        if (other.isCollidingWith(obj)) {
                            if (!hor && !vert) {
                                obj.collision(other);
                                return;
                            }
                            if (!Global.MOVE_INTO_SOLID_OBJECTS
                                    && other.isSolid()) {
                                // Move the object back
                                if (hor) {
                                    if (obj.getVelocityX() > 0) {
                                        obj.setX(other.getX() - obj.getWidth());
                                    } else {
                                        obj.setX(other.getX()
                                                + other.getWidth());
                                    }
                                    obj.setVelocityX(0);
                                    return;
                                }
                                if (vert) {
                                    if (obj.getVelocityY() > 0) {
                                        obj.setY(other.getY() - obj.getHeight());
                                    } else {
                                        obj.setY(other.getY()
                                                + other.getHeight());
                                    }
                                    obj.setVelocityY(0);
                                    return;
                                }
                            }
                            return;
                        }
                    } else {
                        objects.remove(i);
                    }
                } catch (Exception e) {
                    if (!(e instanceof NullPointerException)) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Returns the width of this map.
     * 
     * @return The width of this map ( in pixels )
     */
    public static int getWidth(){
        return MAP_WIDTH * TILE_SIZE;
    }

    /**
     * Returns the height of this map
     * 
     * @return The height of this map ( in pixels )
     */
    public static int getHeight(){
        return MAP_HEIGHT * TILE_SIZE;
    }

    /**
     * Removes all objects of a class from this map. Useful to destroy all bombs
     * etc., Example:
     * 
     * <pre>
     * Map.removeObjectsOfType(Bomb.class);
     * </pre>
     * 
     * @param c The class of the object
     */
    public static void removeObjectsOfType(Class<?> c){
        for (int i = 0; i < objects.size(); i++) {
            GObject obj = objects.get(i);
            if (c.isInstance(obj)) {
                objects.remove(i);
            }
        }
    }

    /**
     * Removes an object from the map
     * 
     * @param obj The object to be removed
     */
    public static void removeObject(GObject obj){
        objects.remove(obj);
    }

    /**
     * Adds a new object to the map
     * 
     * @param obj The new object
     */
    public static void addObject(GObject obj){
        if (obj != null) {
            objects.add(obj);
            maxDepth = Math.max(maxDepth, obj.getDepth());
            if (layers.containsKey(maxDepth)) {
                layers.get(maxDepth).addObject(obj);
            } else {
                MapLayer layer = new MapLayer();
                layer.addObject(obj);
                layers.put(maxDepth, layer);
            }
        }
    }

    /**
     * Checks if an object is aligned to the grid
     * 
     * @param obj The object to be checked
     * @return True if the object is positioned exactly at the left corner of
     *         the grid
     */
    public static boolean isAlignedToGrid(GObject obj){
        return isAlignedToGrid(obj.getX(), obj.getY());
    }

    /**
     * Checks if a position is aligned to the grid
     * 
     * @param x The x-position
     * @param y The y-position
     * @return True if the point is left corner of any cell in the grid.
     */
    public static boolean isAlignedToGrid(float x, float y){
        return (x % TILE_SIZE == 0) && (y % TILE_SIZE == 0);
    }

}
