package com.gej.object;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.gej.util.ImageTool;

/**
 * A tile is a type of object which just sit's there and doesn't do anything.
 * Often used to decorate maps or for fake wall's etc., Note that they cannot be
 * destroyed while the map is running and they are not animated too.
 * 
 * @author Sri Harsha Chilakapati
 */
public class Tile {

    // Private variables
    private Image img = null;
    private BufferedImage bimg = null;
    // The positions
    private int x = 0;
    private int y = 0;

    /**
     * Constructs a new tile with an image at a specified position
     * 
     * @param img The tile image
     * @param x The x-position
     * @param y The y-position
     */
    public Tile(Image img, int x, int y) {
        this.img = img;
        bimg = ImageTool.toBufferedImage(img);
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the image of this tile
     * 
     * @return The image of this tile
     */
    public Image getImage(){
        return img;
    }

    /**
     * Returns the buffered image of this tile
     * 
     * @return The image of this tile as a buffered image
     */
    public BufferedImage getBufferedImage(){
        return bimg;
    }

    /**
     * Returns the x-position of this tile.
     * 
     * @return The x-position of this tile
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the y-position of this tile.
     * 
     * @return The y-position of this tile
     */
    public int getY(){
        return y;
    }

}
