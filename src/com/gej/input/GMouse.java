package com.gej.input;

import java.util.HashMap;

/**
 * This class can be used to note mouse input from any class in the game which
 * are automatically updated by the GInput.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GMouse {

    // A collection of mouse keys
    private static HashMap<Integer, Boolean> data = new HashMap<Integer, Boolean>();

    /**
     * Returns true if the mouse has been moved
     * 
     * @return Whether the mouse is being moved at this moment.
     */
    public static boolean isMouseMoved(){
        if (data.containsKey(GInput.MOUSE_MOVED)) {
            return data.get(GInput.MOUSE_MOVED);
        }
        return false;
    }

    /** The x-coordinate of the mouse */
    public static int MOUSE_X = 0;
    /** The y-coordinate of the mouse */
    public static int MOUSE_Y = 0;

    /**
     * Can be used to know if a mouse button is clicked
     * 
     * @param button The mouse button code.
     * @return True if pressed. Else false.
     */
    public static boolean isMouseClicked(int button){
        if (data.containsKey(button)) {
            return data.get(button);
        }
        return false;
    }

    /**
     * Sets the value of mouse code with a value of true or false.
     * 
     * @param id The Mouse ID
     * @param value The value of the state
     */
    public static void setID(int id, boolean value){
        data.put(id, value);
    }

}
