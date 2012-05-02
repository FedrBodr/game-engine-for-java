package com.gej.graphics;

import java.awt.Image;
import java.util.ArrayList;

/**
 * Contains a list of images and frame timing information
 * to create an animation which is used by the GObject's.
 * 
 * @author Sri Harsha Chilakapati
 */
public class Animation {

	private ArrayList<Image> images = null;
	private ArrayList<Integer> durations = null;
	
	private int currFrameIndex = 0;
	private int duration = 0;
	
	/**
	 * This default constructor could be used to create
	 * an empty animation object. then use the addFrame()
	 * method to add sub-images with a specific delay.
	 */
	public Animation(){
		images = new ArrayList<Image>();
		durations = new ArrayList<Integer>();
	}
	
	/**
	 * This constructor is useful when you have same duration
	 * for every frame and could add frames from an image array.
	 * @param frames The image array containing all the frames
	 * @param duration The common duration of each and every image.
	 */
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
