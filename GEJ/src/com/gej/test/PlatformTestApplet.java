package com.gej.test;

import javax.swing.JApplet;

import com.gej.core.GWindow;

public class PlatformTestApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 79331933730276748L;
	
	public void init(){
		GWindow.setup(new PlatformTest());
	}

}
