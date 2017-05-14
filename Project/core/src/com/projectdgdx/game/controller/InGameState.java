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
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternion;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.*;
import com.projectdgdx.game.model.AI.AINode;
import com.projectdgdx.game.utils.*;
import com.projectdgdx.game.utils.Map;
import com.projectdgdx.game.view.RenderManager;
import javafx.util.Pair;

import java.util.*;

/**
 * InGameState controls everything that is ingame.
 * Created by Eddie on 2017-04-28.
 */
public class InGameState implements iGameState {

	private InputMultiplexer multiplexer;
	private  Array<AnimationController> animationControllers = new Array<AnimationController>();

	private PerspectiveCamera cam;
	private Spotlight spotlight;
	private CameraInputController camController;

	private HashMap<InputController, PlayableCharacter> controllerPlayerMap = new HashMap<>();
	private HashMap<GameObject, GameObjectContainer> objectsMap = new HashMap<>();

	//Bullet
	btDefaultCollisionConfiguration collisionConfig;
	btCollisionDispatcher dispatcher;
	CollisionListener collisionListener;
	btBroadphaseInterface broadphase;
	btDynamicsWorld dynamicsWorld;
	btConstraintSolver constraintSolver;

//	MyMotionState motionState;

	//Collision flags
	final static short STATIC_FLAG = 1<<8;
	final static short ENTITY_FLAG = 1<<9;
	final static short ALL_FLAG = -1;


	private RenderManager renderer;
	private Random rand;
	private Map map;

	class CollisionListener extends ContactListener {
		@Override
		public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			GameObject gameObject0 = map.getGameObjects().get(userValue0);
			GameObject gameObject1 = map.getGameObjects().get(userValue1);
//			System.out.println("COLLISION!");
//			}
			System.out.println(gameObject0.getId() + " collision with: " + gameObject1.getId());
			return true;
		}
	}

	private void generateRenderInstances(){

		Vector3 localInertia = new Vector3();
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

			GameObjectContainer gameObjectContainer;
			if(gameObject instanceof Entity) {
				gameObjectContainer = new EntityContainer(gameObject, modelInstance, dynamicsWorld, map);
			} else {
				gameObjectContainer = new GameObjectContainer(gameObject, modelInstance, dynamicsWorld, map);
			}


			//Add GameObject and ModelInstance to a map that keeps them together
			objectsMap.put(gameObject, gameObjectContainer);
		}

	}

	private void createCamera(){
		cam = new PerspectiveCamera(Config.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Config.CAMERA_X, Config.CAMERA_Y, Config.CAMERA_Z);
		cam.lookAt(0f, 0f, 0f);
		cam.near = Config.CAMERA_NEAR;
		cam.far = Config.CAMERA_FAR;
		cam.update();
	}

    private void updateCamera(ProjectD projectD){
        cam.fieldOfView = Config.CAMERA_FOV;

        camController = new CameraInputController(cam);
        camController.forwardTarget = true;

        this.multiplexer = new InputMultiplexer(projectD.getMultiplexer()); //Handle debug camera control input
        //Add a camera controller to the input multiplexer to enable a movable debug camera.
        multiplexer.addProcessor(camController);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);
        cam.update();
    }


	/**
	 * Renders the game
	 *
	 */
	public void render () {
		renderer.render(cam, spotlight, objectsMap.values()); //Pass render Instances and camera to render
	}

	/**
	 * Updates animation controllers
	 *
	 */
	private void animate(){
		for(AnimationController controllerInstance: animationControllers){
			controllerInstance.update(Gdx.graphics.getDeltaTime() + rand.nextFloat() * 0.02f);
		}
	}

	/**
	 * Handles user input.
	 *
	 * @param projectD ProjectD
	 *
	 */
	private void handleInput(ProjectD projectD){
		float deltaTime = Gdx.graphics.getDeltaTime();
		for(InputController inputController : projectD.getInpuControllers()) {
			InputModel inputModel = inputController.getModel();
			if(controllerPlayerMap.containsKey(inputController)) {
				PlayableCharacter player = controllerPlayerMap.get(inputController);
				EntityContainer playerContainer = (EntityContainer)objectsMap.get(player);
				btRigidBody physicsObject = playerContainer.getPhysicsObject();

				if(inputModel.getLeftStick().getLength() != 0) {

					playerContainer.applyForce(new Vector3(deltaTime * inputModel.getLeftStick().x * 10000, 0, deltaTime * inputModel.getLeftStick().z * 10000));
					playerContainer.updateRotation(new Vector3(0, inputModel.getLeftStick().getAngle() + 90, 0));
					physicsObject.setDamping(0.6f, 0);
				}else {
					physicsObject.setDamping(1f, 0);
				}

				Vector3 linearVelocity = physicsObject.getLinearVelocity();
				if(linearVelocity.len() > 30) {
					linearVelocity.scl(30f/linearVelocity.len());
					physicsObject.setLinearVelocity(physicsObject.getLinearVelocity().clamp(-30f, 30f));
				}
                //Checks if escape button has been pressed.
                if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
                    this.stop(projectD);
                    projectD.setState(GameStates.SETTINGS);
                }
			}

		}
	}

	private void handleWorkers(){
	    for (Worker worker : map.getWorkers()){
	        worker.reactOnUpdate();
        }
    }

	public void update(ProjectD projectD){

//		handleWorkers();
		final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
		dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
		handleInput(projectD);
//		updateModelInstaces();


		animate();
		render();


	}

	public void init(ProjectD projectD){
		//Bullet inits
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
//		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld = new btSimpleDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld.setGravity(new Vector3(0, 0, 0));
		collisionListener = new CollisionListener();

		rand = new Random();

		MapParser parser = new MapParser();
		map = parser.parse("BasicMapTest");

		//Init nodes
		List<AINode> nodeList =  map.getAINodes();
		for(AINode node : nodeList) {
			node.init(map.getAINodes());
		}

		for (Worker worker : map.getWorkers()){
			worker.setTargetNode(nodeList.get(rand.nextInt(nodeList.size())));
		}

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


	@Override
	public void start(ProjectD projectD) {
		this.multiplexer = new InputMultiplexer(projectD.getMultiplexer()); //Handle debug camera control input
		createCamera();

		renderer = new RenderManager();
		renderer.init();

	}

	@Override
	public void stop(ProjectD projectD) {
		projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
	}

	public void exit(ProjectD projectD){
		//Dispose physics objects
		for(GameObjectContainer gameObjectContainer : objectsMap.values()) {
			gameObjectContainer.dispose();
		}
		dispatcher.dispose();
		collisionConfig.dispose();
		collisionListener.dispose();
		dynamicsWorld.dispose();
		broadphase.dispose();
		constraintSolver.dispose();
//		motionState.dispose();

		//Dispose graphic
		renderer.dispose();

		this.stop(projectD);
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