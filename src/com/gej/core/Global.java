package com.gej.core;

import java.util.HashMap;

/**
 * This class contains static fields which can be called by any sprite, or the
 * game class itself.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class Global {

    // prevent instantiation
    private Global() {
    }

    /** The window title */
    public static String TITLE = "GEJ - The simplest GameEngine for Java";
    /** The window width */
    public static int WIDTH = 640;
    /** The window height */
    public static int HEIGHT = 480;
    /** The switch for pixel perfect collision detection */
    public static boolean USE_PIXELPERFECT_COLLISION = false;
    /**
     * If disabled, collision detection is only done for objects which are
     * visible in the view. This is disabled by default. You may want to enable
     * this feature for some games.
     */
    public static boolean ENABLE_COLLISION_DETECTION_FOR_ALL_OBJECTS = false;
    /** The property to show or hide cursor */
    public static boolean HIDE_CURSOR = false;
    /** The switch for full screen mode */
    public static boolean FULLSCREEN = false;
    /**
     * The speed of the game in steps for second. This is the speed with which
     * the game will be updated. You can experiment with change in this value to
     * see which speed aptly suites your game.
     */
    public static int STEPS_FOR_SECOND = 60;
    /** The FPS of the game */
    public static int FRAMES_PER_SECOND = 60;
    /**
     * Fits the drawn game to the window so that nothing in the view is escaped
     * out. This makes the display of text using the Java2D fonts to an uneasy
     * way
     */
    public static boolean STRETCH_TO_SCREEN = true;
    /** A map containing various properties */
    public static HashMap<String, Object> PROPERTIES = new HashMap<String, Object>();
    /** Variable containing the current GEJ version */
    public static final String GEJ_VERSION = "GEJ V0.8";

}
