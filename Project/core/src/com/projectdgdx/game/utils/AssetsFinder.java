package com.projectdgdx.game.utils;

import java.util.HashMap;

/**
 * Created by Hampus on 2017-03-31.
 */


public class AssetsFinder {
    static HashMap<String, String[]> textureMap = new HashMap<String, String[]>();

    static {
        textureMap.put("worker.basic", new String[]{"robo2.obj", "copper.jpg"});
    }

    static String getModelPath(String id) {
        return textureMap.get(id)[0];
    }

    static String getTexturePath(String id) {
        return textureMap.get(id)[1];
    }

}
