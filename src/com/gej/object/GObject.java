package com.gej.object;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import com.gej.core.Global;
import com.gej.core.Updateable;
import com.gej.graphics.Animation;
import com.gej.input.GInput;
import com.gej.input.GMouse;
import com.gej.util.GUtil;

/**
 * This class represents objects in a game. Any object must extend
 * this class. The events are automatically managed if this object
 * is dynamically loaded through a map loader. Else you have to
 * detect them on your own. This is an example object.
 * 
 * <pre>
 * public class MyObject extends GObject {
 *     
 *     public MyObject(float x, float y){
 *         super(Game.loadImage("&lt;image&gt;"));
 *         setX(x);
 *         setY(y);
 *     }
 *     
 *     public void update(long elapsedTime){
 *         // Update this object
 *     }
 *     
 *     public void collision(GObject other){
 *         // Another object has been collided
 *         if (other instanceof MyObject2){
 *             // Other is MyObject2
 *         }
 *     }
 *     
 * }
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class GObject implements Updateable {

	// The animation object
    private Animation anim;
    // The positions and velocities
    protected float x;
    protected float y;
    protected float dx;
    private float dy;
    
    // If this object is solid and alive
    private boolean solid = false;
    private boolean alive = true;

    /**
     * Constructs an object with an animation object.
     * @param anim The animation used for this object
     */
    public GObject(Animation anim) {
        this.anim = anim;
    }
    
    /**
     * Constructs an object with an image object.
     * @param img The image used for this object
     */
    public GObject(Image img){
    	this.anim = new Animation(new Image[]{img}, 100);
    }

    /**
     * Updates this object based on time
     */
    public void update(long elapsedTime) {}
    
    /**
     * Updates the object's animations
     * @param elapsedTime
     */
    public final void superUpdate(long elapsedTime){
    	update(elapsedTime);
    	anim.update(elapsedTime);
    }
    
    /**
     * Moves this object based on time.
     * @param elapsedTime The time elapsed in the current frame
     */
    public void move(long elapsedTime){
    	moveHorizontally(elapsedTime);
    	moveVertically(elapsedTime);
    }
    
    /**
     * Moves the object horizontally
     * @param elapsedTime The time elapsed in the current frame
     */
    public void moveHorizontally(long elapsedTime){
    	float nx = x + dx * elapsedTime;
    	if (check(nx, getY())){
    		x = nx;
    	}
    }
    
    /**
     * Moves the object vertically
     * @param elapsedTime The time elapsed in the current frame
     */
    public void moveVertically(long elapsedTime){
    	float ny = y + dy * elapsedTime;
    	if (check(getX(), ny)){
    		y = ny;
    	}
    }
    
    /**
     * Could be used to give limits to the object.
     * @param nx The new x-position
     * @param ny The new y-position
     * @return True if can move, else false.
     */
    public boolean check(float nx, float ny){
    	return true;
    }
    
    /**
     * Returns true if this object has been clicked by the mouse
     * @return True if mouse has been clicked on this object.
     */
    public boolean isClicked(){
    	return isClicked(GInput.MOUSE_BUTTON_1);
    }
    
    /**
     * Returns true if this object has been clicked by a mouse button
     * @param button The Mouse code of the button
     * @return True if clicked. Else false
     */
    public boolean isClicked(int button){
    	return (getBounds().contains(GMouse.MOUSE_X, GMouse.MOUSE_Y)) && (GMouse.isMouseClicked(button));
    }
    
    /**
     * Returns the boundaries of this object.
     * @return The boundaries of this object as a rectangle.
     */
    public Rectangle getBounds(){
    	return new Rectangle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight());
    }
    
    /**
     * Checks if this object is colliding the other object. You can enable
     * pixel-perfect collision detection by using
     * <pre>
     * Global.USE_PIXELPERFECT_COLLISION = true;
     * </pre>
     * @param other The other object
     * @return True if a collision has been found.
     */
    public boolean isCollidingWith(GObject other){
    	boolean bool = getBounds().intersects(other.getBounds());
    	if (bool && Global.USE_PIXELPERFECT_COLLISION){
    		bool = GUtil.isPixelPerfectCollision(x, y, getAnimation().getBufferedImage(), other.getX(), other.getY(), other.getAnimation().getBufferedImage());
    	}
    	return bool;
    }
    
    /**
     * Returns the state of this object.
     * @return True if alive. else false.
     */
    public boolean isAlive(){
    	return alive;
    }
    
    /**
     * Destroy's this object so that it won't receive any events
     * and will not render again.
     */
    public void destroy(){
    	alive = false;
    }
    
    /**
     * Checks if an object has collided this on top. Like in this example
     * <pre>
     * ---------
     * |       |
     * | Other |
     * |       |
     * -----+--+------
     *      |        |
     *      |  this  |
     *      |        |
     *      ----------
     * </pre>
     * @param other The other object
     * @return True if collision on top
     */
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
    
    /**
     * Checks if an object has collided this on bottom like in this example
     * <pre>
     * ----------
     * |        |
     * |  This  |
     * |        |
     * -----+---------+
     *      |         |
     *      |  Other  |
     *      |         |
     *      -----------
     * </pre>
     * @param other The other object
     * @return True if bottom wise collision
     */
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
    
    /**
     * Checks if an object has collided this from left like in this example.
     * <pre>
     * -----------
     * |         +-----------
     * |  Other  |          |
     * |         |   This   |
     * ----------|          |
     *           ------------
     * </pre>
     * @param other The other object
     * @return True if found a collision on the left
     */
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
    
    /**
     * Checks if an object has collided this from the right like in this example
     * <pre>
     * ----------
     * |        |
     * |  this  +----------
     * |        |         |
     * ---------+  Other  |
     *          |         |
     *          -----------
     * </pre>
     * @param other The other object.
     * @return True if found a collision on the right.
     */
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
    
    /**
     * Called by the map automatically if a collision event occurs.
     * @param other The object which has been collided.
     */
    public void collision(GObject other){}
    
    /**
     * Called by the map automatically if an object has been collided horizontally
     * @param other The object which has been collided
     */
    public void HorizontalCollision(GObject other){}
    
    /**
     * Called by the map automatically if an object has been collided vertically
     * @param other The object which has been collided
     */
    public void VerticalCollision(GObject other){}
    
    /**
     * Moves this object to a specified point with a specific speed.
     * Note that the velocity used is independent of vertical or
     * horizontal velocities of this object.
     * @param nx The new x-position
     * @param ny The new y-position
     * @param speed The speed with which to move
     * @param elapsedTime The time elapsed in the current frame.
     * @return True if the new point has been reached
     */
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
    
    /**
     * Moves this object to a specified point with a specific speed.
     * Note that the velocity used is independent of vertical or
     * horizontal velocities of this object.
     * @param nx The new x-position
     * @param ny The new y-position
     * @param speed The speed with which to move
     * @param elapsedTime The time elapsed in the current frame.
     * @return True if the new point has been reached
     */
    public boolean moveTo(int nx, int ny, float speed, long elapsedTime){
    	return moveTo((float)nx, (float)ny, speed, elapsedTime);
    }
    
    /**
     * Changes the velocities of this object in order to get a bounce
     * effect over the object.
     */
    public void bounce(){
    	boolean left = false;
    	boolean right = false;
    	boolean up = false;
    	boolean down = false;
    	if (dx<0){
    		left = true;
    	} else if (dx>0){
    		right = true;
    	}
    	if (dy<0){
    		up = true;
    	} else if (dy>0){
    		down = true;
    	}
    	if (left && up){
    		dx = -dx;
    	}
    	if (left && down){
    		dy = -dy;
    	}
    	if (right && up){
    		dx = -dx;
    	}
    	if (right && down){
    		dy = -dy;
    	}
    }
    
    /**
     * Moves this object to the next point which on next move would collide.
     * Works perfectly only in the pixel perfect collision detection mode.
     * This automatically resets the velocities.
     * @param other The other object
     */
    public void moveToContact(GObject other){
    	moveToContact(other, true, true);
    }
    
    /**
     * Moves this object to the next point which on next move would collide.
     * Works perfectly only in the pixel perfect collision detection mode.
     * This automatically resets the velocities.
     * @param other The other object
     * @param horizontal Should move horizontally?
     * @param vertical Should move vertically?
     */
    public void moveToContact(GObject other, boolean horizontal, boolean vertical){
    	if (horizontal){
    		if (isLeftCollision(other)){
    			setX(other.getX()+other.getWidth());
    			while(!isCollidingWith(other)){
    				setX(getX()-1);
    			}
    		} else if (isRightCollision(other)){
    			setX(other.getX()-getWidth());
    			while(!isCollidingWith(other)){
    				setX(getX()+1);
    			}
    		}
    		setVelocityX(0);
    	}
    	if (vertical){
    		if (isTopCollision(other)){
    			setY(other.getY()+other.getHeight());
    			while(!isCollidingWith(other)){
    				setY(getY()-1);
    			}
    		} else if (isBottomCollision(other)){
    			setY(other.getY()-getHeight());
    			while(!isCollidingWith(other)){
    				setY(getY()+1);
    			}
    		}
    		setVelocityY(0);
    	}
    }
      
    /**
     * Set's the solid state of this object.
     * @param value The solid value.
     */
    public void setSolid(boolean value){
    	solid = value;
    }
    
    /**
     * Checks the solid state of this object.
     * @return True if solid.
     */
    public boolean isSolid(){
    	return solid;
    }
    
    /**
     * Returns the current x-position of this object
     * @return The current x-position of this object
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the current y-position of this object
     * @return The current y-position of this object
     */
    public float getY() {
        return y;
    }
    
    /**
     * Set's the x-position of this object
     * @param x The new x-position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Set's the y-position of this object
     * @param y The new y-position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets you the width of this object
     * @return The width of this object in pixels
     */
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**
     * Get's you the height of this object
     * @return The height of this object in pixels
     */
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    /**
     * Gets you the horizontal velocity of this object
     * @return The horizontal velocity
     */
    public float getVelocityX() {
        return dx;
    }

    /**
     * Gets you the vertical velocity of this object
     * @return The vertical velocity
     */
    public float getVelocityY() {
        return dy;
    }

    /**
     * Sets the horizontal velocity of this object
     * @param dx The new horizontal velocity
     */
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical velocity of this object
     * @param dy The new vertical velocity
     */
    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    /**
     * Returns the image of this object
     * @return The image which is used to represent this object
     */
    public Image getImage() {
    	if (isAlive()){
    		return anim.getImage();
    	} else {
    		return null;
    	}
    }
    
    /**
     * Returns the animation used by this object
     * @return The current animation
     */
    public Animation getAnimation(){
    	return anim;
    }
    
    /**
     * Sets the animation of this object
     * @param anim The new animation
     */
    public void setAnimation(Animation anim){
    	this.anim = anim;
    }
    
    /**
     * Sets the current image of this object
     * @param img The new image
     */
    public void setImage(Image img){
    	this.anim = new Animation(new Image[]{img}, 150);
    }
    
    /**
     * Render's this object onto the graphics context.
     * @param g The graphics context.
     */
    public void render(Graphics2D g){
    	g.drawImage(getImage(), Math.round(x), Math.round(y), null);
    }
    
}
