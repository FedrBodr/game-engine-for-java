package com.gej.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.gej.core.GWindow;
import com.gej.core.Game;
import com.gej.input.GKeyBoard;
import com.gej.map.Map;
import com.gej.map.MapInfo;
import com.gej.map.MapLoader;
import com.gej.map.MapManager;
import com.gej.object.GObject;
import com.gej.object.Tile;

public class MapManagerTest extends Game implements MapLoader {

    /**
	 * 
	 */
    private static final long serialVersionUID = -925136059688114213L;

    Image box_img = null;

    public void initResources(){
        box_img = loadImage("resources/box.png");
        MapManager.addMap(MapInfo.LoadFromFile("resources/MazerBox.txt", this, 64));
        MapManager.addMap(MapInfo.LoadFromFile("resources/PlatformTest.txt", this, 64));
        MapManager.startFirstMap();
    }

    public void update(long elapsedTime){
        if (GKeyBoard.isPressed(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        if (GKeyBoard.isPressed(KeyEvent.VK_RIGHT)) {
            loadNextMap();
        }
        if (GKeyBoard.isPressed(KeyEvent.VK_LEFT)) {
            loadPrevMap();
        }
    }

    public void loadNextMap(){
        if (MapManager.hasNextMap()) {
            MapManager.loadNextMap();
        }
    }

    public void loadPrevMap(){
        if (MapManager.hasPreviousMap()) {
            MapManager.loadPreviousMap();
        }
    }

    public void render(Graphics2D g){
        Map.renderMap(g);
    }

    public GObject getObject(char c, int x, int y){
        if (c != ' ') {
            GObject obj = new GObject(box_img);
            obj.setX(x);
            obj.setY(y);
            return obj;
        }
        return null;
    }

    public Tile getTile(char c, int x, int y){
        return null;
    }

    public static void main(String[] args){
        GWindow.setup(new MapManagerTest());
    }

}
