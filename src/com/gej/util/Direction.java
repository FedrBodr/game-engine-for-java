package com.gej.util;

/**
 * An enumeration containing constants used for maintaining directions. Only
 * four directions and a constant for no direction. So use two fields for
 * vertical and horizontal directions. For use in objects, use the preprogrammed
 * DirectionedGObject class instead.
 * 
 * @author Sri Harsha Chilakapati
 */
public enum Direction {

    /** The upward direction */
    DIRECTION_UP,
    /** The downward direction */
    DIRECTION_DOWN,
    /** The left direction */
    DIRECTION_LEFT,
    /** The right direction */
    DIRECTION_RIGHT,
    /** No direction.. No movement.. */
    DIRECTION_NONE;

    /**
     * Checks if a direction is horizontal. Returns false even if the direction
     * is none.
     * 
     * @param dir The direction
     * @return True if horizontal else false.
     */
    public static boolean isHorizontal(Direction dir){
        return ((dir == DIRECTION_LEFT) || (dir == DIRECTION_RIGHT));
    }

    /**
     * Checks if a direction is vertical. Returns false even if the direction is
     * none.
     * 
     * @param dir The direction
     * @return True if vertical else false.
     */
    public static boolean isVertical(Direction dir){
        return ((dir == DIRECTION_UP) || (dir == DIRECTION_DOWN));
    }

}
