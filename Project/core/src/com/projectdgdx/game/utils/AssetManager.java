package com.projectdgdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

/**
 * Created by konglobemeralt on 2017-04-03.
 */
public class AssetManager {

    private ModelBatch modelBatch;
    private com.badlogic.gdx.assets.AssetManager rawAssets;

    public AssetManager(){
        modelBatch = new ModelBatch();
        rawAssets = new com.badlogic.gdx.assets.AssetManager();

    }

    public void loadModel(String model){
        rawAssets.load(model, Model.class);
    }

    public void loadTexture(String texture){
        rawAssets.load(texture, Texture.class);
    }

    public void setTextureToModel(String textureToSet, String modelToChange){
        Model model = rawAssets.get(modelToChange, Model.class);
        Texture newModelTexture = new Texture(Gdx.files.internal("copper.jpg"));
        //In the case that we use complex with a number of nodes this will obviously need to be updated to change
        // a specific node instead of the root node.
        model.materials.get(0).set(TextureAttribute.createDiffuse(newModelTexture));
    }

    public void dispose(){
        rawAssets.dispose();
        modelBatch.dispose();
    }


}
