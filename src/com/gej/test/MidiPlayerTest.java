package com.gej.test;

import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GKeyBoard;
import com.gej.sound.MidiPlayer;

public class MidiPlayerTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8562464132276865561L;
	
	MidiPlayer myplayer = null;
	
	public void initResources(){
		myplayer = new MidiPlayer("resources/music.midi", true);
	}
	
	public void update(long elapsedTime){
		if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		if (GKeyBoard.isPressed(KeyEvent.VK_P)){
			myplayer.setPaused(myplayer.isPlaying());
		}
	}
	
	public static void main(String[] args){
		GWindow.setup(new MidiPlayerTest());
	}

}
