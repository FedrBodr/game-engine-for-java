package com.gej.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This class can be used to convert images.
 * Note that all the methods of this class
 * are declared as static.
 * 
 * @author Sri Harsha Chilakapati
 */
public class ImageTool {

	/**
	 * Converts a given Image into a BufferedImage
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img){
		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		// Return the buffered image
		return bimage;
	}
		
	public static Image[] splitImage(Image img, int rows, int cols){
		// Create the arrays
		Image[] images = new Image[rows*cols];
		BufferedImage[] bimages = new BufferedImage[rows*cols];
		// The count
		int count = 0;
		// Width and height of each sub image
		int w = img.getWidth(null)/cols;
		int h = img.getHeight(null)/rows;
		// Start splitting
		for (int x = 0; x < rows; x++) {  
	        for (int y = 0; y < cols; y++) {  
	            bimages[count] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	            // Get the Graphics2D object of the split part of the image
	            Graphics2D g = bimages[count++].createGraphics();  
	            // Draw only the required portion of the main image on to the split image
	            g.drawImage(img, 0, 0, w, h, w * y, h * x, w * y + w, h * x + h, null);
	            // Now Dispose the Graphics2D class
	            g.dispose();  
	        }  
	    }
		// Now convert the images
		for (int i=0; i<bimages.length; i++){
			images[i] = toImage(bimages[i]);
		}
		return images;
	}
	
	/**
	 * Converts a given BufferedImage into an Image
	 * @param bimage The BufferedImage to be converted
	 * @return The converted Image
	 */
	public static Image toImage(BufferedImage bimage){
		// Casting is enough to convert from BufferedImage to Image
		Image img = (Image)bimage;
		return img;
	}
	
	/**
	 * Resizes a given image to given width and height
	 * @param img The image to be resized
	 * @param width The new width
	 * @param height The new height
	 * @return The resized image
	 */
	public static Image resize(Image img, int width, int height){
		// Create a null image
		Image image = null;
		// Resize into a BufferedImage
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimg.createGraphics();
		bGr.drawImage(img, 0, 0, width, height, null);
		bGr.dispose();
		// Convert to Image and return it
		image = toImage(bimg);
		return image;
	}
		
	/**
	 * Creates a tiled image with an image upto given width and height 
	 * @param img The source image
	 * @param width The width of image to be created
	 * @param height The height of the image to be created
	 * @return The created image
	 */
	public static Image createTiledImage(Image img, int width, int height){
		// Create a null image
		Image image = null;
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// The width and height of the given image
		int imageWidth = img.getWidth(null);
		int imageHeight = img.getHeight(null);
		// Start the counting
		int numX = (width / imageWidth) + 2;
		int numY = (height/ imageHeight) + 2;
		// Create the graphics context
		Graphics2D bGr = bimg.createGraphics();
		for (int y=0; y<numY; y++){
			for (int x=0; x<numX; x++){
				bGr.drawImage(img, x*imageWidth, y*imageHeight, null);
			}
		}
		// Convert and return the image
		image = toImage(bimg);
		return image;
	}
		
}
