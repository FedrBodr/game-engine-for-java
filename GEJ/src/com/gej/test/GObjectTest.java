package com.gej.test;

import java.awt.Graphics2D;

import com.gej.core.Game;
import com.gej.object.GObject;

public class GObjectTest extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3353991379735524005L;
	
	GObject box = null;
	
	{
		setFullScreen(true);
    }
		
	@Override
	public void initResources(){
		box = new GObject(loadImage("resources/box.png"));
		box.setVelocityX(0.08f);
        box.setVelocityY(0.08f);
	}
	
	@Override
	public void update(long elapsedTime){
		if (box!=null){
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
		} else {
			initResources();
		}
	}
	
	@Override
	public void render(Graphics2D g){
		if (box!=null){			
			g.drawImage(box.getImage(), Math.round(box.getX()), Math.round(box.getY()), null);
		}
	}
	
	public static void main(String[] args){
		new GObjectTest();
	}

}
