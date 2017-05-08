package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.model.PlayableCharacter;
import com.projectdgdx.game.utils.*;
import com.projectdgdx.game.utils.Map;
import com.projectdgdx.game.view.RenderManager;

import java.util.*;

/**
 * InGameState controls everything that is ingame.
 * Created by Eddie on 2017-04-28.
 */
public class InGameState implements GameState {

    private InputMultiplexer multiplexer;
    private  Array<AnimationController> animationControllers = new Array<AnimationController>();

    private PerspectiveCamera cam;
    private CameraInputController camController;

    private HashMap<InputController, PlayableCharacter> controllerPlayerMap = new HashMap<>();
    private HashMap<GameObject, ModelInstance> objectsMap = new HashMap<>();


    private RenderManager renderer;
    private Random rand;
    private Map map;

    private void createCamera(){
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
       renderer.render(cam, objectsMap.values()); //Pass renderInstances and camera to render
    }

    private void animate(){
       for(AnimationController controllerInstance: animationControllers){
           controllerInstance.update(Gdx.graphics.getDeltaTime() + rand.nextFloat() * 0.02f);
       }
    }

    private void handleInput(ProjectD projectD){
        float deltaTime = Gdx.graphics.getDeltaTime();
        for(InputController inputController : projectD.getInpuControllers()) {
            InputModel inputModel = inputController.getModel();
            if(controllerPlayerMap.containsKey(inputController)) {
                PlayableCharacter player = controllerPlayerMap.get(inputController);
                if(inputModel.getLeftStick().getLength() != 0) {
                    player.setRotation(new Vector3d(0, inputModel.getLeftStick().getAngle() + 90, 0));
                    player.getPosition().add(new Vector3d(deltaTime * inputModel.getLeftStick().x * Config.MOVE_SPEED, 0, deltaTime * -inputModel.getLeftStick().z * Config.MOVE_SPEED));
                }


            }



            if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
                this.stop(projectD);
                projectD.setState(GameStates.SETTINGS);
            }
        }
    }

    public void update(ProjectD projectD){
            handleInput(projectD);
            animate();
            render();
            updateModelInstaces();
    }

    public void init(ProjectD projectD){
        rand = new Random();

        MapParser parser = new MapParser();
        map = parser.parse("BasicMapTest");

        int i = 0;
        List<PlayableCharacter> players = map.getPlayers();
        for(InputController input : projectD.getInpuControllers()) {
            if(i < players.size()) {
                controllerPlayerMap.put(input, players.get(i));
            }
            i++;
        }

        generateRenderInstances();

    }

    private void generateRenderInstances(){


        for (GameObject gameObject : map.getGameObjects()) {
            // Create a ModelInstance for GameObject gameObject
            ModelInstance modelInstance = new ModelInstance(AssetManager.getModel(AssetsFinder.getModelPath(gameObject.getId())));
            modelInstance.transform.setToTranslation(VectorConverter.convertToLibgdx(gameObject.getPosition()));
            Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
            modelInstance.transform.scale(scale.x, scale.y, scale.z);
            Vector3 rotation = VectorConverter.convertToLibgdx(gameObject.getRotation());
            modelInstance.transform.rotate(Vector3.X, rotation.x);
            modelInstance.transform.rotate(Vector3.Y, rotation.y);
            modelInstance.transform.rotate(Vector3.Z, rotation.z);

            //Check for worker or player animation from id
            if(gameObject.getId().equalsIgnoreCase("worker.basic") || gameObject.getId().equalsIgnoreCase("player.basic")) {
                AnimationController animController = new AnimationController(modelInstance);
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

            //Add GameObject and ModelInstance to a map that keeps them together
            objectsMap.put(gameObject, modelInstance);

        }

    }

    @Override
    public void start(ProjectD projectD) {
        this.multiplexer = new InputMultiplexer(projectD.getMultiplexer()); //Handle debug camera control input
        createCamera();

        renderer = new RenderManager();
        renderer.init();

    }

    public void updateModelInstaces() {
        for(java.util.Map.Entry<GameObject, ModelInstance> entrySet : objectsMap.entrySet()) {
            ModelInstance modelInstance = entrySet.getValue();
            GameObject gameObject = entrySet.getKey();
            Vector3 position = VectorConverter.convertToLibgdx(gameObject.getPosition());
            Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
            Quaternion quaternion = new Quaternion();


            quaternion.set(Vector3.Y, VectorConverter.convertToLibgdx(gameObject.getRotation()).y); //TODO fix the addition of 90 degrees

            Matrix4 matrix4 = new Matrix4(position, quaternion, scale);
            modelInstance.transform.set(matrix4);
        }
    }

    @Override
    public void stop(ProjectD projectD) {
        projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
    }

    public void exit(ProjectD projectD){
        this.stop(projectD);
        renderer.dispose();
    }



}
