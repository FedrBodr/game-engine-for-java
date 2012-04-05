package com.gej.core;

public class GEvent {

	public static String TIMER_EVENT = "Timer Event";
	
	String type = "";
	
	public GEvent(String type){
		this.type = type;
	}
	
	public String getEventType(){
		return type;
	}
	
}
