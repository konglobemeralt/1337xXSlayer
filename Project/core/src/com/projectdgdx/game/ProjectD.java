package com.projectdgdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.controller.GameState;
import com.projectdgdx.game.controller.InGameState;
import com.projectdgdx.game.controller.InputController;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.view.BaseShader;
import com.projectdgdx.game.utils.AssetManager;
import com.projectdgdx.game.utils.AssetsFinder;
import com.projectdgdx.game.utils.Map;

import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.utils.MapParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProjectD extends ApplicationAdapter {

    GameState currentstate = new InGameState();
    List<InputController> inpuControllers = new ArrayList();

    public GameState getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(GameState currentstate) {
        this.currentstate = currentstate;
    }

    public List<InputController> getInpuControllers() {
        return inpuControllers;
    }

    public void setInpuControllers(List<InputController> inpuControllers) {
        this.inpuControllers = inpuControllers;
    }

    @Override
    public void create(){
        this.currentstate.init(this);
    }

    @Override
    public void render(){
        this.currentstate.update(this);

    }

    @Override
    public void dispose(){
        this.currentstate.exit();
    }



}
