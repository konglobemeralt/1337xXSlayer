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
public class InGameState implements GameState {

	private InputMultiplexer multiplexer;
	private  Array<AnimationController> animationControllers = new Array<AnimationController>();

	private PerspectiveCamera cam;
	private Spotlight spotlight;
	private CameraInputController camController;

	private HashMap<InputController, PlayableCharacter> controllerPlayerMap = new HashMap<>();
	private HashMap<GameObject, Pair<ModelInstance, btCollisionObject>> objectsMap = new HashMap<>();


	//Bullet
	btDefaultCollisionConfiguration collisionConfig;
	btCollisionDispatcher dispatcher;
	CollisionListener collisionListener;
	btBroadphaseInterface broadphase;
	btCollisionWorld collisionWorld;

	//Collision flags
	final static short STATIC_FLAG = 1<<8;
	final static short ENTITY_FLAG = 1<<9;
	final static short NODE_FLAG = -1;


	private RenderManager renderer;
	private Random rand;
	private Map map;

	class CollisionListener extends ContactListener {
		@Override
		public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			GameObject gameObject0 = map.getGameObjects().get(userValue0);
			GameObject gameObject1 = map.getGameObjects().get(userValue1);
//			instances.get(userValue0).moving = false;
//			instances.get(userValue1).moving = false;
//			if(gameObject0 instanceof Entity && gameObject1 instanceof StaticObject) {
//				System.out.println("COliison");
//				gameObject0.setPosition(gameObject0.getOldPosition());
//			}else if(gameObject0 instanceof StaticObject && gameObject1 instanceof Entity) {
//				gameObject1.setPosition(gameObject0.getOldPosition());
//			}
//			System.out.println(map.getGameObjects().get(userValue0).getId() + " collision with: " + map.getGameObjects().get(userValue1).getId());
			return true;
		}
	}

	/**
	 * Creates a camera to be used for rendering using the settings in the config file
	 *
	 */
	private void createCamera(){
		cam = new PerspectiveCamera(Config.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(40f, 50f, 40f);
		cam.lookAt(0f, 0f, 0f);
		cam.near = Config.CAMERA_NEAR;
		cam.far = Config.CAMERA_FAR;
		camController = new CameraInputController(cam);
		camController.forwardTarget = true;

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
				if(inputModel.getLeftStick().getLength() != 0) {
					//TODO Setting of player position must go through PlayerCharacters move()-function or the states will not work
					player.setRotation(new Vector3d(0, inputModel.getLeftStick().getAngle() + 90, 0));
					player.getPosition().add(new Vector3d(deltaTime * inputModel.getLeftStick().x * Config.MOVE_SPEED, 0, deltaTime * -inputModel.getLeftStick().z * Config.MOVE_SPEED)); //TODO Will be replaced by PlayerCharacters move function.
				}
				if(inputModel.getButtonA().getPressedCount()>0){
					player.honestInteract(map.getHonestInteractables());
				}
				if(inputModel.getButtonB().getPressedCount()>0){
					player.dishonestInteract(map.getDishonestInteractables());
				}
				if(inputModel.getButtonX().getPressedCount()>0){
					player.useAbility();
				}


			}
			inputModel.resetButtonCounts();

			//Checks if escape button has been pressed.
			if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
				this.stop(projectD);
				projectD.setState(GameStates.SETTINGS);
			}
		}
	}

	/**
	 * Handles workers currently in play
	 *
	 */
	private void handleWorkers(){
	    for (Worker worker : map.getWorkers()){
	        worker.reactOnUpdate();
        }
    }


	public void update(ProjectD projectD){
		handleInput(projectD);
		handleWorkers();
		animate();
		render();
		updateModelInstaces();
		collisionWorld.performDiscreteCollisionDetection();
	}

	public void init(ProjectD projectD){
		//Bullet inits
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
		collisionListener = new CollisionListener();

		rand = new Random();

		MapParser parser = new MapParser();
		map = parser.parse("BasicMapAdvanced");

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

	/**
	 * Converts map data to game objects to be used in model.
	 * Adds animation controllers and collision objects when appropriate.
	 */
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
						//Do something when the animation ends.
					}

					@Override
					public void onLoop(AnimationController.AnimationDesc animation) {
						//Do something for every loop of an animation.
					}
				});
				animationControllers.add(animController);
			}
			//Add a box around object that will be used for physics
			BoundingBox boundingBox = modelInstance.model.calculateBoundingBox(new BoundingBox());

			btCollisionShape collisionShape = new btBoxShape(boundingBox.getDimensions(new Vector3()).scl(0.5f));
			btCollisionObject collisionObject = new btCollisionObject();
			collisionObject.setCollisionShape(collisionShape);
			collisionObject.setWorldTransform(modelInstance.transform);
			collisionObject.setUserValue(map.getGameObjects().indexOf(gameObject));
			collisionObject.setCollisionFlags(collisionObject.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);

			if(gameObject instanceof Entity) {
				collisionWorld.addCollisionObject(collisionObject, ENTITY_FLAG, STATIC_FLAG);
			}else if(gameObject instanceof Floor || gameObject instanceof AINode) {
				//Ignore floor
				collisionObject = null;
			} else {
				collisionWorld.addCollisionObject(collisionObject, STATIC_FLAG, ENTITY_FLAG);
			}
			//Check for spotlightControl and get spotlight
			if(gameObject instanceof SpotlightControlBoard) {
				spotlight = ((SpotlightControlBoard)gameObject).getSpotlight();
			}


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

		spotlight = new Spotlight(new Vector3d(1, 1, 1), new Vector3d(1, 1, 1), new Vector3d(1, 1, 1), "null", 1);

	}

	/**
	 * Updates the graphical objects
	 *
	 */
	public void updateModelInstaces() {
		for(java.util.Map.Entry<GameObject, Pair<ModelInstance, btCollisionObject>> entrySet : objectsMap.entrySet()) {
			ModelInstance modelInstance = entrySet.getValue().getKey();
			GameObject gameObject = entrySet.getKey();
			btCollisionObject collisionObject = entrySet.getValue().getValue();

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
				modelInstance.transform.set(matrix4);
				collisionObject.setWorldTransform(matrix4);
			}

			//Check for spotlightControl and update spotlight
			if(gameObject instanceof SpotlightControlBoard) {
				spotlight = ((SpotlightControlBoard)gameObject).getSpotlight();
			}
		}
	}

	@Override
	public void stop(ProjectD projectD) {
		projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
	}

	public void exit(ProjectD projectD){
		//Dispose physics objects
		for(java.util.Map.Entry<GameObject, Pair<ModelInstance, btCollisionObject>> entrySet : objectsMap.entrySet()) {
			btCollisionObject collisionObject = entrySet.getValue().getValue();
			if(collisionObject != null) {
				collisionObject.dispose();
			}
		}
		dispatcher.dispose();
		collisionConfig.dispose();
		collisionListener.dispose();
		collisionWorld.dispose();
		broadphase.dispose();

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