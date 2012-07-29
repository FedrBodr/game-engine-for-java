package com.gej.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Some of the utilities needed by the engine. Note that all the utility methods
 * are static. This class is necessary for the engine to run. The class is made
 * abstract to prevent the user from instantiation.
 * 
 * @author Sri Harsha Chilakapati
 */
public abstract class GUtil {

    // Prevent instantiation
    private GUtil() {
    }

    // Direction and velocities

    public static int getPositiveAngle(int angle){
        if (angle < 0) {
            return angle + 360;
        } else if (angle > 360) {
            return angle - 360;
        } else {
            return angle;
        }
    }

    public static float getVelocityX(float velocity, int angle){
        return (float) (velocity * Math.sin(Math.toRadians(getPositiveAngle(angle))));
    }

    public static float getVelocityY(float velocity, int angle){
        return (float) (velocity * (-1) * Math.cos(Math.toRadians(getPositiveAngle(angle))));
    }

    // Collision Detection

    /**
     * Checks if two rectangles intersect each other at a high speed than the
     * Rectangle class. This is also accurate since the collision detection is
     * done if four stages.
     * 
     * @param x1 The upper-left x-coordinate of the first rectangle
     * @param y1 The upper-left y-coordinate of the first rectangle
     * @param w1 The width of the first rectangle
     * @param h1 The height of the first rectangle
     * @param x2 The upper-left x-coordinate of the second rectangle
     * @param y2 The upper-left y-coordinate of the second rectangle
     * @param w2 The width of the second rectangle
     * @param h2 The height of the second rectangle
     * @return True if the two rectangles intersect each other
     */
    public static boolean isCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
        if (x1 < x2 + w2) {
            if (x2 < x1 + w1) {
                if (y1 < y2 + h2) {
                    if (y2 < y1 + h1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check's for pixel-perfect collision between two buffered images.
     * 
     * @param x1 The x-position of the first object
     * @param y1 The y-position of the first object
     * @param image1 The buffered image of first object
     * @param x2 The x-position of the second object
     * @param y2 The y-position of the second object
     * @param image2 The buffered image of second object
     * @return True if a collision has been found
     */
    public static boolean isPixelPerfectCollision(float x1, float y1, BufferedImage image1, float x2, float y2, BufferedImage image2){
        boolean bool = false;
        double width1 = x1 + image1.getWidth() - 1, height1 = y1
                + image1.getHeight() - 1, width2 = x2 + image2.getWidth() - 1, height2 = y2
                + image2.getHeight() - 1;
        int xstart = (int) Math.max(x1, x2), ystart = (int) Math.max(y1, y2), xend = (int) Math.min(width1, width2), yend = (int) Math.min(height1, height2);
        int toty = Math.abs(yend - ystart);
        int totx = Math.abs(xend - xstart);
        for (int y = 1; y < toty - 1; y++) {
            int ny = Math.abs(ystart - (int) y1) + y;
            int ny1 = Math.abs(ystart - (int) y2) + y;
            for (int x = 1; x < totx - 1; x++) {
                int nx = Math.abs(xstart - (int) x1) + x;
                int nx1 = Math.abs(xstart - (int) x2) + x;
                try {
                    if (((image1.getRGB(nx, ny) & 0xFF000000) != 0x00)
                            && ((image2.getRGB(nx1, ny1) & 0xFF000000) != 0x00)) {
                        bool = true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return bool;
    }

    /**
     * Runs a task in a separate thread. The task here is a Runnable object.
     * 
     * @param r The Runnable object.
     */
    public static void runInSeperateThread(Runnable r){
        Thread th = new Thread(r);
        th.start();
    }

    /**
     * Loads a file from the root of the jar-file into an array of strings
     * 
     * @param FileName The name of the file.
     * @return The String[] array containing all the lines in the file.
     */
    public static String[] loadLinesFromFile(String FileName){
        String[] strs = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(GUtil.class.getClassLoader().getResourceAsStream(FileName)));
        ArrayList<String> lines = new ArrayList<String>();
        try {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            strs = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                strs[i] = lines.get(i);
            }
        } catch (Exception e) {
        }
        return strs;
    }

    // Some real valued functions for advanced users

    /**
     * Returns a random real number between 0 and x. The number is always
     * smaller than x.
     * 
     * @param x The maximum range
     * @return A random real number
     */
    public static int random(int x){
        return (int) (Math.floor(Math.random() * x));
    }

    /**
     * Returns a random real number between x1 (inclusive) and x2 (exclusive).
     * 
     * @param x1 The inclusive
     * @param x2 The exclusive
     * @return A random real number between x1 and x2
     */
    public static int random_range(int x1, int x2){
        return (int) (Math.floor(x1 + (Math.random() * (x2 - x1))));
    }

    /**
     * Returns one of the arguments chosen randomly.
     * 
     * @param values The array containing values
     * @return A random value present in the array
     */
    public static int choose(int[] values){
        return (values[random(values.length + 1)]);
    }

    /**
     * Returns the fractional part of x, that is, the part behind the decimal
     * dot.
     * 
     * @param x The real number
     * @return The fractional part of x
     */
    public static int frac(double x){
        String str = "" + x;
        String result = "";
        boolean add = false;
        for (int i = 0; i < str.length(); i++) {
            if (add) {
                result += str.charAt(i);
            }
            if (!add && str.charAt(i) == '.') {
                add = true;
            }
        }
        return Integer.parseInt(result);
    }

    /**
     * Returns x to the power n.
     * 
     * @param x The base
     * @param n The exponent
     * @return x to the power n.
     */
    public static int power(int x, int n){
        return (int) (Math.pow(x, n));
    }

    /**
     * Returns the average of the values.
     * 
     * @param values The array of integers
     * @return The mean of the values
     */
    public static int mean(int[] values){
        int result = 0;
        for (int i = 0; i < values.length; i++) {
            result += values[i];
        }
        result /= values.length;
        return result;
    }

    /**
     * Returns the distance between point (x1,y1) and point (x2,y2).
     * 
     * @param x1 The abscissa of first point
     * @param y1 The ordinate of first point
     * @param x2 The abscissa of second point
     * @param y2 The ordinate of second point
     * @return The distance between two points
     */
    public static int point_distance(int x1, int y1, int x2, int y2){
        return (int) (Math.sqrt(((x2 - x1) * (x2 - x1))
                + ((y2 - y1) * (y2 - y1))));
    }

    /**
     * You specify the number of sides of a dice which is then thrown. Then if
     * the dice lands on one, the result is true and the next action is
     * performed. This can be used to put an element of randomness in your game.
     * For example, in each step you can generate with a particular chance a
     * bomb or a change of direction. The larger the number of sides of the
     * dice, the smaller the chance.
     * 
     * @param sides The number of sides of the dice
     * @return True if the dice lands on one else false
     */
    public static boolean chance(int sides){
        if (random(sides + 1) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the inclination of the line-segment drawn from (x1, y1) to (x2,
     * y2) in degrees
     * 
     * @param x1 The abscissa of first point
     * @param y1 The ordinate of first point
     * @param x2 The abscissa of second point
     * @param y2 The ordinate of second point
     * @return The direction in degrees
     */
    public static int getDirection(float x1, float y1, float x2, float y2){
        return (int) Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));
    }

}
