package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.*;
import com.projectdgdx.game.utils.*;
import com.projectdgdx.game.utils.Map;
import com.projectdgdx.game.view.RenderManager;
import javafx.util.Pair;

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
    private HashMap<GameObject, Pair<ModelInstance, btCollisionObject>> objectsMap = new HashMap<>();

    //Bullet
	btDefaultCollisionConfiguration collisionConfig;
	btCollisionDispatcher dispatcher;


    private RenderManager renderer;
    private Random rand;
    private Map map;

//    class CollisionListener extends ContactListener {
//        @Override
//        public boolean onContactAdded (btManifoldPoint cp, btCollisionObjectWrapper colObj0Wrap, int partId0, int index0,
//                                       btCollisionObjectWrapper colObj1Wrap, int partId1, int index1) {
////            instances.get(colObj0Wrap.getCollisionObject().getUserValue()).moving = false;
////            instances.get(colObj1Wrap.getCollisionObject().getUserValue()).moving = false;
//            System.out.println("Collision!");
//            return true;
//        }
//    }

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
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
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
            //Add a box around object that will be used for physics
			BoundingBox boundingBox = modelInstance.model.calculateBoundingBox(new BoundingBox());
            System.out.println(boundingBox.getDimensions(new Vector3()).toString());

//			btCollisionShape collisionShape = new btBoxShape(boundingBox.getDimensions(new Vector3()).scl(0.5f));
			btCollisionShape collisionShape = new btBoxShape(boundingBox.getDimensions(new Vector3()).scl(0.5f));
//			btCollisionShape collisionShape = new btBoxShape(new Vector3(100f, 100f, 100f));
			btCollisionObject collisionObject = new btCollisionObject();
			collisionObject.setCollisionShape(collisionShape);
			collisionObject.setWorldTransform(modelInstance.transform);

			//Add GameObject and ModelInstance to a map that keeps them together
            objectsMap.put(gameObject, new Pair<ModelInstance, btCollisionObject>(modelInstance, collisionObject));
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
        for(java.util.Map.Entry<GameObject, Pair<ModelInstance, btCollisionObject>> entrySet : objectsMap.entrySet()) {
            ModelInstance modelInstance = entrySet.getValue().getKey();
            GameObject gameObject = entrySet.getKey();
            btCollisionObject collisionObject = entrySet.getValue().getValue();
            Vector3 oldPosition = modelInstance.transform.getTranslation(new Vector3());

            //Check for entity
            if(gameObject instanceof Entity) {
				Vector3 position = VectorConverter.convertToLibgdx(gameObject.getPosition());
				Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
				Quaternion quaternion = new Quaternion();

				//TODO ugly solution for rotation
				Vector3 rotation = VectorConverter.convertToLibgdx(gameObject.getRotation());
				modelInstance.transform.setToRotation(0,0,0,0,0,0);
				modelInstance.transform.rotate(Vector3.X, rotation.x);
				modelInstance.transform.rotate(Vector3.Y, rotation.y);
				modelInstance.transform.rotate(Vector3.Z, rotation.z);
				quaternion = modelInstance.transform.getRotation(new Quaternion());

				Matrix4 matrix4 = new Matrix4(position, quaternion, scale);

				collisionObject.setWorldTransform(matrix4);


				//Check collisions
                boolean collision = false;
                for(StaticObject staticObject : map.getStaticObjects()) {
                    if(checkCollision(collisionObject, objectsMap.get(staticObject).getValue())) {
                        collision = true;
                        break;
                    }
                }

                if(collision) {
                    System.out.println("COLLISION!");
                    matrix4 = new Matrix4(oldPosition, quaternion, scale);
                    modelInstance.transform.set(matrix4);
                    collisionObject.setWorldTransform(matrix4);
                    gameObject.setPosition(new Vector3d(oldPosition.x, oldPosition.y, oldPosition.z));
                } else {
                    modelInstance.transform.set(matrix4);
                }

			}
        }
    }

    @Override
    public void stop(ProjectD projectD) {
        projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
    }

    public void exit(ProjectD projectD){
        this.stop(projectD);

        //Dispose physics objects
		for(java.util.Map.Entry<GameObject, Pair<ModelInstance, btCollisionObject>> entrySet : objectsMap.entrySet()) {
			entrySet.getValue().getValue().dispose();
		}
		dispatcher.dispose();
		collisionConfig.dispose();

		//Dispose graphic
        renderer.dispose();
    }

	private boolean checkCollision(btCollisionObject object0, btCollisionObject object1) {
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(object0);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(object1);
//        System.out.println(object0.getWorldTransform().getTranslation(new Vector3()).toString() + "   " + object1.getWorldTransform().getTranslation(new Vector3()).toString());


        btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);

        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

        boolean r = result.getPersistentManifold().getNumContacts() > 0;

        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
        result.dispose();
        info.dispose();
        co1.dispose();
        co0.dispose();

        return r;
	}



}
