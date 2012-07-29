package com.gej.object.types;

import java.awt.Image;

import com.gej.graphics.Animation;
import com.gej.object.GObject;
import com.gej.util.ImageTool;

/**
 * A customized object to move along a direction specified by angle in degrees.
 * The angles are as follows:
 * 
 * <pre>
 * 0&deg;   : moving upwards
 * 90&deg;  : moving right or stationary
 * 180&deg; : moving down
 * 270&deg; : moving left
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class AngularGObject extends GObject {

    // The velocity of this object
    protected float velocity = 0;

    /**
     * Constructs an object which is invisible
     */
    public AngularGObject() {
        super(ImageTool.getEmptyImage(1, 1));
    }

    /**
     * Constructs an invisible object at a position x,y
     * 
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public AngularGObject(float x, float y) {
        this();
        setX(x);
        setY(y);
    }

    /**
     * Constructs an object with an image at a position x,y
     * 
     * @param img The image of the object
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public AngularGObject(Image img, float x, float y) {
        super(img);
        setX(x);
        setY(y);
    }

    /**
     * Constructs an object with an animation at a position x,y
     * 
     * @param anim The animation object
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public AngularGObject(Animation anim, float x, float y) {
        super(anim);
        setX(x);
        setY(y);
    }

    /**
     * Constructs an object with an animation object.
     * 
     * @param anim The animation used for this object
     */
    public AngularGObject(Animation anim) {
        super(anim);
    }

    /**
     * Constructs an object with an image object.
     * 
     * @param img The image used for this object
     */
    public AngularGObject(Image img) {
        super(img);
    }

    /**
     * Sets the velocity of this object. The velocity must be always positive.
     * If negative velocities are used, then the direction will be reversed.
     * 
     * @param vel The new velocity of this object.
     */
    public void setVelocity(float vel){
        velocity = vel;
        setDirection((int) direction);
    }

    /**
     * Gets you the current velocity of this object
     * 
     * @return the current velocity of this object
     */
    public float getVelocity(){
        return velocity;
    }

    /**
     * Sets the direction of this object
     * 
     * @param dir The new direction in degrees
     */
    public void setDirection(int dir){
        setVelocityX((float) (velocity * Math.sin(Math.toRadians(dir))));
        setVelocityY((float) (velocity * (-1) * Math.cos(Math.toRadians(dir))));
    }

}
