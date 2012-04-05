package com.gej.graphics;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	private ArrayList<AnimFrame> frames = null;
	private int currFrameIndex = 0;
	private long animTime = 0;
	private long totalDuration = 0;
	
	public Animation(){
		frames = new ArrayList<AnimFrame>();
	}
	
	public Animation(Image[] frames, int duration){
		this();
		for (int i=0; i<frames.length; i++){
			addFrame(frames[i], duration);
		}
	}
	
	public synchronized void addFrame(Image frame, int duration){
		totalDuration += duration;
		frames.add(new AnimFrame(frame, duration));
	}
	
	public synchronized void start(){
		animTime = 0;
		currFrameIndex = 0;
	}
	
	public synchronized AnimFrame getFrame(int index){
		return frames.get(index);
	}
	
	public synchronized void update(long elapsedTime) {
        if (frames.size() > 1) {
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }
            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;
            }
        }
    }
	
	public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }
    }

	public class AnimFrame {
		
		public Image image;
		public long endTime;
		
		public AnimFrame(Image image, int endTime){
			this.image = image; this.endTime = endTime;
		}
		
	}
	
}
