package com.gej.graphics;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GFontAdvanced {
		
	public static GFont getFont(Image img, String font_descriptor){
		try {
			InputStreamReader isr = new InputStreamReader(GFont.class.getClassLoader().getResourceAsStream(font_descriptor));
			BufferedReader br = new BufferedReader(isr);
			int rows = Integer.parseInt(br.readLine());
			int cols = Integer.parseInt(br.readLine());
			String data = "";
			String line = br.readLine();
			while (line!=null){
				data += line;
				line = br.readLine();
			}
			return new GFont(img, rows, cols, data);
		} catch (Exception e){}
		return null;
	}

}
