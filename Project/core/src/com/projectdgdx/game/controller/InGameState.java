package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.*;

import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.model.*;
import com.projectdgdx.game.libgdx.EntityContainer;
import com.projectdgdx.game.libgdx.GameObjectContainer;
import com.projectdgdx.game.libgdx.MapParser;
import com.projectdgdx.game.model.AI.BasicNode;
import com.projectdgdx.game.model.Input.InputModel;
import com.projectdgdx.game.model.ModelStructure.Entity;
import com.projectdgdx.game.model.ModelStructure.GameObject;
import com.projectdgdx.game.model.Playables.PlayableCharacter;
import com.projectdgdx.game.model.StaticInteractable.Machine;
import com.projectdgdx.game.model.StaticInteractable.Spotlight;
import com.projectdgdx.game.model.StaticInteractable.SpotlightControlBoard;
import com.projectdgdx.game.utils.*;
import com.projectdgdx.game.view.RenderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * InGameState controls everything that is in game.
 * Created by Eddie on 2017-04-28.
 */
public class InGameState implements iGameState, iTimerListener, iEventListener {

	private Label gameTimeCountLabel;
	private Skin skin;
	private Table table;
	private Stage stage;
	private Timer gameTimer;
	private boolean gameRunning = true;
	private Events gameEndingEvent;

	private InputMultiplexer multiplexer;
	private  Array<AnimationController> animationControllers = new Array<AnimationController>();

	private PerspectiveCamera cam;
	private List<Spotlight> lightList = new ArrayList<>();
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
	DebugDrawer debugDrawer;

//	MyMotionState motionState;

	//Collision flags
	final static short STATIC_FLAG = 1<<8;
	final static short ENTITY_FLAG = 1<<9;
	final static short ALL_FLAG = -1;


	private RenderManager renderer;
	private Random rand;
	private Map map;

	@Override
	public void reactToEvent(Events event) {
		if (event == Events.MACHINES_DESTROYED_END || event == Events.SABOTEUR_CAUGHT || event == Events.STRIKE_END || event == Events.TIME_UPP){
			gameRunning = false;
			gameEndingEvent = event;
		}else if (event == Events.MACHINE_DESTRUCTION){
			map.getEndgameCounter().incDestroyedMachines();
		}else if (event == Events.SRIKING_WORKER){
			map.getEndgameCounter().incStrikingWorkers();
		}
	}

	class CollisionListener extends ContactListener {
		@Override
		public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
			GameObject gameObject0 = map.getGameObjects().get(userValue0);
			GameObject gameObject1 = map.getGameObjects().get(userValue1);
//			System.out.println("COLLISION!");
//			}
			//System.out.println(gameObject0.getId() + " collision with: " + gameObject1.getId());
			return true;
		}

//		@Override
//		public void onContactStarted (btCollisionObject colObj0, btCollisionObject colObj1) {
//			// implementation
//		}
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

			GameObjectContainer gameObjectContainer;
			if(gameObject instanceof Entity) {
				gameObjectContainer = new EntityContainer(gameObject, modelInstance, dynamicsWorld, map);
			} else {
				gameObjectContainer = new GameObjectContainer(gameObject, modelInstance, dynamicsWorld, map);
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

	public void updateEntities(List<Entity> entities) {
		for(Entity entity : entities) {
			EntityContainer entityContainer = ((EntityContainer)objectsMap.get(entity));
			btRigidBody physicsObject = entityContainer.getPhysicsObject();
			if(entity.getMoveForce().getLength() != 0) {
				Vector3d moveForce = entity.getMoveForce().scale(5);
				entityContainer.applyForce(VectorConverter.convertToLibgdx(moveForce));
				Vector3d rotation = new Vector3d(moveForce.x, 0, -moveForce.z);
				entityContainer.updateRotation(new Vector3(0,rotation.getXZAngle() + 90, 0));
				physicsObject.setDamping(0.7f, 0);
			} else {
				physicsObject.setDamping(1f, 0);
			}



			Vector3 linearVelocity = physicsObject.getLinearVelocity();
			if(linearVelocity.len() > 30) {
				linearVelocity.scl(30f/linearVelocity.len());
				physicsObject.setLinearVelocity(physicsObject.getLinearVelocity().clamp(-30f, 30f));
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
		List<ModelInstance> modelInstances = new ArrayList<>();
		for(GameObjectContainer gameObjectContainer : objectsMap.values()) {
			modelInstances.add(gameObjectContainer.getGraphicObject());
		}

		renderer.render(cam, lightList, modelInstances); //Pass render Instances and camera to render
		stage.act();
		stage.draw();
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
	 * Handles user input. // TODO more thorough docs
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

				//Move player in controller direction
				player.move(new Vector3d(inputModel.getLeftStick().x, 0, inputModel.getLeftStick().z));

                //Checks if escape button has been pressed.
                if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
                    this.stop(projectD);
                    projectD.setState(GameStates.SETTINGS);
                }

				if(inputModel.getButtonA().getPressedCount()>0){
					player.honestInteract(map.getHonestInteractables());
				}
				if(inputModel.getButtonB().getPressedCount()>0){
					player.dishonestInteract(map.getDishonestInteractables());
				}
				if(inputModel.getButtonX().getPressedCount()>0){
					player.useAbility(map.getCharacters());
				}
				inputModel.resetButtonCounts();
			}

		}
	}

