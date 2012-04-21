package com.gej.test;

import java.awt.Graphics2D;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GInput;
import com.gej.object.GAction;
import com.gej.object.GObject;

public class GObjectTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3353991379735524005L;
	
	GObject box = null;
	
	GAction mouse_1 = null;
	
	@Override
	public void initResources(){
		box = new GObject(loadImage("resources/box.png"));
		box.setVelocityX(0.08f);
        box.setVelocityY(0.08f);
        mouse_1 = new GAction("MOUSE1");
        GInput input = new GInput(this);
        input.mapToMouse(mouse_1, GInput.MOUSE_BUTTON_1);
	}
	
	@Override
	public void update(long elapsedTime){
		if (mouse_1.isPressed()){
			System.exit(0);
		}
	    if (box.getX() < 0) {
	        box.setVelocityX(Math.abs(box.getVelocityX()));
	    } else if (box.getX() + box.getWidth() >= getWidth()){
	        box.setVelocityX(-Math.abs(box.getVelocityX()));
	    }
	    if (box.getY() < 0) {
	        box.setVelocityY(Math.abs(box.getVelocityY()));
	    } else if (box.getY() + box.getHeight() >= getHeight()){
	        box.setVelocityY(-Math.abs(box.getVelocityY()));
	    }
	    box.update(elapsedTime);
	}
	
	@Override
	public void render(Graphics2D g){
		g.drawImage(box.getImage(), Math.round(box.getX()), Math.round(box.getY()), null);
	}
	
	public static void main(String[] args){
		GWindow.setup(new GObjectTest());
	}

}
