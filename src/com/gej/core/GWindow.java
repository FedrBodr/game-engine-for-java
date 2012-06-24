package com.gej.core;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.Timer;

/**
 * This class acts as a configured JFrame which is equipped with special
 * abilities. It can be configured to start as a full screen application, or a
 * windowed application. It is invoked like this.<br>
 * 
 * <pre>
 * GWindow.setup(Game game, String title);
 * 
 * GWindow.setup(Game game);
 * </pre>
 * 
 * where the values provided are the default ones. The constructors are
 * discussed again at their respective positions.<br>
 * <br>
 * It's other features are..<br>
 * <br>
 * <ul>
 * <li>FullScreen, using the FullScreen Exclusive Mode by Sun.</li>
 * <li>Uses the Java Metal theme's Title bar</li>
 * <li>Monitors and changes the window size, title, fullscreen state by scanning
 * the Global class
 * <li>Confirmation on the close button</li>
 * <li>Also includes a fix for repaint issue on older machines</li>
 * </ul>
 * 
 * @author Sri Harsha Chilakapati
 */
public class GWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
    private static final long serialVersionUID = 449311978071928834L;

    /** The timer object we use to re-change the title, width and height */
    Timer timer = null;
    /** The Game object (passed as an argument) */
    Game game = null;

    /** The width of resolution of the screen */
    public static int RESOLUTION_X = Global.WIDTH;
    /** The height of resolution of the screen */
    public static int RESOLUTION_Y = Global.HEIGHT;

    /** The screen device */
    GraphicsDevice device = null;

    boolean fullscreen = false;

    /**
     * This method fixes the repaint issue (flickering) on old machines with
     * latest JRE. It is called automatically by the timer
     */
    public final void repaintFix(){
        setIgnoreRepaint(true);
    }

    /**
     * This is our default constructor. Create instances by using the setup()
     * method. The arguments to be passed except the Game are just the default
     * one's. They can be changed during the game play. (use global.HEIGHT =
     * 240;)
     * 
     * @param game The Game object to be displayed
     * @param title The default title (also used on the task bar)
     * @param width The default window width
     * @param height The default window height
     */
    private GWindow(Game game, String title, int width, int height) {
        // Configure and create the title bar
        super(title);
        setUndecorated(true);
        setResizable(false);
        setTitle(title);
        setSize(width, height);
        // Center the window on the screen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        // Set and add the game object
        this.game = game;
        add(game);
        game.setFocusable(true);
        setVisible(true);
        // Fix the repaint issue on some older machines
        repaintFix();
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        // Create and start the timer
        timer = new Timer(500, this);
        timer.start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                int retVal = JOptionPane.showOptionDialog(null, "Are you sure want to exit?", Global.TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {
                        "Sure, close it!", "Sorry! Go Back!" }, "Sorry! Go Back!");
                if (retVal == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                } else {
                    // Do nothing
                }
            }
        });
    }

    /**
     * Called by the timer each second to update the window state, such as
     * title, window size, resolution in full-screen state etc.,
     */
    public final void actionPerformed(ActionEvent e){
        // Set it's properties and center the window on screen
        setTitle(Global.TITLE);
        if (!Global.FULLSCREEN) {
            setLocationRelativeTo(null);
            setSize(Global.WIDTH, Global.HEIGHT);
        }
        // Check and change the full screen state
        setFullScreen(Global.FULLSCREEN);
        // Resolution
        DisplayMode mode = device.getDisplayMode();
        RESOLUTION_X = mode.getWidth();
        RESOLUTION_Y = mode.getHeight();
    }

    /**
     * This method changes the state of full screen. To change the full screen
     * state from the game use the global object.
     * 
     * <pre>
     * Global.FULLSCREEN = true;
     * </pre>
     * 
     * @param bool The boolean value which is used to switch full screen state.
     */
    public final void setFullScreen(boolean bool){
        // If true, switch to FullScreen
        if (bool == true && fullscreen == false) {
            getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            device.setFullScreenWindow((Window) this);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            if (device.isDisplayChangeSupported()) {
                // Get the available display modes
                DisplayMode[] modes = device.getDisplayModes();
                // Cycle and set the best display mode
                for (DisplayMode mode : modes) {
                    //System.out.println(mode.getWidth() + "     " + mode.getHeight() + "     " + mode.getBitDepth());
                    if (mode.getWidth() == Global.WIDTH
                            && mode.getHeight() == Global.HEIGHT
                            && (mode.getBitDepth() == 32 || mode.getBitDepth() == DisplayMode.BIT_DEPTH_MULTI)) {
                        device.setDisplayMode(mode);
                    }
                }
            }
            // Center the mouse using the robot class
            try {
                Robot robot = new Robot();
                robot.mouseMove(dim.width / 4, dim.height / 4);
                fullscreen = true;
            } catch (Exception e) {
            }
        } else if (bool == false && fullscreen == true) {
            // else change to windowed mode
            getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            device.setFullScreenWindow(null);
            fullscreen = false;
        }
    }

    /**
     * Creates a new GWindow instance and packs a game into it.
     * 
     * @param game The game to be packed.
     * @return The GWindow instance.
     */
    public static final GWindow setup(Game game){
        return new GWindow(game, Global.TITLE, Global.WIDTH, Global.HEIGHT);
    }

}
