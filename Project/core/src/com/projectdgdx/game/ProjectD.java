package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.gameobjects.GameObject;
import com.projectdgdx.game.gameobjects.PlayableCharacter;
import com.projectdgdx.game.gameobjects.Saboteur;
import com.projectdgdx.game.gameobjects.Supervisor;
import com.projectdgdx.game.utils.AssetManager;
import com.projectdgdx.game.utils.AssetsFinder;
import com.projectdgdx.game.utils.Map;

import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.utils.MapParser;

import java.util.Random;

public class ProjectD extends ApplicationAdapter {
    private PerspectiveCamera cam;
    private CameraInputController camController;
    private ModelBatch modelBatch;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Environment environment;
    public boolean loading;

    public Texture roboTexture;

    Random rand;
    Map map;

    @Override
    public void create () {
        MapParser parser = new MapParser();
        map = parser.parse("BasicMap");
        rand = new Random();
        loadAssets();

        createEnvironment();
        createCamera();

        PlayableCharacter s = new Supervisor(new Vector3(1,1,1), new Vector3(1,1,1),new Vector3(1,1,1), "Saboteur");
        Controllers.getControllers().get(0).addListener(s);

    }

    public void loadAssets(){
        modelBatch = new ModelBatch();
        //model
        AssetManager.loadModel("robo.g3dj");
        AssetManager.loadModel("machine.g3dj");
        AssetManager.loadModel("ship.g3db");

        loading = true;
    }

    private void doneLoading() {

        AssetManager.setTextureToModel("copper.jpg", "robo.g3dj");
        AssetManager.setTextureToModel("metal.jpg", "machine.g3dj");


        ModelInstance playerInstance;
        playerInstance = new ModelInstance(AssetManager.getModel("robo.g3dj"));
        playerInstance.transform.setToTranslation(0, 0, 0);
        playerInstance.transform.scale(0.2f, 0.2f, 0.2f);


        instances.add(playerInstance);

        for (int x = 0; x < 150; x += 1) {
            ModelInstance npcInstance;
            npcInstance = new ModelInstance(AssetManager.getModel("ship.g3db"));
            npcInstance.transform.setToTranslation(rand.nextFloat() * (50 - -50) + -50, 0, rand.nextFloat() * (50 - -50) + -50);
            npcInstance.transform.scale(2f, 2f, 2f);
            npcInstance.transform.rotate(Vector3.Y, rand.nextFloat() * 360f);

            instances.add(npcInstance);
        }

        for (GameObject gameObject : map.getGameObjects()) {
            ModelInstance npcInstance;
            System.out.println(AssetsFinder.getModelPath(gameObject.getId()));
            npcInstance = new ModelInstance(AssetManager.getModel(AssetsFinder.getModelPath(gameObject.getId())));
            npcInstance.transform.setToTranslation(gameObject.getPosition());
            Vector3 scale = gameObject.getScale();
            npcInstance.transform.scale(scale.x, scale.y, scale.z);
            Vector3 rotation = gameObject.getRotation();
            npcInstance.transform.rotate(Vector3.X, rotation.x);
            npcInstance.transform.rotate(Vector3.Y, rotation.y);
            npcInstance.transform.rotate(Vector3.Z, rotation.z);

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
        cam = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 3f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 0.01f;
        cam.far = 1000f;
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

    }


    public void render () {
        if (loading && AssetManager.update())
            doneLoading();
        cam.update();


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
            instance.transform.trn(1f, 0, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            instance.transform.trn(-1f, 0, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            instance.transform.trn(0, 0, -1f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            instance.transform.trn(0, 0, 1f);
        }


    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        AssetManager.dispose();
    }
}
