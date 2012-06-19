package com.gej.test;

import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GKeyBoard;
import com.gej.sound.WavPlayer;
import com.gej.sound.WavSound;

public class WavPlayerTest extends Game {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7593536027988708002L;

    WavSound mysound = null;

    public void initResources(){
        mysound = WavPlayer.loadSound("resources/prize.wav");
    }

    public void update(long elapsedTime){
        if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        } else if (GKeyBoard.isPressed(KeyEvent.VK_P)) {
            mysound.play();
        } else if (GKeyBoard.isPressed(KeyEvent.VK_SPACE)) {
            mysound.setLooping(!mysound.isLooping());
        } else if (GKeyBoard.isPressed(KeyEvent.VK_S)) {
            mysound.stop();
        }
    }

    public static void main(String[] args){
        GWindow.setup(new WavPlayerTest());
    }

}
