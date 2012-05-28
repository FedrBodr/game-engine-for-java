package com.gej.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Some of the utilities needed by the engine. Note that all
 * the utility methods are static. This class is necessary
 * for the engine to run. The class is made abstract to prevent
 * the user from instantiation.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class GUtil {
	
	/**
	 * Check's for pixel-perfect collision between two buffered images.
	 * @param x1 The x-position of the first object
	 * @param y1 The y-position of the first object
	 * @param image1 The buffered image of first object
	 * @param x2 The x-position of the second object
	 * @param y2 The y-position of the second object
	 * @param image2 The buffered image of second object
	 * @return True if a collision has been found
	 */
	public static boolean isPixelPerfectCollision(float x1, float y1, BufferedImage image1, float x2, float y2, BufferedImage image2){
		boolean bool = false;
		double width1 = x1 + image1.getWidth() -1,
		       height1 = y1 + image1.getHeight() -1,
		       width2 = x2 + image2.getWidth() -1,
		       height2 = y2 + image2.getHeight() -1;
		int xstart = (int) Math.max(x1, x2),
		    ystart = (int) Math.max(y1, y2),
		    xend   = (int) Math.min(width1, width2),
		    yend   = (int) Math.min(height1, height2);
		int toty = Math.abs(yend - ystart);
		int totx = Math.abs(xend - xstart);
		    for (int y=1; y<toty-1; y++){
		    	int ny = Math.abs(ystart - (int) y1) + y;
		    	int ny1 = Math.abs(ystart - (int) y2) + y;
		    	for (int x=1; x<totx-1; x++){
		    		int nx = Math.abs(xstart - (int) x1) + x;
		    		int nx1 = Math.abs(xstart - (int) x2) + x;
		    		try {
		    			if (((image1.getRGB(nx,ny) & 0xFF000000) != 0x00) && ((image2.getRGB(nx1,ny1) & 0xFF000000) != 0x00)) {
		    				bool = true;
		    			}
		    		} catch (Exception e) { }
		    	}
		    }
		return bool;
	}
	
	/**
	 * Runs a task in a separate thread. The task here is a Runnable object.
	 * @param r The Runnable object.
	 */
	public static void runInSeperateThread(Runnable r){
		Thread th = new Thread(r);
		th.start();
	}
	
	/**
	 * Loads a file from the root of the jar-file into an array of strings
	 * @param FileName The name of the file.
	 * @return The String[] array containing all the lines in the file.
	 */
	public static String[] loadLinesFromFile(String FileName){
		String[] strs = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(GUtil.class.getClassLoader().getResourceAsStream(FileName)));
		ArrayList<String> lines = new ArrayList<String>();
		try {
			String line = reader.readLine();
			while (line!=null){
				lines.add(line);
				line = reader.readLine();
			}
			strs = new String[lines.size()];
			for (int i=0; i<lines.size(); i++){
				strs[i] = lines.get(i);
			}
		} catch (Exception e){}		
		return strs;
	}
		  
}
