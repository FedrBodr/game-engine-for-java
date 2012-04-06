package com.gej.core;

/**
 * This interface is used to listen GEvents.
 * Currently these GEvents are only used by the GTimer.
 * 
 * @author Sri Harsha Chilakapati
 */
public interface EventListener {

	/**
	 * Process the GEvent generated and sent to this class
	 * @param e The GEvent to be processed
	 */
	public void processEvent(GEvent e);
	
}
