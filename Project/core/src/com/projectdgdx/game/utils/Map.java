package com.projectdgdx.game.utils;

import com.projectdgdx.game.model.Entity;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.PlayableCharacter;
import com.projectdgdx.game.model.StaticObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2017-03-24.
 */
public abstract class Map {
    private int gameTime = 300;
    private int playerSpeed = 1;
    private List<GameObject> gameObjects;


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

    /**
     * A method to get all player objects in the map
     * @return List of players
     */
    public List<PlayableCharacter> getPlayers() {
        List<PlayableCharacter> players = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof PlayableCharacter) {
                players.add((PlayableCharacter)gameObject);
            }
        }
        return players;
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof Entity) {
                entities.add((PlayableCharacter)gameObject);
            }
        }
        return entities;
    }

    public List<StaticObject> getStaticObjects() {
        List<StaticObject> staticObjects = new ArrayList<>();
        for(GameObject gameObject : gameObjects) {
            if(gameObject instanceof StaticObject) {
                staticObjects.add((StaticObject) gameObject);
            }
        }
        return staticObjects;
    }


}
