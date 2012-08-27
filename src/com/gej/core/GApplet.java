package com.gej.core;

import javax.swing.JApplet;

/**
 * Runs a game as an Applet by loading the class dynamically at runtime.
 * A class should be made extending this class by pointing the path of
 * the class which extends like this.<br><br>
 * 
 * NOTE: This class launches a window from the applet so that we can also
 *       use the fullscreen mode.<br><br>
 * 
 * <pre>
 * public class MyGameApplet extends GApplet {
 * 
 *     public MyGameApplet(){
 *         super("mypackage.MyGame");
 *     }
 * 
 * }
 * </pre>
 * 
 * @author Sri Harsha Chilakapati
 */
public class GApplet extends JApplet {

    /**
     * 
     */
    private static final long serialVersionUID = 1994280929713148311L;

    // Private variables
    private String gmname = "";
    private Class<?> gmClass = null;
    private Game game = null;
    
    /**
     * Construct an applet with a game.
     * @param gmname The name of the game class with package name
     */
    public GApplet(String gmname) {
        this.gmname = gmname;
    }

    /**
     * Initializes the applet mode
     */
    public void init(){
        Global.WEB_MODE = true;
        try {
            gmClass = getClass().getClassLoader().loadClass(gmname);
            game = (Game)gmClass.newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("Error finding class : " + gmname);
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Starts the game only if the game is not running.
     */
    public final void showGame(){
        if (!Game.isRunning()){
            GWindow.setup(game);
        }
    }

}
