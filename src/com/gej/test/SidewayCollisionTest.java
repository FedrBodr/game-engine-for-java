package com.gej.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.input.GInput;
import com.gej.object.GObject;

public class SidewayCollisionTest extends Game {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -3368876887836736254L;
    
    GObject object1 = null;
    GObject object2 = null;
    
    GInput input = null;
    
    Image background = null;
    
    @Override
    public void initResources(){
        background = loadImage("resources/back.png");
        object1 = new GObject(loadImage("resources/box.png"));
        object1.setX(Global.WIDTH / 2 - 32);
        object1.setY(Global.HEIGHT / 2 - 32);
        object2 = new GObject(loadImage("resources/box2.png"));
        input = new GInput(this);
        input.setCursor(GInput.INVISIBLE_CURSOR);
        object2.setX(180);
        object2.setY(180);
        Global.FRAMES_PER_SECOND = 150;
    }
    
    @Override
    public void update(long elapsedTime){
        if (input.getMouseX() != 0 && input.getMouseY() != 0){
            object2.setX(input.getMouseX());
            object2.setY(input.getMouseY());
        }
    }
    
    @Override
    public void render(Graphics2D g){
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(object1.getImage(), Math.round(object1.getX()), Math.round(object1.getY()), null);
        g.drawImage(object2.getImage(), Math.round(object2.getX()), Math.round(object2.getY()), null);
        g.setColor(Color.WHITE);
        g.draw(object2.getBounds());
        g.setFont(new Font("Courier New", Font.BOLD, 12));
        g.drawString("Top    : " + object2.isTopCollision(object1), 15, 25);
        g.drawString("Bottom : " + object2.isBottomCollision(object1), 15, 50);
        g.drawString("Left   : " + object2.isLeftCollision(object1), 15, 75);
        g.drawString("Right  : " + object2.isRightCollision(object1), 15, 100);
    }
    
    public static void main(String[] args){
        GWindow.setup(new SidewayCollisionTest());
    }
    
}
