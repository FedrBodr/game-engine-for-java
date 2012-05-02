package com.gej.core;

/**
 * The Updateable interface is required by every object
 * which needs to be updated along with the game. It provides
 * a method to update with elapsed time spent in the current
 * frame. To make the object update automatically, add it to
 * the Global class's UPDATEABLES list. To add, add this code
 * to the objects constructor.
 * <pre>Global.UPDATEABLES.add(this);</pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public interface Updateable {
	
	/**
	 * Update this object based on time.
	 * @param elapsedTime The time spent in the current frame
	 */
	public void update(long elapsedTime);

}
