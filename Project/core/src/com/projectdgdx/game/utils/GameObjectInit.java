package com.projectdgdx.game.utils;

import com.projectdgdx.game.gameobjects.GameObject;

/**
 * Created by Hampus on 2017-03-26.
 */
public class GameObjectInit {
    private String type;

    //Different values with defaults:
    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double scaleX = 1;
    private double scaleY = 1;
    private double scaleZ = 1;

    private double width = 1;
    private double height  = 1;
    private double depth = 1;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;

    private Polygon polygon = null;

    private double spawnRate = 1;
    boolean spawnRateRandom = false;
    private double spawnDelay = 0;
    private double aliveLimit = 10;

    public GameObjectInit(String type) {
        this.type = type;
    }

    public GameObject convert() {
        switch (type) {
            case "Model":
//                Class<?> clazz = Class.forName(className);
//                GameObject date = clazz.newInstance();
                return new GameObject(new Vector3(x, y, z), new Vector3(scaleX, scaleY, scaleZ), new Vector3(width, height, depth), new Vector3(rotationX, rotationY, rotationZ), "Model");
            case "Machine":
                return new GameObject(new Vector3(x, y, z), new Vector3(scaleX, scaleY, scaleZ), new Vector3(width, height, depth), new Vector3(rotationX, rotationY, rotationZ), "machine.basic");
            case "Spawn":
                return new GameObject(new Vector3(x, y, z), new Vector3(scaleX, scaleY, scaleZ), new Vector3(width, height, depth), new Vector3(rotationX, rotationY, rotationZ), "Spawn");
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
            case "rotationX":
                rotationX = Double.parseDouble(value);
                break;
            case "rotationY":
                rotationY = Double.parseDouble(value);
                break;
            case "rotationZ":
                rotationZ = Double.parseDouble(value);
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
}
