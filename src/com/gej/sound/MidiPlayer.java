package com.gej.sound;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * Implements a player to play MIDI audio data. Needs to
 * construct a player for every MIDI file you play. Can
 * also pause and loop.
 * 
 * @author Sri Harsha Chilakapati
 */
public class MidiPlayer extends Thread {
	
	// The private variables
	private Sequencer sequencer = null;
	
	private String path = "";
	private boolean loop = false;
	
	/**
	 * Constructs the player for a MIDI file present in the jar.
	 * @param path The path of the MIDI file.
	 * @param loop The looping
	 */
	public MidiPlayer(String path, boolean loop){
		this.path = path;
		this.loop = loop;
		start();
	}
		
	/**
	 * Runs the player. These MIDI's are usually used to
	 * represent background music.
	 */
	public void run(){
		try {
			sequencer = MidiSystem.getSequencer();
			Sequence s = MidiSystem.getSequence(MidiPlayer.class.getClassLoader().getResourceAsStream(path));
			sequencer.setSequence(s);
			sequencer.open();
			if (loop){
				sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			}
			sequencer.start();
		} catch (Exception e){e.printStackTrace();}
	}
	
	/**
	 * Returns the sequencer used by this player
	 * @return The Sequencer object
	 */
	public Sequencer getSequencer(){
		return sequencer;
	}
	
	/**
	 * Checks if this player is currently playing or not.
	 * @return True if playing. Else false.
	 */
	public boolean isPlaying(){
		return sequencer.isRunning();
	}
	
	/**
	 * Set's the paused state of this player
	 * @param paused The value in boolean
	 */
	public void setPaused(boolean paused){
		if (paused){
			sequencer.stop();
		} else {
			sequencer.start();
		}
	}

}
