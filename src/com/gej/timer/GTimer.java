package com.gej.timer;

/**
 * This class is designed to easily count the time and perform a logic
 * every time when the requested time elapsed. Usage.
 * 
 * <pre>
 * GTimer timer = new GTimer(1000); // Requested time in ms
 * 
 * public void update(elapsedTime){
 *     if (!timer.active) timer.start();
 *     if (timer.action(elapsedTime)){
 *         // Do something
 *     }
 * }
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class GTimer {
    
    long requested = 0;
    long elapsed = 0;
    
    /** The state of the timer */
    public boolean active = false;

    /**
     * Construct a new GTimer
     * @param req The required time between each call (in ms)
     */
    public GTimer(long req){
        requested = req;
    }
    
    /**
     * Check if the timer has alarmed.
     * @param elapsedTime The time elapsed since the last check
     * @return True if alarmed and false if not
     */
    public boolean action(long elapsedTime){
        if (active){
            elapsed += elapsedTime;
            if (elapsed>=requested){
                elapsed-=requested;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Starts this timer
     */
    public void start(){
        active = true;
    }
    
    /**
     * Stops this timer
     */
    public void stop(){
        active = false;
        elapsed = 0;
    }
    
}
