package com.gej.input;

/**
 * This class can be used to note keyboard input from any class. The values are
 * automatically updated by the GInput object.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class GKeyBoard {

    /** The number of key codes present in the KeyEvent class */
    public static final int NUM_KEY_CODES = 603;
    
    /** The key code which was processed recently */
    public static int RECENT_KEY_CODE = 602;
    
    /**
     * Set the detection type of a key code
     * @param keyCode The code
     * @param b The behavior
     */
    public static void setDetectionType(int keyCode, GInput.Behaviour b){
        GInput.KEY_ACTIONS[keyCode].setDetectionType(b);
    }
    
    /**
     * Check if a key has been pressed
     */
    public static boolean isPressed(int keyCode){
        return GInput.KEY_ACTIONS[keyCode].isPressed();
    }

}
