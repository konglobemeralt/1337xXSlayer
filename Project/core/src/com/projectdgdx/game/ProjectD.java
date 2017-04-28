package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import java.util.List;
import java.util.Random;

public class ProjectD extends ApplicationAdapter {

    private GameState currentState = new MainMenuState();
    private List<InputController> inputController = new ArrayList();

    public GameState getState() {
        return currentState;
    }

    public void setState(GameState currentState) {
        this.currentState = currentState;
    }

    public List<InputController> getInpuControllers() {
        return inputController;
    }

    public void setInpuControllers(List<InputController> inputController) {
        this.inputController = inputController;
    }

    @Override
    public void create(){
        this.currentState.init(this);
        XboxController xboxController = new XboxController();
        Controllers.getControllers().get(0).addListener(xboxController);
        xboxController.setModel(new InputModel());
        inputController.add(xboxController);

    }

    @Override
    public void render(){
        this.currentState.update(this);

    }

    @Override
    public void dispose(){
        this.currentState.exit();
    }



}
