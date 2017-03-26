package com.projectdgdx.game.utils;

/**
 * Created by Hampus on 2017-03-26.
 */
public class GameObjectInit {
    private String type;

    //Different values with defaults:
    int x = 0;
    int y = 0;
    int z = 0;

    int scaleX = 1;
    int scaleY = 1;
    int scaleZ = 1;

    int width = 1;
    int height  = 1;
    int depth = 1;
    Polygon polygon = null;

    int spawnRate = 1;
    boolean spawnRateRandom = false;
    int spawnDelay = 0;
    int aliveLimit = 10;

    String className = null;

    public GameObjectInit(String type) {
        this.type = type;
    }

    public GameObject convert() {
        switch (type) {
            case "Object":
                return new GameObject();

            case "Spawn":
                return new GameObject();
            default:
                System.out.println("TAG OF TYPE: " + type + " not supported");
                return null;
        }
    }

    public void changeValue(String key, String value) {

    }
}
