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
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.utils.*;
import com.projectdgdx.game.view.BaseShader;
import com.projectdgdx.game.view.RenderManager;

import java.util.Random;

/**
 * Created by Eddie on 2017-04-28.
 */
public class InGameState implements GameState {

    private InputMultiplexer multiplexer;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Array<AnimationController> animationControllers = new Array<AnimationController>();
    private AnimationController animController;


    private PerspectiveCamera cam;
    private CameraInputController camController;

    RenderManager renderer;
    Random rand;
    Map map;

    private Model floor;
    boolean loading;

    public boolean isMapParsed(){
        return loading;
    }

    private void createFloor(){
        //Create a temp floor
        ModelBuilder modelBuilder = new ModelBuilder();
        floor = modelBuilder.createBox(500f, 1f, 500f,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instances.add(new ModelInstance(floor));

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
       renderer.render(cam, instances); //Pass renderInstances and camera to render
    }

    private void animate(){
       for(AnimationController controllerInstance: animationControllers){
           controllerInstance.update(Gdx.graphics.getDeltaTime() + rand.nextFloat() * 0.02f);
       }
    }

    private void handleInput(ProjectD projectD){
        //TODO Add support for more controllers here through projectD.getInpuControllers()
        ModelInstance modelInstance = this.instances.get(3);
        InputModel inputModel = projectD.getInpuControllers().get(0).getModel();
        Vector3 position = modelInstance.transform.getTranslation(new Vector3());
        System.out.println(inputModel.getLeftStick().getLength());
        if(inputModel.getLeftStick().getLength() >= 0.1f || inputModel.getLeftStick().getLength() <= -0.1f) {
            Vector3 scale = modelInstance.transform.getScale(new Vector3());
            Quaternion quaternion = new Quaternion();


            quaternion.set(Vector3.Y, inputModel.getLeftStick().getAngle() + 90); //TODO fix the addition of 90 degrees

            Matrix4 matrix4 = new Matrix4(position, quaternion, scale);
            modelInstance.transform.set(matrix4);
        }
        float deltaTime = Gdx.graphics.getDeltaTime();
        modelInstance.transform.trn(deltaTime * inputModel.getLeftStick().x * Config.MOVE_SPEED, 0, deltaTime * -inputModel.getLeftStick().z * Config.MOVE_SPEED);

        if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
            this.stop(projectD);
            projectD.setState(GameStates.SETTINGS);
        }

    }

    public void dispose () {
        instances.clear();
    }

    public void update(ProjectD projectD){
            handleInput(projectD);
            animate();
            render();
    }

    public void init(ProjectD projectD){
        rand = new Random();

        MapParser parser = new MapParser();
        map = parser.parse("BasicMapTest");

    }

    private void generateRenderInstances(){

        loading = true;

        for (GameObject gameObject : map.getGameObjects()) {
            ModelInstance npcInstance;
            System.out.println(AssetsFinder.getModelPath(gameObject.getId()));
            npcInstance = new ModelInstance(AssetManager.getModel(AssetsFinder.getModelPath(gameObject.getId())));
            npcInstance.transform.setToTranslation(VectorConverter.convertToLibgdx(gameObject.getPosition()));
            Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
            npcInstance.transform.scale(scale.x, scale.y, scale.z);
            Vector3 rotation = VectorConverter.convertToLibgdx(gameObject.getRotation());
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

    }

    @Override
    public void start(ProjectD projectD) {
        this.multiplexer = new InputMultiplexer(projectD.getMultiplexer()); //Handle debug camera control input

        generateRenderInstances();
        createCamera();
        createFloor();

        renderer = new RenderManager();
        renderer.init();

    }

    @Override
    public void stop(ProjectD projectD) {
        projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
    }

    public void exit(ProjectD projectD){
        this.stop(projectD);
        this.dispose();
        renderer.dispose();
    }



}
