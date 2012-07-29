package com.gej.timer;

import com.gej.core.Global;

/**
 * Calculates the frame rate (FRAMES_PER_SECOND) and set it to
 * Global.FRAMES_PER_SECOND Users should not call any of the methods of this
 * class as these calls may effect game speed and game timing.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GFrameRateCalculator {

    // Private variables
    private static double lastCount;
    private static int framecount;

    /**
     * Refresh the calculator. Adjusts the frameCount and modifies lastCount.
     */
    public static void refresh(){
        framecount = 0;
        lastCount = getCurrentTime();
    }

    /**
     * Attempt to calculate the FPS of the game.
     */
    public static void reCalcFPS(){
        framecount++;
        if (getCurrentTime() - lastCount > 1000) {
            lastCount = getCurrentTime();
            Global.FRAMES_PER_SECOND = framecount;
            framecount = 0;
        }
    }

    /**
     * Returns the current time in milliseconds with high resolution timer
     * available in the JRE
     * 
     * @return The Current Time in MilliSeconds
     */
    public static double getCurrentTime(){
        return System.nanoTime() * 0.000001;
    }

    // Prevent instantiation
    private GFrameRateCalculator() {
    }

}
