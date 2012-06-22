package com.gej.core;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
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
public abstract class Game extends JPanel implements Runnable, Updateable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5934394613281562786L;

    // Private variables
    private boolean running = false;
    private Image backImage = null;
    private Graphics2D backGraphics = null;
    private static HashMap<String, Image> cache = null;

    /** The time elapsed in the current frame */
    public static long elapsedTime = 0;
    
    // The game state
    private static GameState state = GameState.GAME_LOADING;

    // Input manager
    protected GInput input = null;

    /**
     * Constructs a new Game with default values. No need to use the
     * constructor, as it is automatically called by the constructor of the sub
     * classes.
     */
    public Game() {
        init();
    }

    /**
     * Initializes the game. Called automatically when the game starts. Users
     * are prohibited from overriding this method as it creates the image cache
     * and starts the game loop
     */
    public final void init(){
        running = true;
        cache = new HashMap<String, Image>();
        setFocusTraversalKeysEnabled(false);
        setIgnoreRepaint(true);
        setDoubleBuffered(true);
        setFocusable(true);
        input = new GInput(this);
        // Start the game thread to process game updates.
        Thread th = new Thread(this);
        th.setPriority(Thread.MAX_PRIORITY);
        th.start();
    }

    /**
     * Starts the game and acts as a game loop. The FPS value is calculated
     * automatically every second and can be known by using
     * Global.FRAMES_PER_SECOND variable.
     */
    public final void run(){
        // Initialize the resources
        Map.initMap();
        initResources();
        // Note the current time
        long time = System.nanoTime() / 1000000;
        long fpsRecalcPeriod = 0;
        while (running) {
            // Calculate the elapsed time
            elapsedTime = System.nanoTime() / 1000000 - time;
            time = System.nanoTime() / 1000000;
            // Show or hide the cursor
            if (Global.HIDE_CURSOR) {
                setCursor(GInput.INVISIBLE_CURSOR);
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
            // Update the game and the objects
            update(elapsedTime);
            if (state!=GameState.GAME_PAUSED){
                Map.updateObjects(elapsedTime);
            }
            // Repaint the game
            repaint();
            // Sync the game to meet the target frame rate
            sync();
            fpsRecalcPeriod += elapsedTime;
            // Calculate the FPS every 500ms
            if (elapsedTime > 0 && fpsRecalcPeriod > 500) {
                Global.FRAMES_PER_SECOND = (int) (1000 / elapsedTime);
                fpsRecalcPeriod = 0;
            }
        }
    }

    private void sync(){
        try {
            // Sync the toolkit
            Toolkit.getDefaultToolkit().sync();
            // Wait a bit to meet the target frame rate
            long wait = 1000 / Global.FRAMES_PER_SECOND;
            wait = Math.min(wait, 10);
            Thread.sleep(wait);
        } catch (Exception e) {
        }
    }

    /**
     * Draw's the game. Here the triple buffering or double buffering is
     * implemented
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
            } else {
                g2D.drawImage(backImage, 0, 0, Global.WIDTH, Global.HEIGHT, null);
            }
            g2D.dispose();
        } catch (NullPointerException e) {
        }
    }

    /**
     * Initialize the resources, if any used by the game.
     */
    public void initResources(){
    }

    /**
     * Render the game. Draw all your objects etc., in this game.
     * 
     * @param g The graphics context
     */
    public void render(Graphics2D g){
    }

    /**
     * Use this method to update your game. You could check input, collision
     * between GObjects, and move them in this method.
     * 
     * @param elapsedTime The time elapsed in the current frame
     */
    public void update(long elapsedTime){
    }

    /**
     * Gets you image to load from the root of your jar file
     * 
     * @param name The name of your image
     * @return The loaded image.
     */
    public static Image loadImage(String name){
        Image img = null;
        if (cache.containsKey(name)) {
            img = cache.get(name);
        } else {
            img = new ImageIcon(Game.class.getClassLoader().getResource(name)).getImage();
            cache.put(name, img);
        }
        return img;
    }

    /**
     * Get the GInput object associated with this game.
     * 
     * @return The current GInput method.
     */
    public GInput getInput(){
        return input;
    }
    
    /**
     * Sets the state of this game
     * @param g The new state
     */
    public static void setState(GameState g){
        state = g;
    }
    
    /**
     * Gets the current state of the game
     * @return The current game state
     */
    public static GameState getState(){
        return state;
    }

}
