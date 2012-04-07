package com.gej.graphics;

import java.awt.Image;
import java.util.ArrayList;

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
			currFrameIndex++;
			if (currFrameIndex>=images.size()){
				currFrameIndex = 0;
			}
		}
	}
	
	public synchronized Image getImage(){
		return images.get(currFrameIndex);
	}
	
}
