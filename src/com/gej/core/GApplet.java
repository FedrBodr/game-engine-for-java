package com.gej.core;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

/**
 * Runs a game as an Applet by loading the class dynamically at runtime.
 * The name of the class should be specified as a parameter named game-class.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GApplet extends JApplet {

    /**
     * 
     */
    private static final long serialVersionUID = 1994280929713148311L;
    
    /**
     * Initializes the applet mode
     */
    public void init(){
        String gmname = getParameter("game-class");
        if (gmname==null || gmname==""){
            gmname = JOptionPane.showInputDialog("Enter game class name");
        }
        try {
            Class<?> gameClass = getClass().getClassLoader().loadClass(gmname);
            add((Game)gameClass.newInstance());
        } catch (ClassNotFoundException e) {
            System.err.println("Error finding class : " + gmname);
        } catch (InstantiationException e) {
            System.err.println("Error loading class : " + gmname);
        } catch (IllegalAccessException e) {
            System.err.println("Error loading class : " + gmname);
        }
    }

}
