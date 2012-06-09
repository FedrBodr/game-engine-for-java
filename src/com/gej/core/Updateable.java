package com.gej.core;

/**
 * The Updateable interface is required by every object which needs to be updated along with the
 * game. It provides a method to update with elapsed time spent in the current frame.
 * @author Sri Harsha Chilakapati
 */
public interface Updateable {
    
    /**
     * Update this object based on time.
     * @param elapsedTime The time spent in the current frame
     */
    public void update(long elapsedTime);
    
}
