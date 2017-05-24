package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.libgdx.GameWorld;
import com.projectdgdx.game.libgdx.MapParser;
import com.projectdgdx.game.model.ai.BasicNode;
import com.projectdgdx.game.model.ai.Worker;
import com.projectdgdx.game.model.dataHolding.Map;
import com.projectdgdx.game.model.input.InputModel;
import com.projectdgdx.game.model.gameplay.PlayableCharacter;
import com.projectdgdx.game.model.gameplay.Machine;
import com.projectdgdx.game.model.gameplay.Spotlight;
import com.projectdgdx.game.model.gameplay.SpotlightControlBoard;
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
	private boolean gameRunning = true;
	private Events gameEndingEvent;

	private InputMultiplexer multiplexer;
	private  Array<AnimationController> animationControllers = new Array<AnimationController>();

	private List<Spotlight> lightList = new ArrayList<>();
	private CameraInputController camController;

	private HashMap<InputController, PlayableCharacter> controllerPlayerMap = new HashMap<>();

	private RenderManager renderer;
	private Random rand;
	private Map map;

	private GameWorld gameWorld;

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

	private void updateCamera(ProjectD projectD){
		PerspectiveCamera cam = gameWorld.getCamera();
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
		renderer.render(gameWorld.getCamera(), lightList, gameWorld.getGraphicObjects()); //Pass render Instances and camera to render
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
		//Update world physics
		gameWorld.update();


		handleInput(projectD);
		handleWorkers();
		updateTimerLabel();
		handleLights();
		animate();
		render();

		//Check if game over
		if(!gameRunning) {

			enterEndgame(projectD);
		}
	}

	private void enterEndgame(ProjectD projectD){
		projectD.takePrettyScreenshot();
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

	private void initWorkers() {
		// Init nodes
		rand = new Random();
		List<BasicNode> nodeList =  map.getAINodes();
		for(BasicNode node : nodeList) {
			node.init(map.getAINodes());
		}

		for (Worker worker : map.getWorkers()){
			worker.setTargetNode(nodeList.get(rand.nextInt(nodeList.size())));
		}
	}

	private void initInputs(List<InputController> inputs) {
		int i = 0;
		List<PlayableCharacter> players = map.getPlayers();
		for(InputController input : inputs) {
			if(i < players.size()) {
				controllerPlayerMap.put(input, players.get(i));
			}
			i++;
		}
	}

	private void initView() {
		//Init view
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
	}

	// TODO docs
	public void init(ProjectD projectD){


		//Reset game running
		gameRunning = true;

		//Remove all existing timers
		Timer.removeTimers();


		//Parse and create map
		MapParser parser = new MapParser();
		map = parser.parse(Config.LEVEL_IN_PLAY);

		gameWorld = new GameWorld();
		gameWorld.init(map.getGameObjects());

		initWorkers();
		initInputs(projectD.getInpuControllers());
		initView();
		initMachines();

		// Make this a listener
		EventSender.getEventSender().getListeners().clear();
		EventSender.getEventSender().addListener(this);

	}

	private void initMachines(){
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
		renderer.init(gameWorld.getLightList());
		Timer.resumeTimers();
		//TODO rename this method
		initTimer();
	}

	@Override
	public void stop(ProjectD projectD) {
		projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
		lightList.clear();
		Timer.pauseTimers();
	}

	@Override
	public void resize(int width, int height) {

	}

	public void exit(ProjectD projectD){
		//Dispose game world
		gameWorld.dispose();


		//Dispose graphic
		renderer.dispose();

		this.stop(projectD);
	}

	private void updateTimerLabel(){
		int min = map.getEndgameCounter().getEndgameTimer().getTimerValue()/60;
		int sec = map.getEndgameCounter().getEndgameTimer().getTimerValue()%60;

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

	public void initTimer() {
		map.getEndgameCounter().setEndgameTimer(new Timer(Config.GAME_TIME,1000));
		map.getEndgameCounter().getEndgameTimer().addListener(this);
		map.getEndgameCounter().getEndgameTimer().start();
	}

	@Override
	public void timeIsUp() {
		EventSender.getEventSender().sendTimeOutEnd();
	}



}