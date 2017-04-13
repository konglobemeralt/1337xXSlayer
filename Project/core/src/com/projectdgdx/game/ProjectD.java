package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.gameobjects.GameObject;
import com.projectdgdx.game.renderer.BaseShader;
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


    public RenderContext renderContext;
    public Shader shader;

    private Model animatedModel;
    private ModelInstance animatedInstance;
    private AnimationController animController;

    public Renderable renderable;

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

        shader = new BaseShader();
        shader.init();

        modelBatch = new ModelBatch();
    }

    public void loadAssets(){
        modelBatch = new ModelBatch();
        //model
        AssetManager.loadModel("robo.g3dj");
        AssetManager.loadModel("machine.g3dj");
        AssetManager.loadModel("ship.g3db");
        AssetManager.loadModel("walking_3.g3db");

        loading = true;
    }

    private void doneLoading() {

        AssetManager.setTextureToModel("copper.jpg", "robo.g3dj");
        AssetManager.setTextureToModel("metal.jpg", "machine.g3dj");

        ModelInstance playerInstance;
        playerInstance = new ModelInstance(AssetManager.getModel("robo.g3dj"));
        playerInstance.transform.setToTranslation(0, 0, 0);
        playerInstance.transform.scale(0.2f, 0.2f, 0.2f);

        NodePart blockPart = playerInstance.getNode("robo_root").getChild(0).parts.get(0);

        renderable = new Renderable();
        blockPart.setRenderable(renderable);
        renderable.environment = null;
        renderable.worldTransform.idt();

        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
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

        animatedInstance = new ModelInstance(AssetManager.getModel("walking_3.g3db"));
        animatedInstance.transform.translate(5f, 0, 5f);
        animatedInstance.transform.scale(0.1f, 0.1f, 0.1f);


        animController = new AnimationController(animatedInstance);
        animController.setAnimation("mixamo.com", -1, new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {
            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {
                Gdx.app.log("INFO","Animation Ended");
            }
        });


        loading = false;

        shader = new BaseShader();
        shader.init();
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

        animController.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        //renderable.meshPart.primitiveType = GL20.GL_LINE_STRIP;

        if(!loading)
            moveModel(instances.get(0));

        modelBatch.begin(cam);
        for (ModelInstance instance : instances) {
            modelBatch.render(instance, shader);
        }
        modelBatch.render(animatedInstance);

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
