package com.projectdgdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.Array;

/**
 * Created by konglobemeralt on 2017-04-03.
 */
public class AssetManager {

    private com.badlogic.gdx.assets.AssetManager rawAssets;

    private TextureLoader.TextureParameter param;

    private Array<String> loadedModels = new Array<String>();

    public AssetManager(){
        rawAssets = new com.badlogic.gdx.assets.AssetManager();

        param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        param.genMipMaps = true;
    }

    public void loadModel(String model){
        rawAssets.load(model, Model.class);
    }

    public void loadTexture(String texture){
        rawAssets.load(texture, Texture.class, param);
    }

    public void setTextureToModel(String textureToSet, String modelToChange){
        Model model = rawAssets.get(modelToChange, Model.class);
        Texture newModelTexture = new Texture(Gdx.files.internal(textureToSet));

        //In the case that we use complex with a number of nodes this will obviously need to be updated to change
        // a specific node instead of the root node 0.
        model.materials.get(0).set(TextureAttribute.createDiffuse(newModelTexture));
    }

    public ModelInstance getModel(String modelString){
        if(!checkIfLoaded(modelString)){
            loadModel(modelString);
        }

        Model model = rawAssets.get(modelString, Model.class);
        ModelInstance modelInstance = new ModelInstance(model);
        return modelInstance;
    }

    public void dispose(){
        rawAssets.dispose();
    }

    private boolean checkIfLoaded(String assetToCheck){
        for(String model : loadedModels) {
            if (assetToCheck.equals(model)){
                return true;
            }
        }
        return false;
    }

    public boolean update(){
        return rawAssets.update();
    }
}
