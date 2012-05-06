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
import javax.swing.JApplet;
import javax.swing.JComponent;

import com.gej.input.GInput;
import com.gej.object.GAction;
import com.gej.object.GObject;
import com.gej.util.ImageTool;

/**
 * This class is the main class for any game. You should extend this class
 * to write a game.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class Game extends JApplet implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934394613281562786L;
		
	// Private variables
	private boolean running = false;
    private Image backImage    = null;
    private Graphics2D    backGraphics = null;
	private HashMap<String, Image> cache = null;
	private LinkedList<JComponent> components = null;
	private LinkedList<Point> comp_points = null;
	private GAction click = null;
	
	private GInput input = null;
	
	/**
	 * Constructs a new Game with default values.
	 * No need to use the constructor, as it is
	 * automatically called by the constructor of
	 * the sub classes.
	 */
	public Game(){
		init();
	}
	
	public final void init(){
		running = true;
		cache = new HashMap<String, Image>();
		components = new LinkedList<JComponent>();
		comp_points = new LinkedList<Point>();
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		input = new GInput(this);
		click = new GAction("CLICK", GAction.INITIAL_KEY_PRESS_ONLY);
		input.mapToMouse(click, GInput.MOUSE_BUTTON_1);
		Thread th = new Thread(this);
		th.start();
	}
		
	/**
	 * Starts the game and acts as a game loop.
	 * The default FPS value is 100. But you can
	 * change it in your code by using the Global class.
	 */
	public final void run(){
		initResources();
		long startTime = System.currentTimeMillis();
        long currTime = startTime;
		while (running){
			long elapsedTime = System.currentTimeMillis() - currTime;
	        currTime += elapsedTime;
			update(elapsedTime);
			if (Global.HIDE_CURSOR){
				setCursor(GInput.INVISIBLE_CURSOR);
			} else {
				setCursor(Cursor.getDefaultCursor());
			}
			try {
				Iterator<Updateable> i = Global.UPDATEABLES.iterator();
				while (i.hasNext()){
					Updateable upd = i.next();
					if (upd instanceof GObject){
						if (!((GObject) upd).isAlive()){
							i.remove();
						}
					}
					upd.update(elapsedTime);
				}
				Iterator<JComponent> i2 = components.iterator();
				while (i2.hasNext()){
					JComponent comp = i2.next();
					if (comp.getBounds().contains(input.getMouseX(), input.getMouseY()) && click.isPressed()){
						componentClicked(comp);
					}
				}
				repaint();
				Thread.sleep(1000/Global.FRAMES_PER_SECOND);
			} catch (Exception e) {}
		}
	}
	
	public final void paint(Graphics g){
		try {
			Graphics2D g2D = (Graphics2D)g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (backImage==null){
				backImage = ImageTool.getColoredImage(getBackground(), Global.WIDTH, Global.HEIGHT);
			}
			backGraphics = (Graphics2D) backImage.getGraphics();
			backGraphics.setColor(getBackground());
			backGraphics.fillRect(0, 0, getWidth(), getHeight());
			backGraphics.setColor(getForeground());
			render(backGraphics);
			renderComponents(backGraphics);
			backGraphics.dispose();
			g2D.drawImage(backImage, 0, 0, getWidth(), getHeight(), null);
			g2D.dispose();
		} catch(NullPointerException e){}
	}
	
	public final void renderComponents(Graphics2D g){
		Iterator<JComponent> i = components.iterator();
		while (i.hasNext()){
			JComponent comp = i.next();
			Point p = comp_points.get(components.indexOf(comp));
			g.translate(p.x, p.y);
			comp.paint(g);
		}
	}
	
	public void componentClicked(JComponent comp){};
	
	public final void addComponent(JComponent comp, int x, int y){
		comp.setBounds(x, y, comp.getPreferredSize().width, comp.getPreferredSize().height);
		comp.setLocation(x, y);
		components.add(comp);
		comp_points.add(new Point(x, y));
	}
	
	public final void clearComponents(){
		components.clear();
		comp_points.clear();
	}
	
	public final void removeComponent(JComponent comp){
		int index = components.indexOf(comp);
		components.remove(index);
		comp_points.remove(index);
	}
	
	/**
	 * Initialize the resources
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
	public Image loadImage(String name){
		Image img = null;
		if (cache.containsKey(name)){
			img = cache.get(name);
		} else {
			img =  new ImageIcon(this.getClass().getClassLoader().getResource(name)).getImage();
			cache.put(name, img);
		}
		return img;
	}
	
	public GInput getInput(){
		return input;
	}
		
}
