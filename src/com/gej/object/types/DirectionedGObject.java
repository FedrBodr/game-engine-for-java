package com.gej.object.types;

import java.awt.Image;

import com.gej.graphics.Animation;
import com.gej.object.GObject;
import com.gej.util.Direction;
import com.gej.util.ImageTool;

/**
 * A customized object to move along directions. Useful if the object moves in
 * directions.
 * 
 * @author Sri Harsha Chilakapati
 */
public class DirectionedGObject extends GObject {

    // The directions of this object
    protected Direction verticalDirection = Direction.DIRECTION_NONE;
    protected Direction horizontalDirection = Direction.DIRECTION_NONE;

    protected float velocity = 0;

    /**
     * Constructs an object which is invisible
     */
    public DirectionedGObject() {
        super(ImageTool.getEmptyImage(1, 1));
    }

    /**
     * Constructs an invisible object at a position x,y
     * 
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public DirectionedGObject(float x, float y) {
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
    public DirectionedGObject(Image img, float x, float y) {
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
    public DirectionedGObject(Animation anim, float x, float y) {
        super(anim);
        setX(x);
        setY(y);
    }

    /**
     * Constructs an object with an animation object.
     * 
     * @param anim The animation used for this object
     */
    public DirectionedGObject(Animation anim) {
        super(anim);
    }

    /**
     * Constructs an object with an image object.
     * 
     * @param img The image used for this object
     */
    public DirectionedGObject(Image img) {
        super(new Animation(new Image[] { img }, 100));
    }

    /**
     * Sets the vertical direction of this object
     * 
     * @param dir The new direction
     */
    public void setVerticalDirection(Direction dir){
        verticalDirection = dir;
        updateVelocities();
    }

    /**
     * Sets the horizontal direction of this object
     * 
     * @param dir The new direction
     */
    public void setHorizontalDirection(Direction dir){
        horizontalDirection = dir;
        updateVelocities();
    }

    /**
     * Sets the direction of this object. Use this method only to make movement
     * in only one direction. If the direction is horizontal, the vertical
     * direction is none. Else the horizontal direction is none.
     * 
     * @param dir The new direction
     */
    public void setDirection(Direction dir){
        switch (dir) {
            case DIRECTION_UP:
                setHorizontalDirection(Direction.DIRECTION_NONE);
                setVerticalDirection(dir);
                break;
            case DIRECTION_DOWN:
                setHorizontalDirection(Direction.DIRECTION_NONE);
                setVerticalDirection(dir);
                break;
            case DIRECTION_LEFT:
                setVerticalDirection(Direction.DIRECTION_NONE);
                setHorizontalDirection(dir);
                break;
            case DIRECTION_RIGHT:
                setVerticalDirection(Direction.DIRECTION_NONE);
                setHorizontalDirection(dir);
                break;
            case DIRECTION_NONE:
                setHorizontalDirection(Direction.DIRECTION_NONE);
                setVerticalDirection(Direction.DIRECTION_NONE);
        }
    }

    /**
     * Returns the vertical direction of this object
     * 
     * @return the vertical direction of this object
     */
    public Direction getVerticalDirection(){
        return verticalDirection;
    }

    /**
     * Returns the horizontal direction of this object
     * 
     * @return the horizontal direction of this object
     */
    public Direction getHorizontalDirection(){
        return horizontalDirection;
    }

    /**
     * Updates the velocities of this object based on the vertical and
     * horizontal directions.
     */
    public void updateVelocities(){
        switch (verticalDirection) {
            case DIRECTION_UP:
                setVelocityY(-velocity);
                break;
            case DIRECTION_DOWN:
                setVelocityY(velocity);
                break;
            case DIRECTION_NONE:
                setVelocityY(0);
                break;
        }
        switch (horizontalDirection) {
            case DIRECTION_LEFT:
                setVelocityX(-velocity);
                break;
            case DIRECTION_RIGHT:
                setVelocityX(velocity);
                break;
            case DIRECTION_NONE:
                setVelocityX(0);
                break;
        }
    }

    /**
     * Sets the velocity of this object. The velocity must be always positive.
     * If negative velocities are used, then the direction will be reversed.
     * 
     * @param vel The new velocity of this object.
     */
    public void setVelocity(float vel){
        velocity = vel;
        updateVelocities();
    }

    /**
     * Gets you the current velocity of this object
     * 
     * @return the current velocity of this object
     */
    public float getVelocity(){
        return velocity;
    }

}
