package com.projectdgdx.game.utils;

/**
 * Created by Hampus on 2017-03-26.
 */
public class GameObjectInit {
    private String type;

    //Different values with defaults:
    double x = 0;
    double y = 0;
    double z = 0;

    double scaleX = 1;
    double scaleY = 1;
    double scaleZ = 1;

    double width = 1;
    double height  = 1;
    double depth = 1;
    Polygon polygon = null;

    double spawnRate = 1;
    boolean spawnRateRandom = false;
    double spawnDelay = 0;
    double aliveLimit = 10;

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
        switch(key) {
            case "x":
                x = Double.parseDouble(value);
                break;
            case "y":
                y = Double.parseDouble(value);
                break;
            case "z":
                z = Double.parseDouble(value);
                break;
            case "scaleX":
                scaleX = Double.parseDouble(value);
                break;
            case "scaleY":
                scaleY = Double.parseDouble(value);
                break;
            case "scaleZ":
                scaleZ = Double.parseDouble(value);
                break;
            case "width":
                width = Double.parseDouble(value);
                break;
            case "height":
                height = Double.parseDouble(value);
                break;
            case "depth":
                depth = Double.parseDouble(value);
                break;
            case "polygon":
                // TODO Handle polygons
                break;
            default:
                System.out.println("Value does not exist");
                break;
        }
    }
}
