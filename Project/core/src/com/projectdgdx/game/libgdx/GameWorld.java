package com.projectdgdx.game.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.controller.ProjectD;
import com.projectdgdx.game.model.modelStructure.Entity;
import com.projectdgdx.game.model.modelStructure.GameObject;
import com.projectdgdx.game.model.staticInteractable.Machine;
import com.projectdgdx.game.model.staticInteractable.Spotlight;
import com.projectdgdx.game.model.staticInteractable.SpotlightControlBoard;
import com.projectdgdx.game.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameWorld {
	private PerspectiveCamera cam;
	private List<Spotlight> lightList = new ArrayList<>();
	private HashMap<GameObject, GameObjectContainer> objectsMap = new HashMap<>();

	//Bullet
	private btDefaultCollisionConfiguration collisionConfig;
	private btCollisionDispatcher dispatcher;
	private btBroadphaseInterface broadphase;
	private btDynamicsWorld dynamicsWorld;
	private btConstraintSolver constraintSolver;
	private DebugDrawer debugDrawer;
	private Array<AnimationController> animationControllers = new Array<AnimationController>();
	private Random rand = new Random();


	public List<Spotlight> getLightList() {
		return lightList;
	}

	public void init(List<GameObject> gameObjects) {
		initPhysics();

		createCamera();
		generateGameObjectContainers(gameObjects);
	}

	public void generateGameObjectContainers(List<GameObject> gameObjects) {
		for (GameObject gameObject : gameObjects) {
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
				gameObjectContainer = new EntityContainer((Entity)gameObject, modelInstance, dynamicsWorld);
			} else {
				gameObjectContainer = new GameObjectContainer(gameObject, modelInstance, dynamicsWorld);
			}


			//Check for spotlightControl and get spotlight
			if(gameObject instanceof SpotlightControlBoard) {
				lightList.add(((SpotlightControlBoard)gameObject).getSpotlight());
			}

			//Check for machine and get spotlight
			if(gameObject instanceof Machine) {
				lightList.add(((Machine)gameObject).getSpotLight());
				((Machine)gameObject).updateTimer();
			}

			//Add GameObject and ModelInstance to a map that keeps them together
			objectsMap.put(gameObject, gameObjectContainer);
		}
	}

	private List<EntityContainer>  getEntityContainers() {
		List<EntityContainer> entityContainers = new ArrayList<>();
		for(GameObjectContainer gameObjectContainer : objectsMap.values()) {
			if(gameObjectContainer instanceof EntityContainer) {
				entityContainers.add((EntityContainer)gameObjectContainer);
			}
		}
		return entityContainers;
	}

	public void updateEntities() {
		for(EntityContainer entityContainer : getEntityContainers()) {
			btRigidBody physicsObject = entityContainer.getPhysicsObject();
			Entity entity = entityContainer.getEntity();
			if(entity.getMoveForce().getLength() != 0) {
				Vector3d moveForce = entity.getMoveForce().scale(5);
				entityContainer.applyForce(VectorConverter.convertToLibgdx(moveForce));
				Vector3d rotation = new Vector3d(moveForce.x, 0, -moveForce.z);
				entityContainer.updateRotation(new Vector3(0,rotation.getXZAngle() + 90, 0));
				physicsObject.setDamping(0.7f, 0);
			} else {
				physicsObject.setDamping(1f, 0);
			}

			//Limit linear velocity
			Vector3 linearVelocity = physicsObject.getLinearVelocity();
			if(linearVelocity.len() > Config.MOVE_SPEED) {
				linearVelocity.scl(Config.MOVE_SPEED/linearVelocity.len());
				physicsObject.setLinearVelocity(physicsObject.getLinearVelocity().clamp(-Config.MOVE_SPEED, Config.MOVE_SPEED));
			}
		}
	}

	/**
	 * Creates a camera to be used for rendering using the settings in the config file
	 *
	 */
	private void createCamera(){
		cam = new PerspectiveCamera(Config.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Config.CAMERA_X, Config.CAMERA_Y, Config.CAMERA_Z);
		cam.lookAt(0f, 0f, 0f);
		cam.near = Config.CAMERA_NEAR;
		cam.far = Config.CAMERA_FAR;
		cam.update();
	}

	public PerspectiveCamera  getCamera() {
		return cam;
	}

	private void initPhysics() {
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld.setGravity(new Vector3(0, 0, 0));

		if(Config.DEBUG) {
			debugDrawer = new DebugDrawer();
			debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
			dynamicsWorld.setDebugDrawer(debugDrawer);
		}
	}

	public void dispose(){

		//Dispose physics objects
		dynamicsWorld.dispose();
		collisionConfig.dispose();
		dispatcher.dispose();
		broadphase.dispose();
		constraintSolver.dispose();
		for(GameObjectContainer gameObjectContainer : objectsMap.values()) {
			gameObjectContainer.dispose();
		}
	}

	public void update() {
		//Update world physics
		final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
		updateEntities();
		dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
		animate();

		if(Config.DEBUG) {
			debugDrawer.begin(cam);
			dynamicsWorld.debugDrawWorld();
			debugDrawer.end();
		}
	}

	private void animate(){
		for(AnimationController controllerInstance: animationControllers){
			controllerInstance.update(Gdx.graphics.getDeltaTime() + rand.nextFloat() * 0.02f);
		}
	}

	public List<ModelInstance> getGraphicObjects() {
		List<ModelInstance> graphicObjects = new ArrayList<>();
		for(GameObjectContainer gameObjectContainer : objectsMap.values()) {
			graphicObjects.add(gameObjectContainer.getGraphicObject());
		}
		return graphicObjects;
	}
}
