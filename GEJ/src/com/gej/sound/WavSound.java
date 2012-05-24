package com.gej.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class WavSound {
	
	private String sound_name = "";
	
	private boolean loop = false;
	

	private Clip clip = null;

	public WavSound(String sound, boolean loop){
		sound_name = sound;
		this.loop = loop;
		run();
	}
	
	public void run(){
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(sound_name));
			clip = AudioSystem.getClip();
			clip.open(stream);
		} catch (Exception e){}
	}
	
	public void start(){
		try {
			clip.setFramePosition(0);
			if (loop){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.start();
			}
		} catch (Exception e){}
	}
	
	public void stop(){
		if (clip.isRunning()){
			clip.stop();
		}
	}
	
}
