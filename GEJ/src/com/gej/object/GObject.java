package com.gej.object;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.gej.core.Global;
import com.gej.graphics.Animation;
import com.gej.graphics.ImageTool;

public class GObject {

    private Animation anim;
    protected float x;
    protected float y;
    protected float dx;
    private float dy;
    
    private boolean solid = false;
    private boolean alive = true;

    public GObject(Animation anim) {
        this.anim = anim;
    }
    
    public GObject(Image img){
    	this.anim = new Animation(new Image[]{img}, 100);
    }

    public void update(long elapsedTime) {
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        anim.update(elapsedTime);
    }
    
    public Rectangle getBounds(){
    	return new Rectangle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight());
    }
    
    public boolean isCollidingWith(GObject other){
    	boolean bool = getBounds().intersects(other.getBounds());
    	if (bool && Global.USE_PIXELPERFECT_COLLISION){
    		bool = isPixelPerfectCollision(this, Math.round(getX()), Math.round(getY()), other);
    	}
    	return bool;
    }
    
    public boolean isAlive(){
    	return alive;
    }
    
    public void destroy(){
    	alive = false;
    }
    
    public static boolean isPixelPerfectCollision(GObject this_obj, int thisx, int thisy, GObject other){
		boolean bool = false;
		// Get their images
		BufferedImage image1 = ImageTool.toBufferedImage(this_obj.getImage());
		BufferedImage image2 = ImageTool.toBufferedImage(other.getImage());
		// Initialize
		double width1 = thisx + image1.getWidth()-2,
		       height1 = this_obj.y + image1.getHeight()-2,
			   width2 = other.x + image2.getWidth()-2,
		       height2 = other.y + image2.getHeight()-2;
		   int xstart = (int) Math.max(thisx, other.x),
			   ystart = (int) Math.max(this_obj.y, other.y),
			   xend   = (int) Math.min(width1, width2),
			   yend   = (int) Math.min(height1, height2);
	    // Bounding box
	    int totx = Math.abs(xend - xstart);
	    int toty = Math.abs(yend - ystart);
	    // Check the pixels
        for (int i=1; i<toty-1; i++){
    	    int ny = Math.abs(ystart - (int) this_obj.y) + i;
	        int ny1 = Math.abs(ystart - (int) other.y) + i;
		    for (int j=1; j<totx-1; j++) {
			    int nx = Math.abs(xstart - (int) thisx) + j;
		        int nx1 = Math.abs(xstart - (int) other.x) + j;
		        try {
		            if (((image1.getRGB(nx,ny) & 0xFF000000) != 0x00) && ((image2.getRGB(nx1,ny1) & 0xFF000000) != 0x00)) {
		                // They are colliding
		                bool = true;
			        }
 			    } catch (Exception e) {}
		    }
	    }
	    return bool;
	}
    
    public boolean isTopCollision(GObject other){
    	boolean bool = isCollidingWith(other);
    	if (bool){
    		if (getY()>=other.getY()){
    			bool = true;
    		} else {
    			bool = false;
    		}
    	}
    	return bool;
    }
    
    public boolean isBottomCollision(GObject other){
    	boolean bool = isCollidingWith(other);
    	if (bool){
    		if (getY()<=other.getY()){
    			bool = true;
    		} else {
    			bool = false;
    		}
    	}
    	return bool;
    }
    
    public boolean isLeftCollision(GObject other){
    	boolean bool = isCollidingWith(other);
    	if (bool){
    		if (getX()>=other.getX()){
    			bool = true;
    		} else {
    			bool = false;
    		}
    	}
    	return bool;
    }
    
    public boolean isRightCollision(GObject other){
    	boolean bool = isCollidingWith(other);
    	if (bool){
    		if (getX()<=other.getX()){
    			bool = true;
    		} else {
    			bool = false;
    		}
    	}
    	return bool;
    }
    
    public void collision(GObject other){}
    
    public void bounce(GObject other){
    	if (isTopCollision(other)||isBottomCollision(other)){
    		dy = -dy;
    	}
    	if (isLeftCollision(other)||isRightCollision(other)){
    		dx = -dx;
    	}
    }
      
    public void setSolid(boolean value){
    	solid = value;
    }
    
    public boolean isSolid(){
    	return solid;
    }
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    public float getVelocityX() {
        return dx;
    }

    public float getVelocityY() {
        return dy;
    }

    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    public Image getImage() {
    	if (isAlive()){
    		return anim.getImage();
    	} else {
    		return null;
    	}
    }
    
    public Animation getAnimation(){
    	return anim;
    }
    
}
