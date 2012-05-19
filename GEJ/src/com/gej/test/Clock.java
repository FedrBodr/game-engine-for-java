package com.gej.test;


import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;

public class Clock extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6266288210685998880L;
	
	long milliseconds = 0;
	long seconds      = 0;
	long minutes      = 0;
	long hours        = 0;
	
	@Override
	public void initResources(){
		Global.HEIGHT = 30;
		Global.WIDTH = 400;
	}
	
	@Override
	public void update(long elapsedTime){
		Global.TITLE = "TIME : " + hours + " : " + minutes + " : " + seconds + " : " + milliseconds;
		milliseconds += elapsedTime;
		if (milliseconds >= 1000){
			seconds++;
			milliseconds = 0;
		}
		if (seconds >= 60){
			minutes++;
			seconds = 0;
		}
		if (minutes >= 60){
			hours++;
			minutes = 0;
		}
	}
	
	public static void main(String[] args){
		GWindow.setup(new Clock());
	}

}
