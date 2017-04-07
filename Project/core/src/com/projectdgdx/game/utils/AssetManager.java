package com.projectdgdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.Array;

/**
 * Created by konglobemeralt on 2017-04-03.
 */
public class AssetManager {

   static  private com.badlogic.gdx.assets.AssetManager rawAssets;

    static private TextureLoader.TextureParameter param;

    static private Array<String> loadedModels = new Array<String>();

    static{
        rawAssets = new com.badlogic.gdx.assets.AssetManager();

        param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        param.genMipMaps = true;
    }

    public static void loadModel(String model){
        rawAssets.load("models/" + model, Model.class);
        rawAssets.finishLoading();
    }

    public static void loadTexture(String texture){
        rawAssets.load("textures/" + texture, Texture.class, param);
    }

    public static void setTextureToModel(String textureToSet, String modelToChange){
        Model model = rawAssets.get("models/" + modelToChange, Model.class);
        Texture newModelTexture = new Texture(Gdx.files.internal("textures/" + textureToSet));

        //In the case that we use complex with a number of nodes this will obviously need to be updated to change
        // a specific node instead of the root node 0.
        model.materials.get(0).set(TextureAttribute.createDiffuse(newModelTexture));
    }

    public static ModelInstance getModel(String modelString){
        if(!checkIfLoaded(modelString)){
            loadModel(modelString);
        }

        Model model = rawAssets.get("models/" + modelString, Model.class);
        ModelInstance modelInstance = new ModelInstance(model);
        return modelInstance;
    }

    public static Model getRawModel(String modelString){
        if(!checkIfLoaded(modelString)){
            loadModel(modelString);
        }
        Model model = rawAssets.get("models/" + modelString, Model.class);
        return model;
    }

    public static void dispose(){
        rawAssets.dispose();
    }

    private static boolean checkIfLoaded(String assetToCheck){
        for(String model : loadedModels) {
            if (assetToCheck.equals(model)){
                return true;
            }
        }
        return false;
    }

    public static boolean update(){
        return rawAssets.update();
    }
}
