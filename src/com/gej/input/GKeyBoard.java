package com.gej.input;

import java.util.HashMap;

/**
 * This class can be used to note keyboard input from any class. The values are automatically
 * updated by the GInput object.
 * @author Sri Harsha Chilakapati
 */
public abstract class GKeyBoard {
    
    // The map containing booleans and keys
    private static HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
    
    // The number of keys which are pressed now
    private static int keysPressed = 0;
    
    /**
     * Notify if a key has been pressed
     * @param keyCode The key code.
     */
    public static void KeyPress(int keyCode){
        if (!isPressed(keyCode)){
            keys.put(keyCode, true);
            keysPressed++;
        }
    }
    
    /**
     * Notify if a key has been released
     * @param keyCode The key code.
     */
    public static void KeyReleased(int keyCode){
        if (isPressed(keyCode)){
            keys.put(keyCode, false);
            keysPressed--;
        }
    }
    
    /**
     * Returns whether a key is pressed or not.
     * @param keyCode The key code.
     * @return The boolean value of the key pressed
     */
    public static boolean isPressed(int keyCode){
        Boolean bool = keys.get(keyCode);
        if (bool == null){
            bool = false;
        }
        return bool;
    }
    
    /**
     * Returns true if no key is pressed now.
     * @return true if no key is pressed. Else false.
     */
    public static boolean isNoKeyPressed(){
        return (keysPressed == 0);
    }
    
}
