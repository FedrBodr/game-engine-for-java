package com.gej.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.gej.core.EventListener;
import com.gej.core.GEvent;

public class GTimer implements ActionListener {

	Timer timer = null;
	
	EventListener listener = null;
	
	public GTimer(EventListener listener, int time){
		this.listener = listener;
		timer = new Timer(time, this);
	}
	
	public synchronized void start(){
		timer.start();
	}
	
	public synchronized void stop(){
		timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		listener.processEvent(new GEvent(GEvent.TIMER_EVENT));
	}
	
}
