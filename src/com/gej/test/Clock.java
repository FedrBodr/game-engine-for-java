package com.gej.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.core.Global;
import com.gej.graphics.Background;
import com.gej.graphics.GFont;
import com.gej.graphics.GFontAdvanced;
import com.gej.input.GKeyBoard;
import com.gej.util.ImageTool;

public class Clock extends Game {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -6266288210685998880L;
    
    long milliseconds = 0;
    long seconds = 0;
    long minutes = 0;
    long hours = 0;
    
    GFont font = null;
    
    @Override
    public void initResources(){
        Global.WIDTH = 400;
        Global.HEIGHT = 50;
        Global.FULLSCREEN = true;
        Background.setBackground(ImageTool.getColoredImage(Color.GRAY, Global.WIDTH, Global.HEIGHT));
        font = GFontAdvanced.getFont(loadImage("ImageFonts/font_blue.png"), "ImageFonts/DefFontDescriptor.txt");
    }
    
    @Override
    public void update(long elapsedTime){
        Global.TITLE = "TIME : " + hours + " : " + minutes + " : " + seconds + " : " + milliseconds;
        milliseconds += elapsedTime;
        if (milliseconds >= 1000){
            seconds++;
            Global.HEIGHT = 150;
            milliseconds -= 1000;
        }
        if (seconds >= 60){
            minutes++;
            seconds -= 60;
        }
        if (minutes >= 60){
            hours++;
            minutes -= 60;
        }
        if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)){
            System.exit(0);
        }
    }
    
    @Override
    public void render(Graphics2D g){
        Background.render(g);
        font.renderText(Global.TITLE, g, 15, 15);
    }
    
    public static void main(String[] args){
        GWindow.setup(new Clock());
    }
    
}
