package com.gej.input;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.gej.object.GAction;

public class GInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	public static final Cursor INVISIBLE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(0,0), "Invisible");
	
	public static final int MOUSE_MOVE_LEFT  = 0;
    public static final int MOUSE_MOVE_RIGHT = 1;
    public static final int MOUSE_MOVE_UP    = 2;
    public static final int MOUSE_MOVE_DOWN  = 3;
    public static final int MOUSE_WHEEL_UP   = 4;
    public static final int MOUSE_WHEEL_DOWN = 5;
    public static final int MOUSE_BUTTON_1   = 6;
    public static final int MOUSE_BUTTON_2   = 7;
    public static final int MOUSE_BUTTON_3   = 8;
    
    private static final int NUM_MOUSE_CODES = 9;

    private static final int NUM_KEY_CODES = 600;

    private GAction[] keyActions = new GAction[NUM_KEY_CODES];
    private GAction[] mouseActions = new GAction[NUM_MOUSE_CODES];

    private Point mouseLocation;
    private Point centerLocation;
    private Component comp;
    private Robot robot;
    private boolean isRecentering;
    
    public GInput(Component comp) {
        this.comp = comp;
        mouseLocation = new Point();
        centerLocation = new Point();
        comp.addKeyListener(this);
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
        comp.addMouseWheelListener(this);
        comp.setFocusTraversalKeysEnabled(false);
    }
    
    public void setRelativeMouseMode(boolean mode) {
        if (mode == isRelativeMouseMode()) {
            return;
        }
        if (mode) {
            try {
                robot = new Robot();
                recenterMouse();
            }
            catch (AWTException ex) {
                robot = null;
            }
        }
        else {
            robot = null;
        }
    }
    
    public boolean isRelativeMouseMode() {
        return (robot != null);
    }

    private synchronized void recenterMouse() {
        if (robot != null && comp.isShowing()) {
            centerLocation.x = comp.getWidth() / 2;
            centerLocation.y = comp.getHeight() / 2;
            SwingUtilities.convertPointToScreen(centerLocation,
                comp);
            isRecentering = true;
            robot.mouseMove(centerLocation.x, centerLocation.y);
        }
    }
    
    public void mapToKey(GAction gameAction, int keyCode) {
        keyActions[keyCode] = gameAction;
    }

    public void mapToMouse(GAction gameAction, int mouseCode) {
        mouseActions[mouseCode] = gameAction;
    }

    public void clearMap(GAction gameAction) {
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] == gameAction) {
                keyActions[i] = null;
            }
        }
        for (int i=0; i<mouseActions.length; i++) {
            if (mouseActions[i] == gameAction) {
                mouseActions[i] = null;
            }
        }
        gameAction.reset();
    }

    public ArrayList<String> getMaps(GAction gameCode) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] == gameCode) {
                list.add(getKeyName(i));
            }
        }
        for (int i=0; i<mouseActions.length; i++) {
            if (mouseActions[i] == gameCode) {
                list.add(getMouseName(i));
            }
        }
        return list;
    }

    public void resetAllGameActions() {
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] != null) {
                keyActions[i].reset();
            }
        }
        for (int i=0; i<mouseActions.length; i++) {
            if (mouseActions[i] != null) {
                mouseActions[i].reset();
            }
        }
    }

    public static String getKeyName(int keyCode) {
        return KeyEvent.getKeyText(keyCode);
    }
    
    public static String getMouseName(int mouseCode) {
        switch (mouseCode) {
            case MOUSE_MOVE_LEFT: return "Mouse Left";
            case MOUSE_MOVE_RIGHT: return "Mouse Right";
            case MOUSE_MOVE_UP: return "Mouse Up";
            case MOUSE_MOVE_DOWN: return "Mouse Down";
            case MOUSE_WHEEL_UP: return "Mouse Wheel Up";
            case MOUSE_WHEEL_DOWN: return "Mouse Wheel Down";
            case MOUSE_BUTTON_1: return "Mouse Button 1";
            case MOUSE_BUTTON_2: return "Mouse Button 2";
            case MOUSE_BUTTON_3: return "Mouse Button 3";
            default: return "Unknown mouse code " + mouseCode;
        }
    }

    public int getMouseX() {
        return mouseLocation.x;
    }

    public int getMouseY() {
        return mouseLocation.y;
    }

    private GAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }

    public static int getMouseButtonCode(MouseEvent e) {
        switch (e.getButton()) {
           case MouseEvent.BUTTON1: return MOUSE_BUTTON_1;
           case MouseEvent.BUTTON2: return MOUSE_BUTTON_2;
           case MouseEvent.BUTTON3: return MOUSE_BUTTON_3;
           default                : return -1;
        }
    }

    private GAction getMouseButtonAction(MouseEvent e) {
        int mouseCode = getMouseButtonCode(e);
        if (mouseCode != -1) {
             return mouseActions[mouseCode];
        }
        else {
             return null;
        }
    }

    public void setCursor(Cursor c){
    	comp.setCursor(c);
    }
    
    public void keyPressed(KeyEvent e) {
        GAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
        e.consume();
    }

    public void keyReleased(KeyEvent e) {
        GAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    public void mousePressed(MouseEvent e) {
        GAction gameAction = getMouseButtonAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
    }

    public void mouseReleased(MouseEvent e) {
        GAction gameAction = getMouseButtonAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
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
        if (isRecentering && centerLocation.x == e.getX() && centerLocation.y == e.getY()) {
            isRecentering = false;
        } else {
            int dx = e.getX() - mouseLocation.x;
            int dy = e.getY() - mouseLocation.y;
            mouseHelper(MOUSE_MOVE_LEFT, MOUSE_MOVE_RIGHT, dx);
            mouseHelper(MOUSE_MOVE_UP, MOUSE_MOVE_DOWN, dy);
            if (isRelativeMouseMode()) {
                recenterMouse();
            }
        }
        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseHelper(MOUSE_WHEEL_UP, MOUSE_WHEEL_DOWN, e.getWheelRotation());
    }

    private void mouseHelper(int codeNeg, int codePos, int amount) {
        GAction gameAction;
        if (amount < 0) {
            gameAction = mouseActions[codeNeg];
        } else {
            gameAction = mouseActions[codePos];
        }
        if (gameAction != null) {
            gameAction.press(Math.abs(amount));
            gameAction.release();
        }
    }

}