	// TODO docs
	private void handleWorkers(){
	    for (Worker worker : map.getWorkers()){
	        worker.reactOnUpdate();
        }
    }

	private void handleLights(){
		lightList.clear();
		for (SpotlightControlBoard spotlightControlBoard : map.getSpotlightControlBoard()){
			lightList.add(spotlightControlBoard.getSpotlight());
			spotlightControlBoard.updateSpotlight();
		}

		for (Machine machine : map.getMachines()){
			lightList.add(machine.getSpotLight());
			machine.updateSpotlight();
		}
	}


	public void update(ProjectD projectD){
		final float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());


		dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
//		dynamicsWorld.stepSimulation(1);


		handleInput(projectD);
		handleWorkers();
		updateEntities(map.getEntities());
		updateTimerLabel();
		handleLights();
		animate();
		render();

		if(!gameRunning) {
			enterEndgame(projectD);
		}
		if(Config.DEBUG) {
			debugDrawer.begin(cam);
			dynamicsWorld.debugDrawWorld();
			debugDrawer.end();
		}
	}

	private void enterEndgame(ProjectD projectD){
		if (gameEndingEvent == Events.MACHINES_DESTROYED_END){
			projectD.setState(GameStates.ENDGAME_MACHINES);
		}else if (gameEndingEvent == Events.SABOTEUR_CAUGHT){
			projectD.setState(GameStates.ENDGAME_CAUGHT);
		}else if (gameEndingEvent == Events.STRIKE_END){
			projectD.setState(GameStates.ENDGAME_STRIKE);
		}else if (gameEndingEvent == Events.TIME_UPP){
			projectD.setState(GameStates.ENDGAME_TIMER);
		}
	}

	// TODO functional decomposition and docs
	public void init(ProjectD projectD){
		objectsMap = new HashMap<>();

		//Bullet inits
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
		// dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);

		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);

		if(Config.DEBUG) {
			debugDrawer = new DebugDrawer();
			debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
			dynamicsWorld.setDebugDrawer(debugDrawer);
		}
		dynamicsWorld.setGravity(new Vector3(0, 0, 0));
		collisionListener = new CollisionListener();


		rand = new Random();

		createCamera();

		MapParser parser = new MapParser();
		map = parser.parse(Config.LEVEL_IN_PLAY);

		// Init nodes
		List<BasicNode> nodeList =  map.getAINodes();
		for(BasicNode node : nodeList) {
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

		renderer = new RenderManager();
		renderer.init(lightList);

		this.table = new Table();
		this.stage = new Stage();
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		skin = new Skin(Gdx.files.internal(Config.UI_SKIN_PATH));

		//Create buttons
		gameTimeCountLabel = new Label("Tid kvar ", skin);

		this.table.add(gameTimeCountLabel).padBottom(Gdx.graphics.getHeight() - 70);
		this.stage.addActor(table);

		// Machine init
		this.machineInit();

		// Make this a listener
		EventSender.getEventSender().addListener(this);

	}

	private void machineInit(){
		List<Spotlight> mainSpotlights = new ArrayList<>();

		for(SpotlightControlBoard sCB : this.map.getSpotlightControlBoard()){
			mainSpotlights.add(sCB.getSpotlight());
		}

		for(Machine m : this.map.getMachines()){
			m.setBigDetectingSpotlights(mainSpotlights);
		}
	}


	@Override
	public void start(ProjectD projectD) {
		updateCamera(projectD);

		renderer = new RenderManager();
		renderer.init(lightList);
		updateTimer();

	}

	@Override
	public void stop(ProjectD projectD) {
		projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
		lightList.clear();
	}

	@Override
	public void resize(int width, int height) {

	}

	public void exit(ProjectD projectD){

		//Dispose physics objects
		dynamicsWorld.dispose();
		collisionConfig.dispose();
		dispatcher.dispose();
		collisionListener.dispose();
		broadphase.dispose();
		constraintSolver.dispose();
		for(GameObjectContainer gameObjectContainer : objectsMap.values()) {
			gameObjectContainer.dispose();
		}

		//Dispose graphic
		renderer.dispose();

		this.stop(projectD);
	}

	private boolean checkCollision(btCollisionObject object0, btCollisionObject object1) {
		CollisionObjectWrapper co0 = new CollisionObjectWrapper(object0);
		CollisionObjectWrapper co1 = new CollisionObjectWrapper(object1);
//      System.out.println(object0.getWorldTransform().getTranslation(new Vector3()).toString() + "   " + object1.getWorldTransform().getTranslation(new Vector3()).toString());


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

	private void updateTimerLabel(){
		int min = gameTimer.getTimerValue()/60;
		int sec = gameTimer.getTimerValue()%60;

		String minString = String.valueOf(min);
		String secString = String.valueOf(sec);

		if(sec < 10){
			secString = 0 + secString;
		}
		if(min < 10){
			minString = 0 + minString;
		}

		gameTimeCountLabel.setText("Tid kvar: " + minString + ":" + secString);
	}

	public void updateTimer() {
		this.gameTimer = new Timer(Config.GAME_TIME, 1000);
		this.gameTimer.start();
	}

	@Override
	public void timeIsUp() {
		EventSender.getEventSender().sendTimeOutEnd();
	}

}