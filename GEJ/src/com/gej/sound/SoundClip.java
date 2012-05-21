package com.gej.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundClip {
	
	private Clip clip;
	private int loop;
	
	public static final int LOOP_NEVER = 0;
	public static final int LOOP_CONTINUOUSLY = 1;
	
	public SoundClip(String filename, int loop){
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(filename)));
			if (loop==LOOP_CONTINUOUSLY){
				loop = Clip.LOOP_CONTINUOUSLY;
			}
			this.loop = loop;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		clip.stop();
	}
	
	public void start(){
		clip.loop(loop);
	}
	
	public boolean isPlaying(){
		return clip.isRunning();
	}

}
