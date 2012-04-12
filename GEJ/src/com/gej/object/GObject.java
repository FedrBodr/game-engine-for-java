package com.gej.object;

import java.awt.Image;
import java.awt.Rectangle;

import com.gej.graphics.Animation;

public class GObject {

    private Animation anim;
    protected float x;
    protected float y;
    protected float dx;
    private float dy;

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
    	return getBounds().intersects(other.getBounds());
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
    
    public void bounce(GObject other){
    	if (isTopCollision(other)||isBottomCollision(other)){
    		dy = -dy;
    	}
    	if (isLeftCollision(other)||isRightCollision(other)){
    		dx = -dx;
    	}
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
        return anim.getImage();
    }
}

