package com.gej.core;

/**
 * This class is passed as an argument to all the registered
 * EventListeners describing the event happened. Currently this
 * class is only used by the GTimer class.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GEvent {

	/** Constant value of a Timer Event */
	public static String TIMER_EVENT = "Timer Event";
	
	/** The type of this event */
	String type = "";
	
	/** Construct a new instance of GEvent with the type mentioned as a string */
	public GEvent(String type){
		this.type = type;
	}
	
	/**
	 * Use this method to know about the current GEvent type
	 * @return The type string
	 */
	public String getEventType(){
		return type;
	}
	
}
