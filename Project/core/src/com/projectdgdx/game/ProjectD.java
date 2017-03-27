package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ProjectD extends ApplicationAdapter {
    private PerspectiveCamera cam;
    private CameraInputController camController;
    private ModelBatch modelBatch;
    private AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Environment environment;
    public boolean loading;


    @Override
    public void create () {

        loadModels();

        createEnvironment();
        createCamera();
    }

    public void loadModels(){
        modelBatch = new ModelBatch();

        assets = new AssetManager();

        //Text
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        param.genMipMaps = true;
        assets.load("badlogic.jpg", Texture.class, param);

        //model
        assets.load("ship.g3db", Model.class);
        assets.load("bunny.obj", Model.class);
        loading = true;
    }

    private void doneLoading() {
        Model ship = assets.get("ship.g3db", Model.class);
        Model bunny = assets.get("bunny.obj", Model.class);
        for (float x = -50f; x <= 50f; x += 2f) {
            for (float z = -50f; z <= 50f; z += 2f) {
                ModelInstance modelInstance;
                if (z<-30) {
                    modelInstance = new ModelInstance(bunny);
                    modelInstance.transform.setToTranslation(x, 3, z);
                    modelInstance.transform.scale(10, 10, 10);
                }
                else{
                    modelInstance = new ModelInstance(ship);
                    modelInstance.transform.setToTranslation(x, 0, z);

                }

                instances.add(modelInstance);
            }
        }
        loading = false;
    }

    public void createEnvironment(){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    public void createCamera(){
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(7f, 7f, 7f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        //Camera control
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }


    public void render () {
        if (loading && assets.update())
            doneLoading();
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        if(!loading)
            moveModel(instances);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    private void moveModel(Array<ModelInstance> instances){
        for(ModelInstance instance: instances) {
            if (Gdx.input.isButtonPressed(Input.Keys.I)) {
                instance.transform.trn(0, 0, 0.1f);
            }
            if (Gdx.input.isButtonPressed(Input.Keys.K)) {
                instance.transform.trn(0, 0, 0.1f);
            }
        }
    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
    }
}
