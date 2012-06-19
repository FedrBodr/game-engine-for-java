package com.gej.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.gej.core.Updateable;
import com.gej.util.ImageTool;

/**
 * Contains a list of images and frame timing information to create an animation
 * which is used by the GObject's.
 * 
 * @author Sri Harsha Chilakapati
 */
public class Animation implements Updateable {

    // Collection of frames in the animation
    private ArrayList<Image> images = null;
    private ArrayList<Integer> durations = null;

    // Collection of frames in the BufferedImage format
    // Used for pixel-perfect collision detection.
    private ArrayList<BufferedImage> bimages = null;

    // The index of current frame
    private int currFrameIndex = 0;
    // The duration spent in the current frame
    private int duration = 0;

    // The paused property
    private boolean paused = false;

    /**
     * This default constructor could be used to create an empty animation
     * object. then use the addFrame() method to add sub-images with a specific
     * delay.
     */
    public Animation() {
        images = new ArrayList<Image>();
        durations = new ArrayList<Integer>();
        bimages = new ArrayList<BufferedImage>();
    }

    /**
     * This constructor is useful when you have same duration for every frame
     * and could add frames from an image array.
     * 
     * @param frames The image array containing all the frames
     * @param duration The common duration of each and every image.
     */
    public Animation(Image[] frames, int duration) {
        this();
        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], duration);
        }
    }

    /**
     * Add an image as a frame of this animation with a specific duration.
     * 
     * @param frame The frame image
     * @param duration The duration
     */
    public void addFrame(Image frame, int duration){
        images.add(frame);
        bimages.add(ImageTool.toBufferedImage(frame));
        durations.add(duration);
    }

    /**
     * Updates the current animation object
     * 
     * @param elapsedTime The time spent in current frame
     */
    public synchronized void update(long elapsedTime){
        if (!paused) {
            duration += elapsedTime;
        }
        if (duration >= durations.get(currFrameIndex)) {
            duration = 0;
            nextFrame();
        }
    }

    /**
     * Skip this frame and move to the next one. Note that all the frames are
     * looped constantly.
     */
    public void nextFrame(){
        currFrameIndex++;
        if (currFrameIndex >= images.size()) {
            currFrameIndex = 0;
        }
    }

    /**
     * Displays the previous frame. Note that all the frames are looped
     * constantly.
     */
    public void previousFrame(){
        currFrameIndex--;
        if (currFrameIndex < 0) {
            currFrameIndex = 0;
        }
    }

    /**
     * Gets you the current frame image of this animation object.
     * 
     * @return The current frame as an Image
     */
    public synchronized Image getImage(){
        return images.get(currFrameIndex);
    }

    /**
     * Gets you the current frame image of this animation object.
     * 
     * @return The current frame as an Image
     */
    public synchronized BufferedImage getBufferedImage(){
        return bimages.get(currFrameIndex);
    }

    /**
     * Pause this animation
     */
    public synchronized void pause(){
        paused = true;
    }

    /**
     * Resume this animation
     */
    public synchronized void resume(){
        paused = false;
    }

    /**
     * Returns whether the animation is paused or not.
     * 
     * @return The paused state.
     */
    public synchronized boolean isPaused(){
        return paused;
    }

}
