package com.gej.input;

import com.gej.input.GInput.Behaviour;

/**
 * A class representing an input action.
 * 
 * @author Sri Harsha Chilakapati
 */
public class Action {        
    
    /**
     * The state of this action
     * 
     * @author Sri Harsha Chilakapati
     */
    public static enum State {
        PRESSED, RELEASED, WAITING_FOR_RELEASE
    }
    
    // private variables
    Behaviour DETECTION_TYPE = Behaviour.NORMAL;
    State     STATE          = State.RELEASED;
    int       AMOUNT         = 0;
    
    /**
     * Creates a new Action
     */
    public Action(){}
    
    /**
     * Sets the detection type for this action
     */
    public void setDetectionType(Behaviour b){
        DETECTION_TYPE = b;
    }
    
    /**
     * Resets this action
     */
    public void reset(){
        AMOUNT = 0;
        STATE = State.RELEASED;
    }
    
    /**
     * Presses this action once
     */
    public synchronized void press(){
        press(1);
    }
    
    /**
     * Presses this action with a certain amount
     */
    public synchronized void press(int amount){
        if (STATE != State.WAITING_FOR_RELEASE){
            AMOUNT += amount;
            STATE = State.PRESSED;
        }
    }
    
    /**
     * Releases this action
     */
    public synchronized void release(){
        STATE = State.RELEASED;
    }
    
    /**
     * Taps this action. Same as calling press() followed by a release()
     */
    public synchronized void tap(){
        press();
        release();
    }
    
    /**
     * Returns the amount it's been pressed. For mouse actions,
     * this will be the distance moved.
     */
    public synchronized int getAmount(){
        int retVal = AMOUNT;
        if (STATE == State.RELEASED){
            AMOUNT = 0;
        } else if (DETECTION_TYPE == Behaviour.INITIAL_DETECT){
            STATE = State.WAITING_FOR_RELEASE;
            AMOUNT = 0;
        }
        return retVal;
    }
    
    /**
     * Check if the action is pressed
     */
    public synchronized boolean isPressed(){
        return (getAmount()!=0);
    }
    
}