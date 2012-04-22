package com.gej.core;

import java.util.ArrayList;

/**
 * This class contains static fields which can be called by any
 * sprite, or the game class itself.
 * 
 * @author Sri Harsha Chilakapati
 */
public class Global {
	
	/** The window title */
	public static String  TITLE                      = "GEJ - The simplest GameEngine for Java";
	/** The window width */
	public static int     WIDTH                      = 640;
	/** The window height */
	public static int     HEIGHT                     = 480;
	/** The switch for pixel perfect collision detection */
	public static boolean USE_PIXELPERFECT_COLLISION = false;
		
	public static boolean FULLSCREEN                 = false;
	
	public static int     FRAMES_PER_SECOND          = 100;
	
	public static ArrayList<Updateable> UPDATEABLES  = new ArrayList<Updateable>();
	
}
