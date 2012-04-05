package com.gej.object;

public class GAction {
	
	public static final int NORMAL_KEY_PRESS       = 0;
	public static final int INITIAL_KEY_PRESS_ONLY = 1;
	
	public static final int STATE_RELEASED            = 0;
	public static final int STATE_PRESSED             = 1;
	public static final int STATE_WAITING_FOR_RELEASE = 2;
	
	String name;
	int    behaviour;
	int    amount;
	int    state;
	
	public GAction(String name, int behaviour){
		this.name = name;
		this.behaviour = behaviour;
	}
	
	public GAction(String name){
		this(name, NORMAL_KEY_PRESS);
	}

	public void reset(){
		state = STATE_RELEASED;
		amount = 0;
	}
	
	public synchronized void press(int amount){
		if (state!=STATE_WAITING_FOR_RELEASE){
			state = STATE_PRESSED;
			this.amount = amount;
		}
	}
	
	public synchronized void press(){
		press(1);
	}
	
	public synchronized void release(){
		state = STATE_RELEASED;
	}
	
	public synchronized void tap(){
		press();
		release();
	}
	
	public synchronized int getAmount(){
		int retVal = amount;
		if (retVal != 0) {
            if (state == STATE_RELEASED) {
                amount = 0;
            }
            else if (behaviour == INITIAL_KEY_PRESS_ONLY) {
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }
        }
		return retVal;
	}
	
	public boolean isPressed(){
		return (getAmount()!=0);
	}
	
}
