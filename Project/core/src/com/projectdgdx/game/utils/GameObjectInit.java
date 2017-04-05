package com.projectdgdx.game.utils;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.gameobjects.GameObject;
import com.projectdgdx.game.gameobjects.Machine;

/**
 * Created by Hampus on 2017-03-26.
 */
public class GameObjectInit {
    private String type;

    //Different values with defaults:
    private float x = 0;
    private float y = 0;
    private float z = 0;

    private float scaleX = 1;
    private float scaleY = 1;
    private float scaleZ = 1;

    private float rotationX = 0;
    private float rotationY = 0;
    private float rotationZ = 0;

    private Polygon polygon = null;

    private float spawnRate = 1;
    private boolean spawnRateRandom = false;
    private float spawnDelay = 0;
    private float aliveLimit = 10;

    GameObjectInit(String type) {
        this.type = type;
    }

    private GameObjectInit(GameObjectInit gameObjectInit) {
        type = gameObjectInit.type;

        x = gameObjectInit.x;
        y = gameObjectInit.y;
        z = gameObjectInit.z;

        scaleX = gameObjectInit.scaleX;
        scaleY = gameObjectInit.scaleY;
        scaleZ = gameObjectInit.scaleZ;

        rotationX = gameObjectInit.rotationX;
        rotationY = gameObjectInit.rotationY;
        rotationZ = gameObjectInit.rotationZ;

        polygon = gameObjectInit.polygon;

        spawnRate = gameObjectInit.spawnRate;
        spawnRateRandom = gameObjectInit.spawnRateRandom;
        spawnDelay = gameObjectInit.spawnDelay;
        aliveLimit = gameObjectInit.aliveLimit;
    }

    GameObject convert() {
        switch (type) {
            case "Machine":
                return new Machine(new Vector3(x, y, z), new Vector3(scaleX, scaleY, scaleZ), new Vector3(rotationX, rotationY, rotationZ), "machine.basic");
            default:
                System.out.println("TAG OF TYPE: " + type + " not supported");
                return null;
        }
    }

    void changeValue(String key, String value) {
        switch(key) {
            case "x":
                x = Float.parseFloat(value);
                break;
            case "y":
                y = Float.parseFloat(value);
                break;
            case "z":
                z = Float.parseFloat(value);
                break;
            case "scaleX":
                scaleX = Float.parseFloat(value);
                break;
            case "scaleY":
                scaleY = Float.parseFloat(value);
                break;
            case "scaleZ":
                scaleZ = Float.parseFloat(value);
                break;
            case "rotationX":
                rotationX = Float.parseFloat(value);
                break;
            case "rotationY":
                rotationY = Float.parseFloat(value);
                break;
            case "rotationZ":
                rotationZ = Float.parseFloat(value);
                break;
            case "polygon":
                // TODO Handle polygons
                break;
            case "className":
//                className = value;
                break;
            default:
                System.out.println("Value does not exist   " + key);
                break;
        }
    }

    @Override
    public GameObjectInit clone() {
        return new GameObjectInit(this);
    }
}
