package com.gej.core;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import com.gej.input.GInput;

/**
 * Runs a game as an Applet by loading the class dynamically at runtime.
 * A class should be made extending this class by pointing the path of
 * the class which extends like this.
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
public class GApplet extends JApplet implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 1994280929713148311L;

    String gmname = "";
    Class<?> gmClass = null;

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
        if (gmname == null || gmname == "") {
            gmname = JOptionPane.showInputDialog("Enter game class name");
        }
        try {
            gmClass = getClass().getClassLoader().loadClass(gmname);
            new Thread(this).run();
        } catch (ClassNotFoundException e) {
            System.err.println("Error finding class : " + gmname);
            e.printStackTrace();
        }
    }
    
    public void run(){
        try {
            Game game = (Game)gmClass.newInstance();
            game.setInput(GInput.install(game));
            GInput.install(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Stops the applet
     */
    public void stop(){
        Game.stopGame();
    }

}
