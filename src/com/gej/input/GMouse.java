package com.gej.input;

/**
 * This class can be used to note mouse input from any class in the game which
 * are automatically updated by the GInput.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GMouse {

    /** The recent mouse code that is processed */
    public static int RECENT_MOUSE_CODE = -1;
    
    /** The mouse had moved left */
    public static final int MOUSE_MOVE_LEFT = 0;
    /** The mouse had moved right */
    public static final int MOUSE_MOVE_RIGHT = 1;
    /** The mouse had moved up */
    public static final int MOUSE_MOVE_UP = 2;
    /** The mouse had moved down */
    public static final int MOUSE_MOVE_DOWN = 3;
    /** The mouse wheel scrolled up */
    public static final int MOUSE_WHEEL_UP = 4;
    /** The mouse wheel scrolled down */
    public static final int MOUSE_WHEEL_DOWN = 5;
    /** Left mouse button */
    public static final int MOUSE_BUTTON_1 = 6;
    /** Middle mouse button (Pressing the scroll-wheel) */
    public static final int MOUSE_BUTTON_2 = 7;
    /** Right mouse button */
    public static final int MOUSE_BUTTON_3 = 8;
    /** Pressed once the left mouse button */
    public static final int MOUSE_CLICKED = 9;
    /** The total number of mouse codes */
    public static final int NUM_MOUSE_CODES = 10;
    
    /** The x-position of the mouse */
    public static int MOUSE_X = 0;
    /** The y-position of the mouse */
    public static int MOUSE_Y = 0;
    
    /** The x-position of the recent mouse click */
    public static int MOUSE_CLICK_X = 0;
    /** The y-position of the recent mouse click */
    public static int MOUSE_CLICK_Y = 0;
    
    /**
     * Check if the mouse has been clicked ( A press and release )
     */
    public static boolean isClicked(){
        return GInput.MOUSE_ACTIONS[MOUSE_CLICKED].isPressed();
    }
    
    /**
     * Check if the mouse has been pressed
     * @param button The mouse button code
     */
    public static boolean isPressed(int button){
        return GInput.MOUSE_ACTIONS[button].isPressed();
    }
    
    /**
     * Returns the distance moved by the mouse in a direction
     * @param mouseCode The mouse movement code
     */
    public static int getDistance(int mouseCode){
        return GInput.MOUSE_ACTIONS[mouseCode].getAmount();
    }

}
