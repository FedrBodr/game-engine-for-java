package com.gej.core;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.gej.input.GInput;
import com.gej.input.GMouse;
import com.gej.map.Map;
import com.gej.util.ImageTool;

/**
 * This class is the main class for any game. You should extend this class
 * to write a game. The games requires J2SE 1.5 or more to run. This class
 * won't support applet mode as applets are not the target of this engine.
 * This class uses triple buffering if available or uses double buffering.
 * The default game template could be like this.
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
public abstract class Game extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934394613281562786L;
		
	// Private variables
	private boolean running = false;
    private Image backImage    = null;
    private Graphics2D    backGraphics = null;
	private static HashMap<String, Image> cache = null;
	private LinkedList<JComponent> components = null;
	private LinkedList<Point> comp_points = null;
	
	protected GInput input = null;
	
	/**
	 * Constructs a new Game with default values.
	 * No need to use the constructor, as it is
	 * automatically called by the constructor of
	 * the sub classes.
	 */
	public Game(){
		init();
	}
	
	/**
	 * Initializes the game. Called automatically when the game starts.
	 * Users are prohibited from overriding this method as it creates
	 * the image cache and starts the game loop
	 */
	public final void init(){
		running = true;
		cache = new HashMap<String, Image>();
		components = new LinkedList<JComponent>();
		comp_points = new LinkedList<Point>();
		setFocusTraversalKeysEnabled(false);
		setDoubleBuffered(true);
		setFocusable(true);
		input = new GInput(this);
		Thread th = new Thread(this);
		th.start();
	}
		
	/**
	 * Starts the game and acts as a game loop.
	 * The FPS value is calculated automatically
	 * every second and can be known by using
	 * Global.FRAMES_PER_SECOND variable.
	 */
	public final void run(){
		// Initialize the resources
		initResources();
		// Note the current time
		long startTime = System.nanoTime() / 1000000;
        long currTime = startTime;
        // Variables used in counting the frame rate
    	int frames = 0;
    	int frame_time = 0;
    	// The actual game loop
		while (running){
			// Calculate the time
			long elapsedTime = (System.nanoTime()/1000000) - currTime;
	        currTime += elapsedTime;
	        frame_time += elapsedTime;
	        frames++;
	        // Calculate the frame rate
	        if (frame_time>=1000){
	        	Global.FRAMES_PER_SECOND = frames;
	        	frames = 0;
	        	frame_time = (int) elapsedTime;
	        }
	        // Set the max frame-rate to 150 as more fps is
	        // not supported int old linux systems.
	        if (Global.FRAMES_PER_SECOND > 150){
	        	Global.FRAMES_PER_SECOND = 150;
	        }
	        // Update and render the game
	        update(elapsedTime);	        	
        	repaint();
        	// Show or hide the cursor
	        if (Global.HIDE_CURSOR){
	        	setCursor(GInput.INVISIBLE_CURSOR);
	        } else {
	        	setCursor(Cursor.getDefaultCursor());
	        }
	        // If a map is loaded, update all the objects in the map
	        try {
	        	Map.updateObjects(elapsedTime);
	        	// Update any components if added.
	        	Iterator<JComponent> i2 = components.iterator();
	        	while (i2.hasNext()){
	        		JComponent comp = i2.next();
	        		if (comp.getBounds().contains(input.getMouseX(), input.getMouseY()) && GMouse.isMouseClicked(GInput.MOUSE_BUTTON_1)){
	        			componentClicked(comp);
	        		}
	        	}
	        } catch (Exception e) {}
	        try {
	        	// Sleep for a maximum of 10 milliseconds, so that
	        	// we won't waste any cpu cycles
	        	Thread.sleep(Math.min(1000l/Global.FRAMES_PER_SECOND, 10));
	        } catch (Exception e){}
	    }
	}
	
	/**
	 * Draw's the game. Here the triple buffering or double buffering is implemented
	 */
	public final void paint(Graphics g){
		try {
			// We need the Graphics2D class, not the legacy Graphics
			Graphics2D g2D = (Graphics2D)g;
			// Smooth the text rendering
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// Create a back buffer, if none
			if (backImage==null){
				backImage = ImageTool.getColoredImage(getBackground(), Global.WIDTH, Global.HEIGHT);
			}
			// Create a graphics from a back buffer
			backGraphics = (Graphics2D) backImage.getGraphics();
			backGraphics.setColor(getBackground());
			backGraphics.fillRect(0, 0, getWidth(), getHeight());
			backGraphics.setColor(getForeground());
			// Render the game on to the back buffer
			render(backGraphics);
			// Render any of the components
			renderComponents(backGraphics);
			// Dispose the graphics and flip the display
			backGraphics.dispose();
			g2D.drawImage(backImage, 0, 0, Global.WIDTH, Global.HEIGHT, null);
			g2D.dispose();
		} catch(NullPointerException e){}
	}
	
	/**
	 * Render's the components on this component
	 * @param g The Graphics context on which we have to draw
	 */
	public final void renderComponents(Graphics2D g){
		Iterator<JComponent> i = components.iterator();
		// Iterate through and draw all the components
		while (i.hasNext()){
			JComponent comp = i.next();
			Point p = comp_points.get(components.indexOf(comp));
			g.translate(p.x, p.y);
			comp.paint(g);
		}
	}
	
	/**
	 * Called when a component is added to this game. If a
	 * visible component is clicked, the component is sent
	 * as a parameter to this method.
	 * @param comp The component which is clicked
	 */
	public void componentClicked(JComponent comp){};
	
	/**
	 * Adds a component to the game. The component is already visible.
	 * @param comp The component to be added.
	 * @param x The x-coordinate of the component
	 * @param y The y-coordinate of the component
	 */
	public final void addComponent(JComponent comp, int x, int y){
		comp.setBounds(x, y, comp.getPreferredSize().width, comp.getPreferredSize().height);
		comp.setLocation(x, y);
		components.add(comp);
		comp_points.add(new Point(x, y));
	}
	
	/**
	 * Clears all the components from this game.
	 */
	public final void clearComponents(){
		components.clear();
		comp_points.clear();
	}
	
	/**
	 * Removes the specified component from the components list
	 * The component is made inaccessible from now.
	 * @param comp The component to be removed.
	 */
	public final void removeComponent(JComponent comp){
		int index = components.indexOf(comp);
		components.remove(index);
		comp_points.remove(index);
	}
	
	/**
	 * Initialize the resources, if any used by the game.
	 */
	public void initResources() {}
	
	/**
	 * Render the game. Draw all your objects etc., in
	 * this game.
	 * @param g The graphics context
	 */
	public void render(Graphics2D g){}
	
	/**
	 * Use this method to update your game. You could
	 * check input, collision between GObjects, and move
	 * them in this method.
	 * @param elapsedTime The time elapsed in the current frame
	 */
	public void update(long elapsedTime) {}
	
	/**
	 * Gets you image to load from the root of your jar file
	 * @param name The name of your image
	 * @return The loaded image.
	 */
	public static Image loadImage(String name){
		Image img = null;
		if (cache.containsKey(name)){
			img = cache.get(name);
		} else {
			img =  new ImageIcon(Game.class.getClassLoader().getResource(name)).getImage();
			cache.put(name, img);
		}
		return img;
	}
	
	/**
	 * Get the GInput object associated with this game.
	 * @return The current GInput method.
	 */
	public GInput getInput(){
		return input;
	}
		
}
