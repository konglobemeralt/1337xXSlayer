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
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.MapLoader;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class ProjectD extends ApplicationAdapter {
    private PerspectiveCamera cam;
    private CameraInputController camController;
    private ModelBatch modelBatch;
    private AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Environment environment;
    public boolean loading;

    public Texture roboTexture;

    Random rand;

    @Override
    public void create () {

        rand = new Random();
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
        assets.load("robo.obj", Model.class);
        loading = true;
    }

    private void doneLoading() {
        Model robo = assets.get("robo.obj", Model.class);

        roboTexture = new Texture(Gdx.files.internal("copper.jpg"));
        robo.materials.get(0).set(TextureAttribute.createDiffuse(roboTexture));

        //Player
        ModelInstance playerInstance;
        playerInstance = new ModelInstance(robo);
        playerInstance.transform.setToTranslation(0, 0, 0);
        playerInstance.transform.scale(0.03f, 0.03f, 0.03f);

        instances.add(playerInstance);

        for (int x = 0; x < 150; x += 1) {
                    ModelInstance npcInstance;
                    npcInstance = new ModelInstance(robo);
                    npcInstance.transform.setToTranslation(rand.nextFloat() * (50 - -50) + -50, 0, rand.nextFloat() * (50 - -50) + -50);
                    npcInstance.transform.scale(0.03f, 0.03f, 0.03f);
                    npcInstance.transform.rotate(Vector3.Y, rand.nextFloat() * 360f);

                    instances.add(npcInstance);
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
            moveModel(instances.get(0));

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    private void moveModel(ModelInstance instance){

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            instance.transform.trn(0.1f, 0, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            instance.transform.trn(-0.1f, 0, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            instance.transform.trn(0, 0, -0.1f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            instance.transform.trn(0, 0, 0.1f);
        }


    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
    }
}
