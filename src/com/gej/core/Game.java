package com.gej.core;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.gej.input.GInput;
import com.gej.map.Map;
import com.gej.util.ImageTool;

/**
 * This class is the main class for any game. You should extend this class to
 * write a game. The games requires J2SE 1.5 or more to run. To run as an
 * Applet, use the GApplet class. This class uses triple buffering if available
 * or uses double buffering. The default game template could be like this.
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
public abstract class Game extends JPanel implements Updateable, Runnable {

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

    // Input manager
    protected GInput input = null;
    
    /**
     * Start the game and the game loop
     */
    public final void start(){
        // Finalize the VM
        System.gc();
        System.runFinalization();
        // Panel settings
        setDoubleBuffered(true);
        setFocusable(true);
        // Create the cache and input
        cache = new HashMap<String, Image>();
        input = GInput.install(this);
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
        long SKIP_STEPS = 1000/Global.STEPS_FOR_SECOND;
        int loops = 0;
        long NEXT_GAME_STEP = (long)getCurrentTime();
        // FPS counter
        int framecount = 0;
        double lastfpsCount = getCurrentTime();
        // UPD counter
        int updcount = 0;
        double lastupdCount = getCurrentTime();
        // The game loop
        while (running){
            loops = 0;
            while(getCurrentTime()>NEXT_GAME_STEP && loops<1){
                // Update the game and map
                update(SKIP_STEPS);
                if (state == GameState.GAME_PLAYING) {
                    Map.updateObjects(SKIP_STEPS);
                }
                // calculate update count
                updcount++;
                if (getCurrentTime() - lastupdCount > 1000){
                    lastupdCount = getCurrentTime();
                    Global.ACTUAL_STEPS_FOR_SECOND = updcount;
                    updcount = 0;
                }
                // Show or hide the cursor
                if (Global.HIDE_CURSOR) {
                    setCursor(GInput.INVISIBLE_CURSOR);
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
                NEXT_GAME_STEP+=SKIP_STEPS;
                loops++;
            }
            // Redraw the game
            repaint();
            // FPS counter
            framecount++;
            if (getCurrentTime() - lastfpsCount > 1000) {
                lastfpsCount = getCurrentTime();
                Global.FRAMES_PER_SECOND = framecount;
                framecount = 0;
            }
            // Timing mechanism
            try {Thread.sleep(1);} catch (Exception e){}
        }
    }

    /**
     * Paints the game and calls the render() method
     */
    public final void paint(Graphics g){
        try {
            // We need the Graphics2D class, not the legacy Graphics
            Graphics2D g2D = (Graphics2D) g;
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
            if (Global.FULLSCREEN) {
                g2D.drawImage(backImage, 0, 0, GWindow.RESOLUTION_X, GWindow.RESOLUTION_Y, null);
            } else if (Global.STRETCH_TO_SCREEN) {
                g2D.drawImage(backImage, 0, 0, getWidth(), getHeight(), null);
            } else {
                g2D.drawImage(backImage, 0, 0, Global.WIDTH, Global.HEIGHT, null);
            }
            g2D.dispose();
        } catch (NullPointerException e) {}
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
    public static double getCurrentTime(){
        return System.nanoTime() * 0.000001;
    }
    
}
