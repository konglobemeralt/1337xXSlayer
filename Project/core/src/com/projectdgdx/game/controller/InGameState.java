package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.utils.AssetManager;
import com.projectdgdx.game.utils.AssetsFinder;
import com.projectdgdx.game.utils.Map;
import com.projectdgdx.game.utils.MapParser;
import com.projectdgdx.game.view.BaseShader;

import java.util.Random;

/**
 * Created by Eddie on 2017-04-28.
 */
public class InGameState implements GameState {

    private FPSLogger fps;
    private InputMultiplexer multiplexer;

    private PerspectiveCamera cam;
    private CameraInputController camController;

    private ModelBatch modelBatch;
    private ModelBatch shadowBatch;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Array<AnimationController> animationControllers = new Array<AnimationController>();
    private AnimationController animController;

    public Environment environment;
    DirectionalShadowLight shadowLight;
    public boolean loading;
    private Model floor;
    public Shader shader;


    Random rand;
    Map map;


    public void loadAssets(){
        modelBatch = new ModelBatch();
        shadowBatch = new ModelBatch(new DepthShaderProvider());

        //Create a temp floor
        ModelBuilder modelBuilder = new ModelBuilder();
        floor = modelBuilder.createBox(500f, 1f, 500f,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instances.add(new ModelInstance(floor));

        loading = true;
    }

    private void doneLoading() {

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

            if(gameObject.getId() == "worker.basic" || gameObject.getId() == "player.basic") {
                animController = new AnimationController(npcInstance);
                animController.setAnimation("Robot|IdleAnim", -1, 0.2f, new AnimationController.AnimationListener() {
                    @Override
                    public void onEnd(AnimationController.AnimationDesc animation) {
                    }

                    @Override
                    public void onLoop(AnimationController.AnimationDesc animation) {
                        //   Gdx.app.log("INFO", "Animation Ended");
                    }
                });
                animationControllers.add(animController);
            }
            instances.add(npcInstance);
        }

        loading = false;

        shader = new BaseShader();
        shader.init();
    }

    public void createEnvironment(){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
                Config.AMBIENT_LIGHT_R,
                Config.AMBIENT_LIGHT_G,
                Config.AMBIENT_LIGHT_B,
                Config.AMBIENT_LIGHT_A));
        if(Config.SHADOWMAPPING_ENABLED){
        environment.add((shadowLight = new DirectionalShadowLight(Config.SHADOW_MAP_WIDTH,
                Config.SHADOW_MAP_HEIGHT,
                Config.SHADOW_MAP_VIEWPORT_WIDTH,
                Config.SHADOW_MAP_VIEWPORT_HEIGHT,
                Config.SHADOW_MAP_NEAR,
                Config.SHADOW_MAP_FAR)).set(
                Config.SUN_LIGHT_R,
                Config.SUN_LIGHT_G,
                Config.SUN_LIGHT_B,
                Config.SUN_LIGHT_X,
                Config.SUN_LIGHT_Y,
                Config.SUN_LIGHT_Z));
        environment.shadowMap = shadowLight;
        }else{
            environment.add(new DirectionalLight().set(Config.SUN_LIGHT_R,
                    Config.SUN_LIGHT_G,
                    Config.SUN_LIGHT_B,
                    Config.SUN_LIGHT_X,
                    Config.SUN_LIGHT_Y,
                    Config.SUN_LIGHT_Z));
        }
        environment.add(new PointLight().set(0.9f, 0.3f, 0.3f,
                35, 15f, 45f, 100f));


    }


    public void createCamera(){
        cam = new PerspectiveCamera(Config.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(110f, 120f, 135f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = Config.CAMERA_NEAR;
        cam.far = Config.CAMERA_FAR;
        camController = new CameraInputController(cam);
        camController.forwardTarget = true;

        multiplexer.addProcessor(camController);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);

        cam.update();
    }

    public void render () {

        fps.log();

        if (loading && AssetManager.update())
            doneLoading();

        cam.update();

        for(AnimationController controllerInstance: animationControllers){
            controllerInstance.update(Gdx.graphics.getDeltaTime() + rand.nextFloat() * 0.02f);
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        if(!loading)
            //moveModel(instances.get(2));

        if(Config.SHADOWMAPPING_ENABLED) {
            renderShadowMap();
        }
        renderToScreen();

    }

    private void renderToScreen(){
        modelBatch.begin(cam);
        for (ModelInstance instance : instances) {
            modelBatch.render(instance, environment);
        }
        modelBatch.end();
    }

    private void renderShadowMap(){
        shadowLight.begin(Vector3.Zero, cam.direction);
        shadowBatch.begin(shadowLight.getCamera());

        for (ModelInstance instance : instances) {
            shadowBatch.render(instance, environment);
        }
        shadowBatch.end();
        shadowLight.end();
    }

    private void moveModel(ModelInstance instance){

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            instance.transform.trn(Config.MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0, 0);
            //cam.position.set(cam.position.x + Config.MOVE_SPEED, cam.position.y, cam.position.z);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            instance.transform.trn(-Config.MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0, 0);
            //cam.position.set(cam.position.x - Config.MOVE_SPEED, cam.position.y, cam.position.z);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            instance.transform.trn(0, 0, -Config.MOVE_SPEED * Gdx.graphics.getDeltaTime());
            //cam.position.set(cam.position.x, cam.position.y, cam.position.z -Config.MOVE_SPEED);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            instance.transform.trn(0, 0, Config.MOVE_SPEED * Gdx.graphics.getDeltaTime());
            //cam.position.set(cam.position.x, cam.position.y, cam.position.z +Config.MOVE_SPEED);
        }


    }


    public void dispose () {
        modelBatch.dispose();
        instances.clear();
        AssetManager.dispose();
    }

    public void update(ProjectD projectD){

        render();

        //TODO Controller testing:
		if(projectD.getInpuControllers().size() >= 1) {
			ModelInstance modelInstance = this.instances.get(3);
			InputModel inputModel = projectD.getInpuControllers().get(0).getModel();
			Vector3 position = modelInstance.transform.getTranslation(new Vector3());
			Vector3 scale = modelInstance.transform.getScale(new Vector3());
			Quaternion quaternion = new Quaternion();
			quaternion.set(Vector3.Y, inputModel.getLeftStick().getAngle() + 90); //TODO fix the addition of 90 degrees
			Matrix4 matrix4 = new Matrix4(position, quaternion, scale);
			modelInstance.transform.set(matrix4);

			float deltaTime = Gdx.graphics.getDeltaTime();
			modelInstance.transform.trn(deltaTime * inputModel.getLeftStick().x * Config.MOVE_SPEED, 0, deltaTime * -inputModel.getLeftStick().z * Config.MOVE_SPEED);
		}else{
            moveModel(this.instances.get(3));
        }
    }


    public void init(ProjectD projectD){
        this.multiplexer = projectD.getMultiplexer();

        MapParser parser = new MapParser();
        map = parser.parse("BasicMapTest");
        rand = new Random();

        loadAssets();

        shader = new BaseShader();
        shader.init();

        fps = new FPSLogger();
    }

    @Override
    public void start() {
        createEnvironment();
        createCamera();
    }

    @Override
    public void stop() {

    }

    public void exit(){
        dispose();
    }



}
