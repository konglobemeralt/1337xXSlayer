package com.projectdgdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.controller.*;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.view.BaseShader;
import com.projectdgdx.game.utils.AssetManager;
import com.projectdgdx.game.utils.AssetsFinder;
import com.projectdgdx.game.utils.Map;

import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.utils.MapParser;
import com.sun.org.apache.xpath.internal.objects.XBoolean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ProjectD extends ApplicationAdapter {

    private GameState currentState;
    private List<InputController> inputController = new ArrayList();

    private InputMultiplexer multiplexer;

    public GameState getState() {
        return currentState;
    }

    public void setState(GameStates newState) {
        if(currentState != null){
            currentState.stop(this);
        }
        this.currentState = gameStates.get(newState);
        this.currentState.start(this);
    }

    public List<InputController> getInpuControllers() {
        return inputController;
    }

    public void setInpuControllers(List<InputController> inputController) {
        this.inputController = inputController;
    }


	public HashMap<GameStates, GameState> gameStates = new HashMap<GameStates, GameState>();


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
        } else {
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
        this.currentState.exit(this);
    }

    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

}
