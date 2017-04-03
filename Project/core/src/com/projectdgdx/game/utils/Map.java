package com.projectdgdx.game.utils;

import com.projectdgdx.game.gameobjects.GameObject;

import java.util.List;

/**
 * Created by Hampus on 2017-03-24.
 */
public abstract class Map {
    int gameTime = 300;
    int playerSpeed = 1;
    List<GameObject> gameObjects;

    public Map(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

}
