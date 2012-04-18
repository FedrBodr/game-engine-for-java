package com.gej.core;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * This class is used to wrap the game in a window.
 * Could be used to switch to fullscreen mode. Please
 * note that it is integrated into the Game class.
 * You need not invoke it. So, this class is unused
 * by the end user.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GWindow implements WindowListener {
	
	// Private variables
	private JFrame frame = null;
	private boolean fullscreen = false;
	private GraphicsDevice device = null;
	
	/**
	 * Constructs a new game window with the game and
	 * a value for the fullscreen state.
	 * 
	 * @param gm The game object to be displayed
	 * @param fullscreen The fullscreen state of the game
	 */
	public GWindow(Game gm, boolean fullscreen){
		// Create a new frame
		this.fullscreen = fullscreen;
		frame = new JFrame(Global.TITLE);
		frame.add(gm);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		// If fullscreen, start up in fullscreen mode
		if (fullscreen){
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			device = genv.getDefaultScreenDevice();
			frame.setUndecorated(true);
			device.setFullScreenWindow((Window)frame);
			// Select the resolution specified in the Global class
			DisplayMode[] modes = device.getDisplayModes();
			for (DisplayMode mode : modes){
				if (mode.getWidth()==Global.WIDTH && mode.getHeight()==Global.HEIGHT && mode.getBitDepth()==32){
					device.setDisplayMode(mode);
				}
			}
		} else {
			// Start the window in normal state
			Insets i = frame.getInsets();
			int w = Global.WIDTH + i.left + i.right;
			int h = Global.HEIGHT + i.top + i.bottom;
			frame.setSize(w, h);
			frame.setLocationRelativeTo(null);
		}
		// Display the window
		frame.setVisible(true);
	}
	
	/**
	 * Get the current width of this window. 
	 * @return The current width of this window
	 */
	public int getWidth(){
		return frame.getWidth();
	}
	
	/**
	 * Get the current height of this window.
	 * @return The current height of this window
	 */
	public int getHeight(){
		return frame.getHeight();
	}
	
	/**
	 * Get the instance of java.awt.window used by this object
	 * @return The instance of java.awt.window
	 */
	public Window getWindow(){
		if (!fullscreen){
			return (Window)frame;
		} else {
			return device.getFullScreenWindow();
		}
	}
	
	/**
	 * Sets the title to be displayed in the window border
	 * @param title The title of the window
	 */
	public void setTitle(String title){
		frame.setTitle(title);
	}
	
	// From the WindowListener interface

	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		if (fullscreen){
			device.getFullScreenWindow().dispose();
			device.setFullScreenWindow(null);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}
	
}
