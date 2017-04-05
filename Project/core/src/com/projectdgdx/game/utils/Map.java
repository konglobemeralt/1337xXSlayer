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


    /**
     * The constructor for Map just requires a list of GameObjects
     *
     * @param gameObjects A list of GameObjects
     */
    public Map(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * getGameObjects can be used to get all of the map's GameObjects
     *
     * @return A list of GameObjects
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

}
