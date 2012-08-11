package com.gej.core;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

/**
 * Runs a game as an Applet by loading the class dynamically at runtime. The
 * name of the class should be specified as a parameter named "game".
 * 
 * @author Sri Harsha Chilakapati
 */
public class GApplet extends JApplet {

    /**
     * 
     */
    private static final long serialVersionUID = 1994280929713148311L;

    String gmname = "";

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
            Class<?> gameClass = getClass().getClassLoader().loadClass(gmname);
            Game game = (Game)gameClass.newInstance();
            add(game);
            game.setFocusable(true);
            setVisible(true);
            setIgnoreRepaint(true);
        } catch (ClassNotFoundException e) {
            System.err.println("Error finding class : " + gmname);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.err.println("Error loading class : " + gmname);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Error loading class : " + gmname);
            e.printStackTrace();
        }
    }

}
