package com.gej.input;

import java.util.HashMap;

public class GMouse {
	
	private static HashMap<Integer, Boolean> data = new HashMap<Integer, Boolean>();
	
	public static boolean isMouseMoved(){
		if (data.containsKey(GInput.MOUSE_MOVED)){
			return data.get(GInput.MOUSE_MOVED);
		}
		return false;
	}
	
	public static boolean isMouseClicked(int button){
		if (data.containsKey(button)){
			return data.get(button);
		}
		return false;
	}
	
	public static void setID(int id, boolean value){
		data.put(id, value);
	}

}
