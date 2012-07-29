package com.gej.timer;

import com.gej.core.Global;

/**
 * An internal timer to time the game loop. Users should not call any of the
 * methods in this class as this would effect the game loop.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GTimer {

    // Private variables
    private static long delay, start, end, timeDiff, sleepTime, overSleepTime;

    /** Variable indicating the running status of the timer */
    public static boolean running;

    // Prevent instantiation
    private GTimer() {
    }

    /**
     * Starts the timer. The TARGET game rate can be modified even when the
     * timer is running by changing the GLOBAL.STEPS_FOR_SECOND variable.
     */
    public static void startTimer(){
        if (running) {
            stopTimer();
        }
        running = true;
        refresh();
        GFrameRateCalculator.refresh();
    }

    /**
     * Stops this timer.
     */
    public static void stopTimer(){
        running = false;
    }

    /**
     * Sync's the timer and calculates the time needed to sleep and calculates
     * the FPS and sleeps for a time to maintain the UPDATE_RATE of the game.
     * 
     * @return Returns the time elapsed to sync
     */
    public static long sync(){
        end = (long) GFrameRateCalculator.getCurrentTime();
        timeDiff = end - start;
        delay = 1000 / Global.STEPS_FOR_SECOND;
        sleepTime = (delay - timeDiff) - overSleepTime;
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
            }
            overSleepTime = ((long) GFrameRateCalculator.getCurrentTime() - end)
                    - sleepTime;
        } else {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
            overSleepTime = 0;
        }
        GFrameRateCalculator.reCalcFPS();
        end = (long) GFrameRateCalculator.getCurrentTime();
        long elapsedTime = end - start;
        start = end;
        if (elapsedTime > 100) {
            elapsedTime = 100;
        }
        return elapsedTime;
    }

    /** Refreshes the timer */
    public static void refresh(){
        start = (long) GFrameRateCalculator.getCurrentTime();
        overSleepTime = 0;
    }

}
