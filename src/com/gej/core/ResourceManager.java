package com.gej.core;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import com.gej.sound.WavPlayer;
import com.gej.sound.WavSound;

/**
 * A resource manager to load the resources in separate thread. Can load
 * images and sounds and say the load percentage. Get the resources from
 * the manager and assign them in the update() method of the Game class
 * like this.
 * 
 * <pre>
 * 
 * Image myImg = null;
 * WavSound mySound = null;
 * 
 * public void initResources(){
 *     ResourceManager.defineImage("resources/myImg.png");
 *     ResourceManager.defineSound("resources/mySound.wav");
 *     ResourceManager.loadResources();
 * }
 * 
 * public void update(long elapsedTime){
 *     switch (getState()){
 *         case GAME_LOADING:
 *              // Loading completed
 *              if (!ResourceManager.isLoading()){
 *                  // Get the loaded image
 *                  myImg = ResourceManager.getImage("resources/myImg.png");
 *                  // Get the loaded sounded
 *                  mySound = ResourceManager.getSound("resources/mySound.wav");
 *                  // Change game state
 *                  setState(GameState.GAME_PLAYING);
 *              }
 *              break;
 *         case GAME_PLAYING:
 *              .....
 *     }
 * }
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class ResourceManager extends Thread {
            
    // Prevent instantiation by others
    private ResourceManager(){}
    
    // Private variables. To calculate 
    private static int numResources = 0;
    private static int numLoaded = 0;
    
    // Maps to store images and sounds
    private static final HashMap<String, Image> imgMap = new HashMap<String, Image>();
    private static final HashMap<String, WavSound> sndMap = new HashMap<String, WavSound>();
    
    // Lists of images and sounds to load
    private static final ArrayList<String> images = new ArrayList<String>();
    private static final ArrayList<String> sounds = new ArrayList<String>();
    
    /**
     * Loads the resources in a separate thread
     */
    public final void run(){
        Game.setState(GameState.GAME_LOADING);
        for (int i=0; i<images.size(); i++){
            String imageName = images.get(i);
            Image image = Game.loadImage(imageName);
            while (image==null){}
            imgMap.put(imageName, image);
            numLoaded++;
        }
        images.clear();
        for (int i=0; i<sounds.size(); i++){
            String soundName = sounds.get(i);
            WavSound sound = WavPlayer.loadSound(soundName);
            while (sound==null){}
            sndMap.put(soundName, sound);
            numLoaded++;
        }
        sounds.clear();
    }
    
    /**
     * Defines an image to load
     * @param imgName The name of the image
     */
    public static final void defineImage(String imgName){
        images.add(imgName);
        numResources++;
    }
    
    /**
     * Defines a sound to load
     * @param sndName The name of the sound
     */
    public static final void defineSound(String sndName){
        sounds.add(sndName);
        numResources++;
    }
    
    /**
     * Starts loading resources in a new thread
     */
    public static final void loadResources(){
        numLoaded = 0;
        new ResourceManager().start();
    }
    
    /**
     * Gets a loaded image if exists in the map
     * @param imgName The name of the image
     * @return The loaded image
     */
    public static final Image getImage(String imgName){
        return imgMap.get(imgName);
    }
    
    /**
     * Gets a loaded sound if exists in the map
     * @param sndName The name of the sound
     * @return The loaded sound
     */
    public static final WavSound getSound(String sndName){
        return sndMap.get(sndName);
    }
    
    /**
     * Calculates the load percentage of this resource manager
     * @return The percentage of loading resources
     */
    public static final int getLoadPercentage(){
        return (int)((numLoaded/numResources)*100);
    }
    
    /**
     * Checks if the manager is loading any resources
     * @return True if loading, else false
     */
    public static final boolean isLoading(){
        return !(numResources==numLoaded);
    }

}
