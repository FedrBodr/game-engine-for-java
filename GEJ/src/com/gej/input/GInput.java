package com.gej.input;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.gej.util.GUtil;
import com.gej.util.ImageTool;

/**
 * Constitutes all the information to take input polling
 * for a game. You could use the static methods of the
 * GKeyBoard and GMouse classes to check the key input.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GInput implements KeyListener, MouseListener, MouseMotionListener {
	
	/**
	 * Install's GInput as input listener to the specified component
	 * @param c The component which is the event source.
	 * @return The GInput class for the event source.
	 */
	public static GInput install(Component c){
		return new GInput(c);
	}
	
	/**
	 * An invisible cursor made to hide the cursor in the
	 * game panel.
	 */
	public static final Cursor INVISIBLE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(ImageTool.getEmptyImage(1, 1), new Point(0, 0), "Invisible Cursor");
	
	/** The mouse code for button-1 */
    public static final int MOUSE_BUTTON_1   = 0;
    /** The mouse code for button-2 */
    public static final int MOUSE_BUTTON_2   = 1;
    /** The mouse code for button-3 */
    public static final int MOUSE_BUTTON_3   = 2;
    /** The mouse code for mouse-movement */
    public static final int MOUSE_MOVED      = 3;
    
    // Some private variables.
    private Point mouseLocation;
    private Component comp;
    
    /**
     * The default constructor. Makes this class listen the
     * events (key, mouse)
     * @param comp The component which is the event source.
     */
    public GInput(Component comp) {
        this.comp = comp;
        mouseLocation = new Point();
        comp.addKeyListener(this);
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
        comp.setFocusTraversalKeysEnabled(false);
    }

    /**
     * Returns the text of the key code
     * @param keyCode The code of the key required
     * @return The String representation
     */
    public static String getKeyName(int keyCode) {
        return KeyEvent.getKeyText(keyCode);
    }
    
    /**
     * Returns the text of the mouse code
     * @param mouseCode The code of the mouse
     * @return The string representation
     */
    public static String getMouseName(int mouseCode) {
        switch (mouseCode) {
            case MOUSE_BUTTON_1: return "Mouse Button 1";
            case MOUSE_BUTTON_2: return "Mouse Button 2";
            case MOUSE_BUTTON_3: return "Mouse Button 3";
            case MOUSE_MOVED   : return "Mouse Moved";
            default: return "Unknown mouse code " + mouseCode;
        }
    }

    /**
     * Returns the x-coordinate of the mouse in the game window.
     * @return The x-coordinate of mouse
     */
    public int getMouseX() {
        return mouseLocation.x;
    }

    /**
     * Returns the y-coordinate of the mouse in the game window.
     * @return The y-coordinate of mouse
     */
    public int getMouseY() {
        return mouseLocation.y;
    }

    /**
     * Converts the mouse button code from the MouseEvent
     * @param e The mouse event
     * @return The mouse button code. -1 if not a button.
     */
    public static int getMouseButtonCode(MouseEvent e) {
        switch (e.getButton()) {
           case MouseEvent.BUTTON1: return MOUSE_BUTTON_1;
           case MouseEvent.BUTTON2: return MOUSE_BUTTON_2;
           case MouseEvent.BUTTON3: return MOUSE_BUTTON_3;
           default                : return -1;
        }
    }

    /**
     * Set's the cursor of the component
     * @param c The cursor object
     */
    public void setCursor(Cursor c){
    	comp.setCursor(c);
    }
    
    public void keyPressed(KeyEvent e) {
    	GKeyBoard.KeyPress(e.getKeyCode());
        e.consume();
    }

    public void keyReleased(KeyEvent e) {
    	GKeyBoard.KeyReleased(e.getKeyCode());
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    public void mousePressed(MouseEvent e) {
    	GMouse.setID(getMouseButtonCode(e), true);
    }

    public void mouseReleased(MouseEvent e) {
    	GMouse.setID(getMouseButtonCode(e), false);
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public synchronized void mouseMoved(MouseEvent e) {
    	GUtil.runInSeperateThread(new Runnable(){
    		public void run(){
    			try {
    				GMouse.setID(GInput.MOUSE_MOVED, true);
    				Thread.sleep(150);
    				GMouse.setID(GInput.MOUSE_MOVED, false);
    			} catch (Exception e){}
    		}
    	});
        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();
        GMouse.MOUSE_X = e.getX();
        GMouse.MOUSE_Y = e.getY();
    }

}
