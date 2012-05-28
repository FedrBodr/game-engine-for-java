package com.gej.sound;

/**
 * Represents the state of the sound.
 * 
 * @author Sri Harsha Chilakapati
 */
public enum SoundState {
	
	/**
	 * The current sound is playing 
	 */
	STATE_PLAYING,
	
	/**
	 * The current sound is stopped
	 */
	STATE_STOPPED,
	
	/**
	 * The current sound is looping
	 */
	STATE_LOOPING,
	
	/**
	 * The current sound s destroyed
	 */
	STATE_DESTROYED

}
