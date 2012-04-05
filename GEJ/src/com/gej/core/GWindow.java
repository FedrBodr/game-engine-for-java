package com.gej.core;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class GWindow {
	
	JFrame frame = null;
	
	public GWindow(Game gm, boolean fullscreen){
		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo laf : lafs){
			if (laf.getName().startsWith("Nimbus")){
				try {
					UIManager.setLookAndFeel(laf.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		}
		frame = new JFrame(Global.TITLE);
		frame.add(gm);
		frame.setSize(Global.WIDTH, Global.HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		if (fullscreen){
			frame.setUndecorated(true);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = genv.getDefaultScreenDevice();
			device.setFullScreenWindow((Window)frame);
			DisplayMode[] modes = device.getDisplayModes();
			for (DisplayMode mode : modes){
				if (mode.getWidth()==Global.WIDTH && mode.getHeight()==Global.HEIGHT){
					device.setDisplayMode(mode);
				}
			}
		} else {
			frame.setLocationRelativeTo(null);
		}
	}
	
	public Window getWindow(){
		return (Window)frame;
	}

}
