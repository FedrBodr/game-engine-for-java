package com.gej.util;

import java.util.HashMap;

public class Logger {
    
    private static HashMap<Integer, String> logs = new HashMap<Integer, String>();
    
    public static boolean PRINT_TO_CONSOLE = false;

    public static void Log(String msg, int id){
        logs.put(id, msg);
        if (PRINT_TO_CONSOLE){
            System.out.println("LOG ID " + id + ":     " + msg);
        }
    }
    
}
