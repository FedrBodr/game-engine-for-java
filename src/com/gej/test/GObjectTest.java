package com.gej.test;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GInput;
import com.gej.input.GKeyBoard;
import com.gej.input.GMouse;
import com.gej.object.GObject;

public class GObjectTest extends Game {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3353991379735524005L;

    GObject box = null;

    @Override
    public void initResources(){
        box = new GObject(loadImage("resources/box.png"));
        box.setVelocityX(0.08f);
        box.setVelocityY(0.08f);
    }

    @Override
    public void update(long elapsedTime){
        if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        if (box.getX() < 0) {
            box.setVelocityX(Math.abs(box.getVelocityX()));
        } else if (box.getX() + box.getWidth() >= getWidth()) {
            box.setVelocityX(-Math.abs(box.getVelocityX()));
        }
        if (box.getY() < 0) {
            box.setVelocityY(Math.abs(box.getVelocityY()));
        } else if (box.getY() + box.getHeight() >= getHeight()) {
            box.setVelocityY(-Math.abs(box.getVelocityY()));
        }
        box.superUpdate(elapsedTime);
        box.move();
    }

    @Override
    public void render(Graphics2D g){
        g.drawImage(box.getImage(), Math.round(box.getX()), Math.round(box.getY()), null);
        if (GMouse.isMouseClicked(GInput.MOUSE_BUTTON_1)) {
            g.drawString("Mouse Clicked", 15, 15);
        }
        if (box.isClicked()) {
            g.drawString("on object", 100, 15);
        }
    }

    public static void main(String[] args){
        GWindow.setup(new GObjectTest());
    }

}
