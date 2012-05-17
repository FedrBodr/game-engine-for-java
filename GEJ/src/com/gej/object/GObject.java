package com.gej.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import com.gej.core.Global;
import com.gej.core.Updateable;
import com.gej.graphics.Animation;
import com.gej.util.GUtil;

public class GObject implements Updateable {

    private Animation anim;
    protected float x;
    protected float y;
    protected float dx;
    private float dy;
    
    private boolean solid = false;
    private boolean alive = true;

    public GObject(Animation anim) {
    	Global.UPDATEABLES.add(this);
        this.anim = anim;
    }
    
    public GObject(Image img){
    	Global.UPDATEABLES.add(this);
    	this.anim = new Animation(new Image[]{img}, 100);
    }

    public void update(long elapsedTime) {
        float nx = x + dx * elapsedTime;
        float ny = y + dy * elapsedTime;
        if (check(nx, ny)){
        	x = nx;   y = ny;
        }
        anim.update(elapsedTime);
    }
    
    public boolean check(float nx, float ny){
    	return true;
    }
    
    public Rectangle getBounds(){
    	return new Rectangle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight());
    }
    
    public boolean isCollidingWith(GObject other){
    	boolean bool = getBounds().intersects(other.getBounds());
    	if (bool && Global.USE_PIXELPERFECT_COLLISION){
    		bool = GUtil.isPixelPerfectCollision(x, y, getAnimation().getBufferedImage(), other.getX(), other.getY(), other.getAnimation().getBufferedImage());
    	}
    	return bool;
    }
    
    public boolean isAlive(){
    	return alive;
    }
    
    public void destroy(){
    	alive = false;
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
    
    public boolean moveTo(float nx, float ny, float speed, long elapsedTime){
    	boolean _x = false;
    	boolean _y = false;
    	if (x>nx){
    		// We should move left
    		x -= speed * elapsedTime;
    	} else if (x<nx){
    		// We should move right
    		x += speed * elapsedTime;
    	} else {
    		_x = true;
    	}
    	if (y>ny){
    		// We should move up
    		y -= speed * elapsedTime;
    	} else if (y<ny){
    		// We should move down
    		y += speed * elapsedTime;
    	} else {
    		_y = true;
    	}
    	return (_x && _y);
    }
    
    public boolean moveTo(int nx, int ny, float speed, long elapsedTime){
    	return moveTo((float)nx, (float)ny, speed, elapsedTime);
    }
    
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
    
    public void setAnimation(Animation anim){
    	this.anim = anim;
    }
    
    public void setImage(Image img){
    	this.anim = new Animation(new Image[]{img}, 150);
    }
    
    public void render(Graphics2D g){
    	g.drawImage(getImage(), Math.round(x), Math.round(y), null);
    }
    
}
