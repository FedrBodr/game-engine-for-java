package com.gej.input;

import java.util.HashMap;

public abstract class GKeyBoard {
	
	private static HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
	
	public static void KeyPress(int keyCode){
		keys.put(keyCode, true);
	}
	
	public static void KeyReleased(int keyCode){
		keys.put(keyCode, false);
	}
	
	public static boolean isPressed(int keyCode){
		Boolean bool = keys.get(keyCode);
		if (bool==null){
			bool = false;
		}
		return bool;
	}

}
