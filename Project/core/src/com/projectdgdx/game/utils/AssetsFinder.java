package com.projectdgdx.game.utils;

import java.util.HashMap;

/**
 * Created by Hampus on 2017-03-31.
 */


public class AssetsFinder {
    static HashMap<String, String[]> textureMap = new HashMap<String, String[]>();

    static {
        textureMap.put("machine.basic", new String[]{"ship.obj", "ship.jpg"});
    }

    public static String getModelPath(String id) {
        System.out.println("ID: " + id);
        return textureMap.get(id)[0];
    }

    public static String getTexturePath(String id) {
        return textureMap.get(id)[1];
    }

}
