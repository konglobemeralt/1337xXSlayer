package com.projectdgdx.game.controller;

import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.utils.AssetManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectD extends ApplicationAdapter {

    private iGameState currentState;
    private List<InputController> inputController = new ArrayList();

    private InputMultiplexer multiplexer;

    public iGameState getState() {
        return currentState;
    }

    public void setState(GameStates newState) {
        for(InputController inputController : getInpuControllers()) {
            inputController.getModel().resetButtonCounts();
        }
        if(currentState != null){
            currentState.stop(this);
        }
        this.currentState = gameStates.get(newState);
        this.currentState.start(this);
    }

    public void resetState(GameStates stateToReset) {
        gameStates.get(stateToReset).stop(this);
        gameStates.get(stateToReset).init(this);
    }

    public List<InputController> getInpuControllers() {
        return inputController;
    }

    public void setInpuControllers(List<InputController> inputController) {
        this.inputController = inputController;
    }


	public HashMap<GameStates, iGameState> gameStates = new HashMap<GameStates, iGameState>();


    @Override
    public void create(){

        multiplexer = new InputMultiplexer();
        if(Controllers.getControllers().size >= 1) {
            for(Controller controller : Controllers.getControllers()) {
                XboxController xboxController = new XboxController();
                controller.addListener(xboxController);
                xboxController.setModel(new InputModel());
                inputController.add(xboxController);
            }
        }

        //Add a keyboard controller if there are not enough gamepads
        if(Controllers.getControllers().size < 4) {
            KeyboardController keyboardController = new KeyboardController();
            keyboardController.setModel(new InputModel());
            multiplexer.addProcessor(keyboardController);
            inputController.add(keyboardController);
        }

        gameStates.put(GameStates.MAINMENU, new MainMenuState());
        gameStates.get(GameStates.MAINMENU).init(this);
        gameStates.put(GameStates.SETTINGS, new SettingsState());
        gameStates.get(GameStates.SETTINGS).init(this);
    	gameStates.put(GameStates.INGAME, new InGameState());
    	gameStates.get(GameStates.INGAME).init(this);
        gameStates.put(GameStates.ENDGAME, new EndGameState());
    	gameStates.get(GameStates.ENDGAME).init(this);


        if(Config.DEBUG) {
        	this.setState(GameStates.INGAME);
		} else {
			this.setState(GameStates.MAINMENU);
		}




    }

    @Override
    public void render(){
        this.currentState.update(this);

    }

    @Override
    public void dispose(){
        AssetManager.dispose();
        this.currentState.exit(this);
    }

    @Override
    public void resize (int width, int height) {
        for(iGameState gameState : gameStates.values()) {
            gameState.resize(width, height);
        }
    }

    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

}
