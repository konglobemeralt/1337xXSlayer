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

	static class MyMotionState extends btMotionState {
		Matrix4 transform;
		@Override
		public void getWorldTransform (Matrix4 worldTrans) {
			worldTrans.set(transform);
		}
		@Override
		public void setWorldTransform (Matrix4 worldTrans) {
			System.out.println("asdasd");
			transform.set(worldTrans);
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
			//Add a box around object that will be used for physics
			BoundingBox boundingBox = modelInstance.model.calculateBoundingBox(new BoundingBox());
			btCollisionShape collisionShape;
			collisionShape = new btBoxShape(boundingBox.getDimensions(new Vector3()).scl(0.5f));



			btRigidBody collisionObject;
			if(gameObject instanceof StaticObject) {
				localInertia.set(0, 0, 0);
				collisionObject = new btRigidBody(0f, null, collisionShape, new Vector3());
			} else {
				collisionShape.calculateLocalInertia(1f, localInertia);
				collisionObject = new btRigidBody(1f, null, collisionShape, localInertia);
			}

			collisionObject.setCollisionShape(collisionShape);
//			collisionObject.setFriction(6f);
			collisionObject.setLinearFactor(new Vector3(1,1,1));
			collisionObject.setAngularFactor(new Vector3(0,0,0));

			collisionObject.proceedToTransform(modelInstance.transform);
			MyMotionState motionState = new MyMotionState();
			motionState.transform = modelInstance.transform;
			collisionObject.setUserValue(map.getGameObjects().indexOf(gameObject));
			collisionObject.setCollisionFlags(collisionObject.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);

			collisionObject.setMotionState(motionState);

			if(gameObject instanceof Entity) {
				dynamicsWorld.addRigidBody(collisionObject, ENTITY_FLAG, STATIC_FLAG);
			}else if(gameObject instanceof AINode) {
				//Ignore ainode
				collisionObject = null;
//			}else if(gameObject instanceof Floor) {
//				collisionObject = null;
			} else if(gameObject instanceof StaticObject) {
				dynamicsWorld.addRigidBody(collisionObject, STATIC_FLAG, ALL_FLAG);
			}




			//Add GameObject and ModelInstance to a map that keeps them together
			objectsMap.put(gameObject, new Pair<ModelInstance, btCollisionObject>(modelInstance, collisionObject));
		}

	}

	public void updateModelInstaces() {
		for(java.util.Map.Entry<GameObject, Pair<ModelInstance, btCollisionObject>> entrySet : objectsMap.entrySet()) {
			ModelInstance modelInstance = entrySet.getValue().getKey();
			GameObject gameObject = entrySet.getKey();
			btCollisionObject collisionObject = entrySet.getValue().getValue();

			//Check for entity
			if(gameObject instanceof Entity) {
//				Vector3 position = VectorConverter.convertToLibgdx(gameObject.getPosition());
//				Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
//				Quaternion quaternion = new Quaternion();
//
//				//TODO ugly solution for rotation
//				Vector3 rotation = VectorConverter.convertToLibgdx(gameObject.getRotation());
//				modelInstance.transform.setToRotation(0,0,0,0,0,0);
//				modelInstance.transform.rotate(Vector3.X, rotation.x);
//				modelInstance.transform.rotate(Vector3.Y, rotation.y);
//				modelInstance.transform.rotate(Vector3.Z, rotation.z);
//				quaternion = modelInstance.transform.getRotation(new Quaternion());
//
//				Matrix4 matrix4 = new Matrix4(position, quaternion, scale);
//				modelInstance.transform.set(matrix4);
//				collisionObject.getWorldTransform(modelInstance.transform);
//				collisionObject.setWorldTransform(matrix4);

			}


//			if(modelInstance != null && collisionObject != null) {
//				collisionObject.getWorldTransform(modelInstance.transform);
//			}

		}
	}

	private void createCamera(){
		cam = new PerspectiveCamera(Config.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(40f, 50f, 40f);
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
				btRigidBody rigidBody = ((btRigidBody)objectsMap.get(player).getValue());
				Quaternion rotation = new Quaternion();
//				rotation.set(Vector3.X, 0);
//				rotation.set(Vector3.Y, inputModel.getLeftStick().getAngle() + 90);
//				rotation.set(Vector3.Z, 0);
				rotation.setEulerAngles(inputModel.getLeftStick().getAngle() + 90, 0, 0);


//				System.out.println(inputModel.getLeftStick().getLength());
				if(inputModel.getLeftStick().getLength() != 0) {
//					rigidBody.setCenterOfMassTransform(transform);

					Matrix4 transform = rigidBody.getWorldTransform();
					Vector3 position = transform.getTranslation(new Vector3().add(0,0.2f,0));
					Vector3 scale = VectorConverter.convertToLibgdx(player.getScale());


//					scale = new Vector3(1,1,1);

					rigidBody.setWorldTransform(new Matrix4(position,rotation,scale));
//					rigidBody.setCenterOfMassTransform(rigidBody.getCenterOfMassTransform().set(rotation));

					rigidBody.applyCentralForce(new Vector3(deltaTime * inputModel.getLeftStick().x * 10000, 0, deltaTime * inputModel.getLeftStick().z * 10000));
					rigidBody.setDamping(0.4f, 0);
				}else {
					rigidBody.setDamping(1f, 0);

				}
				Vector3 linearVelocity = rigidBody.getLinearVelocity();
				if(linearVelocity.len() > 30) {
					linearVelocity.scl(30f/linearVelocity.len());
					rigidBody.setLinearVelocity(rigidBody.getLinearVelocity().clamp(-30f, 30f));
				}



			}



			if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
				this.stop(projectD);
				projectD.setState(GameStates.SETTINGS);
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
		updateModelInstaces();


//		animate();
		render();


	}

	public void init(ProjectD projectD){
		//Bullet inits
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld.setGravity(new Vector3(0, -1, 0));
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
		for(java.util.Map.Entry<GameObject, Pair<ModelInstance, btCollisionObject>> entrySet : objectsMap.entrySet()) {
			btCollisionObject collisionObject = entrySet.getValue().getValue();
			if(collisionObject != null) {
				collisionObject.dispose();
			}
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