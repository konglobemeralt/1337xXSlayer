package com.projectdgdx.game.utils;

import java.util.HashMap;

/**
 * AssetFinder provides a way to find paths to both models and textures using a id
 *
 * Created by Hampus on 2017-03-31.
 */
public class AssetsFinder {
    static HashMap<String, String[]> textureMap = new HashMap<String, String[]>();

    //Add all paths here
    static {
        textureMap.put("machine.basic", new String[]{"machineAOblend.g3dj", "metal.jpg"});
        textureMap.put("worker.basic", new String[]{"animRobot.g3dj", "metal.jpg"});
    }

    /**
     * getModelPath provides a way to get a model path by id
     *
     * @param id Id that should be looked for
     * @return A path to the model
     */
    public static String getModelPath(String id) {
        System.out.println("ID: " + id);
        return textureMap.get(id)[0];
    }

    /**
     * getTexturePath provides a way to get a texture path by id
     *
     * @param id ID that should be looked for
     * @return A path to the texture
     */
    public static String getTexturePath(String id) {
        return textureMap.get(id)[1];
    }

}
