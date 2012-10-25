package com.gej.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Constitutes all the information to take input polling for a game. You could
 * use the static methods of the GKeyBoard and GMouse classes to check the key
 * input.
 * 
 * @author Sri Harsha Chilakapati
 */
public class GInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    
    /**
     * Input detection mode
     * 
     * @author Sri Harsha Chilakapati
     */
    public static enum Behaviour {
        NORMAL, INITIAL_DETECT
    }

    /**
     * Install's GInput as input listener to the specified component
     * 
     * @param c The component which is the event source.
     * @return The GInput class for the event source.
     */
    public static GInput install(Component c){
        return new GInput(c);
    }

    /** A list of key actions */
    public static Action[] KEY_ACTIONS   = new Action[GKeyBoard.NUM_KEY_CODES];
    /** A list of mouse actions */
    public static Action[] MOUSE_ACTIONS = new Action[GMouse.NUM_MOUSE_CODES];
    
    // Should use the static install method
    private GInput(Component c){
        c.addKeyListener(this);
        c.addMouseListener(this);
        c.addMouseMotionListener(this);
        c.addMouseWheelListener(this);
        c.setFocusTraversalKeysEnabled(false);
        // Create actions
        for (int i=0; i<KEY_ACTIONS.length; i++){
            KEY_ACTIONS[i] = new Action();
        }
        for (int i=0; i<MOUSE_ACTIONS.length; i++){
            MOUSE_ACTIONS[i] = new Action();
        }
        MOUSE_ACTIONS[GMouse.MOUSE_CLICKED].setDetectionType(Behaviour.INITIAL_DETECT);
    }
    
    /**
     * Reset's the input information
     */
    public void reset(){
        for (int i=0; i<KEY_ACTIONS.length; i++){
            KEY_ACTIONS[i].reset();
        }
        for (int i=0; i<MOUSE_ACTIONS.length; i++){
            MOUSE_ACTIONS[i].reset();
        }
    }
    
    /**
     * Returns the name of the keyCode
     */
    public static String getKeyName(int keyCode){
        if (keyCode==602){
            return "NO KEY";
        }
        return KeyEvent.getKeyText(keyCode);
    }
    
    /**
     * The name of the mouseCode
     */
    public static String getMouseName(int mouseCode){
        switch (mouseCode){
            case GMouse.MOUSE_BUTTON_1:   return "MOUSE BUTTON 1";
            case GMouse.MOUSE_BUTTON_2:   return "MOUSE BUTTON 2";
            case GMouse.MOUSE_BUTTON_3:   return "MOUSE BUTTON 3";
            case GMouse.MOUSE_MOVE_DOWN:  return "MOUSE MOVED DOWN";
            case GMouse.MOUSE_MOVE_LEFT:  return "MOUSE MOVED LEFT";
            case GMouse.MOUSE_MOVE_RIGHT: return "MOUSE MOVED RIGHT";
            case GMouse.MOUSE_MOVE_UP:    return "MOUSE MOVED UP";
            case GMouse.MOUSE_WHEEL_UP:   return "MOUSE WHEEL MOVED UP";
            case GMouse.MOUSE_WHEEL_DOWN: return "MOUSE WHEEL MOVED DOWN";
            default:                     return "NO RECENT MOUSE ACTION";
        }
    }
    
    // Generate mouse code from the mouse event
    private static int getMouseCode(MouseEvent e){
        switch (e.getButton()){
            case MouseEvent.BUTTON1: return GMouse.MOUSE_BUTTON_1;
            case MouseEvent.BUTTON2: return GMouse.MOUSE_BUTTON_2;
            case MouseEvent.BUTTON3: return GMouse.MOUSE_BUTTON_3;
            default:                 return -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void mouseWheelMoved(MouseWheelEvent e){
        int amount = e.getWheelRotation();
        if (amount<0){
            MOUSE_ACTIONS[GMouse.MOUSE_WHEEL_UP].press(Math.abs(amount));
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_WHEEL_UP;
        } else {
            MOUSE_ACTIONS[GMouse.MOUSE_WHEEL_DOWN].press(Math.abs(amount));
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_WHEEL_DOWN;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void mouseDragged(MouseEvent e){
        mouseMoved(e);
    }

    /**
     * {@inheritDoc}
     */
    public void mouseMoved(MouseEvent e){
        int dx = e.getX() - GMouse.MOUSE_X;
        int dy = e.getY() - GMouse.MOUSE_Y;
        if (dx<0){
            MOUSE_ACTIONS[GMouse.MOUSE_MOVE_LEFT].press(Math.abs(dx));
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_MOVE_LEFT;
        } else {
            MOUSE_ACTIONS[GMouse.MOUSE_MOVE_RIGHT].press(Math.abs(dx));
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_MOVE_RIGHT;
        }
        if (dy<0){
            MOUSE_ACTIONS[GMouse.MOUSE_MOVE_UP].press(Math.abs(dy));
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_MOVE_UP;
        } else {
            MOUSE_ACTIONS[GMouse.MOUSE_MOVE_DOWN].press(Math.abs(dy));
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_MOVE_DOWN;
        }
        GMouse.MOUSE_X = e.getX();
        GMouse.MOUSE_Y = e.getY();
    }

    /**
     * {@inheritDoc}
     */
    public void mouseClicked(MouseEvent e){
        Action click = MOUSE_ACTIONS[GMouse.MOUSE_CLICKED];
        if (!click.isPressed()){
            click.press();
            GMouse.MOUSE_CLICK_X = e.getX();
            GMouse.MOUSE_CLICK_Y = e.getY();
            GMouse.RECENT_MOUSE_CODE = GMouse.MOUSE_CLICKED;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void mousePressed(MouseEvent e){
        MOUSE_ACTIONS[getMouseCode(e)].press();
        GMouse.RECENT_MOUSE_CODE = getMouseCode(e);
    }

    /**
     * {@inheritDoc}
     */
    public void mouseReleased(MouseEvent e){
        MOUSE_ACTIONS[getMouseCode(e)].press();
    }

    /**
     * {@inheritDoc}
     */
    public void mouseEntered(MouseEvent e){
        mouseMoved(e);
    }

    /**
     * {@inheritDoc}
     */
    public void mouseExited(MouseEvent e){
        mouseMoved(e);
    }
    
    /**
     * {@inheritDoc}
     */
    public void keyTyped(KeyEvent e){
        e.consume();
    }

    /**
     * {@inheritDoc}
     */
    public void keyPressed(KeyEvent e){
        KEY_ACTIONS[e.getKeyCode()].press();
        GKeyBoard.RECENT_KEY_CODE = e.getKeyCode();
        e.consume();
    }

    /**
     * {@inheritDoc}
     */
    public void keyReleased(KeyEvent e){
        KEY_ACTIONS[e.getKeyCode()].release();
        e.consume();
    }

}
