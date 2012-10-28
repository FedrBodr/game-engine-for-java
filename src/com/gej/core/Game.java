package com.gej.core;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JApplet;

import com.gej.graphics.GCursor;
import com.gej.input.GInput;
import com.gej.map.Map;
import com.gej.util.ImageTool;

/**
 * This class is the main class for any game. You should extend this class to
 * write a game. The games requires J2SE 1.5 or more to run. This class uses
 * triple buffering if available or uses double buffering. The default game
 * template could be like this.
 * 
 * <pre>
 * public class MyGame extends Game {
 * 
 *     public void initResources(){
 *         // load resources
 *     }
 * 
 *     public void update(long elapsedTime){
 *         // update the game
 *     }
 * 
 *     public void render(Graphics2D g){
 *         // render the game
 *     }
 * 
 * }
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class Game extends JApplet implements Updateable, Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 4506620240836220298L;
    
    // Private variables
    private static boolean running = false;
    private Image backImage = null;
    private Graphics2D backGraphics = null;
    private static HashMap<String, Image> cache = null;
    
    // The game state
    private static GameState state = GameState.GAME_LOADING;
    
    private Canvas canvas = null;
    
    /** The BufferStrategy of the game */
    public static BufferStrategy buffer = null;

    // Input manager
    protected GInput input = null;
    
    /**
     * Start the game and the game loop
     */
    public final void start(){
        canvas = new Canvas();
        add(canvas);
        canvas.setIgnoreRepaint(true);
        canvas.requestFocus();
        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
        // Finalize the VM
        System.gc();
        System.runFinalization();
        // Panel settings
        setFocusable(true);
        // Create the cache and input
        cache = new HashMap<String, Image>();
        input = GInput.install(canvas);
        running = true;
        // Start the game loop in new thread
        Thread th = new Thread(this);
        th.start();
    }
    
    /**
     * Implements the game loop. User mustn't call this method.
     */
    public final void run(){
        // Initialize the resources
        Map.initMap();
        initResources();
        // Game loop initialization
        int SKIP_STEPS = 1000/Global.STEPS_FOR_SECOND;
        // The time
        long gameTime = getCurrentTime();
        int loops;
        // FPS counter
        int frames = 0;
        long lastFPSCount = getCurrentTime();
        // UPD counter
        int updates = 0;
        long lastUPDCount = getCurrentTime();
        // update info
        long elapsedTime = 0;
        long lastUpdateTime = 0;
        while (running){
            loops = 0;
            while(getCurrentTime()>gameTime && loops<Global.MAX_FRAMESKIP){
                elapsedTime = getCurrentTime() - lastUpdateTime;
                lastUpdateTime = getCurrentTime();
                updateGame(elapsedTime);
                // calculate update count
                updates++;
                if (getCurrentTime() - lastUPDCount > 1000){
                    lastUPDCount = getCurrentTime();
                    Global.ACTUAL_STEPS_FOR_SECOND = updates;
                    Global.UPDATE_RATE = (int)((float)((float)Global.ACTUAL_STEPS_FOR_SECOND/(float)Global.STEPS_FOR_SECOND)*100);
                    updates = 0;
                }
                gameTime += SKIP_STEPS;
                loops++;
            }
            displayGame();
            // FPS counter
            frames++;
            if (getCurrentTime() - lastFPSCount > 1000) {
                lastFPSCount = getCurrentTime();
                Global.FRAMES_PER_SECOND = frames;
                frames = 0;
            }
        }
    }
    
    private void updateGame(long elapsedTime){
        // Update the game and map
        update(elapsedTime);
        if (state == GameState.GAME_PLAYING) {
            Map.updateObjects(elapsedTime);
        }
        // Show or hide the cursor
        if (Global.HIDE_CURSOR) {
            GCursor.setCursor(GCursor.INVISIBLE);
        }
        setCursor(GCursor.CURRENT);
    }
    
    private void displayGame(){
        try{
            Graphics2D g2D = (Graphics2D)buffer.getDrawGraphics();
            // Smooth the text rendering
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Create a back buffer, if none
            if (backImage == null) {
                backImage = ImageTool.getColoredImage(getBackground(), Global.WIDTH, Global.HEIGHT);
            }
            // Create a graphics from a back buffer
            backGraphics = (Graphics2D) backImage.getGraphics();
            backGraphics.setColor(getBackground());
            backGraphics.fillRect(0, 0, getWidth(), getHeight());
            backGraphics.setColor(getForeground());
            // Render the game on to the back buffer
            render(backGraphics);
            // Dispose the graphics and flip the display
            backGraphics.dispose();
            if (!Global.WEB_MODE){
                if (Global.FULLSCREEN){
                    g2D.drawImage(backImage, 0, 0, GWindow.RESOLUTION_X, GWindow.RESOLUTION_Y, null);
                } else if (Global.STRETCH_TO_SCREEN) {
                    g2D.drawImage(backImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2D.drawImage(backImage, 0, 0, Global.WIDTH, Global.HEIGHT, null);
                }
            } else {
                if (Global.STRETCH_TO_SCREEN) {
                    g2D.drawImage(backImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2D.drawImage(backImage, 0, 0, Global.WIDTH, Global.HEIGHT, null);
                }
            }
            g2D.dispose();
            buffer.show();
            Toolkit.getDefaultToolkit().sync();
        } catch (Exception e){}
    }
    
    /**
     * Initialize the resources for the game
     */
    public void initResources(){}

    /**
     * Render the game using the supplied Graphics2D object
     * @param g The Graphics2D object
     */
    public void render(Graphics2D g){}
    
    /**
     * Update the game. Do game logic.
     */
    public void update(long elapsedTime){}
    
    /**
     * Set the state of the game
     * @param s The new game state
     */
    public static final void setState(GameState s){
        state = s;
    }
    
    /**
     * @return The current game state
     */
    public static final GameState getState(){
        return state;
    }
        
    /**
     * Ends the current game. Only use this method but
     * do not explicitly terminate the VM
     */
    public static final void end_game(){
        running = false;
        GWindow.closeWindow();
    }
    
    /**
     * Gets you image to load from the root of your jar file
     * 
     * @param name The name of your image
     * @return The loaded image.
     */
    public static Image loadImage(String name){
        Image img = null;
        if (cache==null){
            cache = new HashMap<String, Image>();
        }
        if (cache.containsKey(name)) {
            img = cache.get(name);
        } else {
            try {
                img = new ImageIcon(Game.class.getClassLoader().getResource(name)).getImage();
                cache.put(name, img);
            } catch (Exception e) {
                System.err.println("Error loading image : " + name
                        + "\nFatal error. The program is exiting.");
            }
        }
        return img;
    }
    
    /**
     * @return True if the GameLoop is running
     */
    public static final boolean isRunning(){
        return running;
    }
    
    /**
     * Returns the current time in milliseconds with high resolution timer
     * available in the JRE.
     */
    public static long getCurrentTime(){
        return (System.nanoTime()/1000000);
    }
    
}
