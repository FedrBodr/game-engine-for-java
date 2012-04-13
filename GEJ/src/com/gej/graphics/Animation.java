package com.gej.graphics;

import java.awt.Image;
import java.util.ArrayList;

/**
 * This
 * 
 * @author Sri Harsha Chilakapati
 */
public class Animation {

	ArrayList<Image> images = null;
	ArrayList<Integer> durations = null;
	
	int currFrameIndex = 0;
	int duration = 0;
	
	public Animation(){
		images = new ArrayList<Image>();
		durations = new ArrayList<Integer>();
	}
	
	public Animation(Image[] frames, int duration){
		this();
		for (int i=0; i<frames.length; i++){
			addFrame(frames[i], duration);
		}
	}
	
	public void addFrame(Image frame, int duration){
		images.add(frame);
		durations.add(duration);
	}
	
	public synchronized void update(long elapsedTime){
		duration += elapsedTime;
		if (duration>=durations.get(currFrameIndex)){
			duration = 0;
			nextFrame();
		}
	}
	
	public void nextFrame(){
		currFrameIndex++;
		if (currFrameIndex>=images.size()){
			currFrameIndex = 0;
		}
	}
	
	public void previousFrame(){
		currFrameIndex--;
		if (currFrameIndex<0){
			currFrameIndex = 0;
		}
	}
	
	public synchronized Image getImage(){
		return images.get(currFrameIndex);
	}
	
}
