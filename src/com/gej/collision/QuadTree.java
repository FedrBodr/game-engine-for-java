package com.gej.collision;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.gej.map.MapView;
import com.gej.object.GObject;

/**
 * A QuadTree implementation to reduce collision checks. Every level contains
 * a maximum of 10 objects and the tree sub divides on exceeding this limit.
 * 
 * @author Sri Harsha Chilakapati
 */
public class QuadTree {

    // The MAX_OBJECTS and LEVEL constants
    private int MAX_OBJECTS = 10;
    private int level;
    
    // The objects list
    private ArrayList<GObject> objects;
    // The retrieve list
    private ArrayList<GObject> retrieveList;

    // The bounds of this tree
    private Rectangle bounds;

    // Branches of this tree a.k.a the quadrants
    private QuadTree[] nodes;

    /**
     * Construct a QuadTree with default values. Set's for the whole screen
     */
    public QuadTree() {
        this(0, MapView.getVisibleRect());
    }

    /**
     * Construct a QuadTree with custom values. Used to create sub trees or branches
     * @param l The level of this tree
     * @param b The bounds of this tree
     */
    public QuadTree(int l, Rectangle b) {
        level = l;
        bounds = b;
        objects = new ArrayList<GObject>();
        retrieveList = new ArrayList<GObject>();
        nodes = new QuadTree[4];
    }
    
    /**
     * Set's the bounds of this tree.
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param width The width
     * @param height The height
     */
    public void setBounds(int x, int y, int width, int height){
        bounds.x = x;
        bounds.y = y;
        bounds.width = width;
        bounds.height = height;
        clear();
        split();
    }

    /**
     * Clear this tree. Also clears any subtrees.
     */
    public void clear(){
        objects.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    // Split the tree into 4 quadrants
    private void split(){
        int subWidth = (int) (bounds.getWidth() / 2);
        int subHeight = (int) (bounds.getHeight() / 2);
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();
        nodes[0] = new QuadTree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level + 1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    // Get the index of an object
    private int getIndex(GObject r){
        int index = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);
        boolean topQuadrant = (r.getY() < horizontalMidpoint && r.getY() + r.getHeight() < horizontalMidpoint);
        boolean bottomQuadrant = (r.getY() > horizontalMidpoint);
        if (r.getX() < verticalMidpoint && r.getX() + r.getWidth() < verticalMidpoint){
            if (topQuadrant){
                index = 1;
            } else if (bottomQuadrant){
                index = 2;
            }
        } else if (r.getX() > verticalMidpoint){
            if (topQuadrant){
                index = 0;
            } else if (bottomQuadrant){
                index = 3;
            }
        }
        return index;
    }
    
    /**
     * Insert an object into this tree
     */
    public void insert(GObject r){
        if (nodes[0]!=null){
            int index = getIndex(r);
            if (index!=-1){
                nodes[index].insert(r);
                return;
            }
        }
        objects.add(r);
        if (objects.size() > MAX_OBJECTS){
            if (nodes[0]==null){
                split();
            }
            for (int i=0; i<objects.size(); i++){
                int index = getIndex(objects.get(i));
                if (index!=-1){
                    nodes[index].insert(objects.remove(i));
                }
            }
        }
    }
    
    /**
     * Insert an ArrayList of objects into this tree
     */
    public void insert(ArrayList<GObject> o){
        for (int i=0; i<o.size(); i++){
            insert(o.get(i));
        }
    }
    
    /**
     * Returns the collidable objects with the given object
     */
    public ArrayList<GObject> retrieve(GObject r){
        retrieveList.clear();
        int index = getIndex(r);
        if (index != -1 && nodes[0] != null){
            retrieveList = nodes[index].retrieve(r);
        }
        retrieveList.addAll(objects);
        return retrieveList;
    }

}
