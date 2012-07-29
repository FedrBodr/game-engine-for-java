package com.gej.sound;

import javax.sound.sampled.AudioFormat;

/**
 * Represents a WavFile from the JAR. You have to load them using the static
 * loadSound() method of the WavPlayer class.
 * 
 * @author Sri Harsha Chilakapati
 */
public class WavSound {

    // Private variables
    private byte[] data = null;
    private AudioFormat format = null;

    private int length = 0;

    private boolean loop = false;
    private SoundState state = SoundState.STATE_STOPPED;

    /**
     * Constructs a new WavSound with a specific AudioFormat and sound data
     * 
     * @param data The data of the WAV file as a byte array.
     * @param format The format of this WAV file.
     */
    public WavSound(byte[] data, AudioFormat format, int length) {
        this.data = data;
        this.format = format;
        this.length = length;
    }

    /**
     * Returns the data associated with this WavSound
     * 
     * @return The data as a byte array
     */
    public byte[] getData(){
        return data;
    }

    /**
     * Returns the audio format used by this WavSound
     * 
     * @return The audio format used
     */
    public AudioFormat getAudioFormat(){
        return format;
    }

    /**
     * Returns the current state of the sound
     * 
     * @return The current sound state
     */
    public SoundState getState(){
        return state;
    }

    /**
     * Sets the state of this sound
     * 
     * @param state The SoundState describing the state
     */
    public void setState(SoundState state){
        this.state = state;
    }

    /**
     * Returns true if this sound is playing or looping
     * 
     * @return True if this sound is playing
     */
    public boolean isPlaying(){
        return (state == SoundState.STATE_PLAYING || state == SoundState.STATE_LOOPING);
    }

    /**
     * Stops the current sound
     */
    public void stop(){
        state = SoundState.STATE_STOPPED;
    }

    /**
     * Play's the current sound
     */
    public void play(){
        if (loop) {
            state = SoundState.STATE_LOOPING;
        } else {
            state = SoundState.STATE_PLAYING;
        }
    }

    /**
     * Destroy's this sound so that it can't be played
     */
    public void destroy(){
        state = SoundState.STATE_DESTROYED;
    }

    /**
     * Checks if this sound is looping
     * 
     * @return True if looping else false
     */
    public boolean isLooping(){
        return loop;
    }

    /**
     * Set's the looping value of this sound
     * 
     * @param value
     */
    public void setLooping(boolean value){
        loop = value;
    }

    public int getLength(){
        return length;
    }

}
