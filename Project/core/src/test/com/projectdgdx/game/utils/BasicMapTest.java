package com.projectdgdx.game.utils;

import com.projectdgdx.game.gameobjects.GameObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2017-04-07.
 */
public class BasicMapTest {

    @Test
    public void gameObjects() {
        List<GameObject> gameObjects = new ArrayList<GameObject>();
        BasicMap map = new BasicMap(gameObjects);
        assertTrue(gameObjects.equals(map.getGameObjects()));
    }

}