package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.utils.AssetManager;
import com.projectdgdx.game.utils.AssetsFinder;
import com.projectdgdx.game.utils.Map;
import com.projectdgdx.game.utils.MapParser;
import com.projectdgdx.game.view.BaseShader;
import com.projectdgdx.game.view.RenderManager;

import java.util.Random;

/**
 * Created by Eddie on 2017-04-28.
 */
public class InGameState implements GameState {

    private InputMultiplexer multiplexer;
    public Array<ModelInstance> instances = new Array<ModelInstance>();

    private PerspectiveCamera cam;
    private CameraInputController camController;

    RenderManager renderer;
    Random rand;
    Map map;





    private void moveModel(ModelInstance instance){

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            instance.transform.trn(Config.MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0, 0);
            //cam.position.set(cam.position.x + Config.MOVE_SPEED, cam.position.y, cam.position.z);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            instance.transform.trn(-Config.MOVE_SPEED * Gdx.graphics.getDeltaTime(), 0, 0);
            //cam.position.set(cam.position.x - Config.MOVE_SPEED, cam.position.y, cam.position.z);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            instance.transform.trn(0, 0, -Config.MOVE_SPEED * Gdx.graphics.getDeltaTime());
            //cam.position.set(cam.position.x, cam.position.y, cam.position.z -Config.MOVE_SPEED);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            instance.transform.trn(0, 0, Config.MOVE_SPEED * Gdx.graphics.getDeltaTime());
            //cam.position.set(cam.position.x, cam.position.y, cam.position.z +Config.MOVE_SPEED);
        }


    }


    public void update(ProjectD projectD){

        renderer.render(cam);

        //TODO Controller testing:
        //TODO MOVE ACTUAL OBJECTS INSTEAD OF GRAPHIC INSTANCES
		if(Controllers.getControllers().size >= 1) {
			ModelInstance modelInstance = this.instances.get(3);
			InputModel inputModel = projectD.getInpuControllers().get(0).getModel();
			Vector3 position = modelInstance.transform.getTranslation(new Vector3());
			Vector3 scale = modelInstance.transform.getScale(new Vector3());
			Quaternion quaternion = new Quaternion();
			quaternion.set(Vector3.Y, inputModel.getLeftStick().getAngle() + 90); //TODO fix the addition of 90 degrees
			Matrix4 matrix4 = new Matrix4(position, quaternion, scale);
			modelInstance.transform.set(matrix4);

			float deltaTime = Gdx.graphics.getDeltaTime();
			modelInstance.transform.trn(deltaTime * inputModel.getLeftStick().x * Config.MOVE_SPEED, 0, deltaTime * -inputModel.getLeftStick().z * Config.MOVE_SPEED);
		}else{
		    //if(instances.size>0)
            //moveModel(this.instances.get(3));
        }
    }


    public void createCamera(){
        cam = new PerspectiveCamera(Config.CAMERA_FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(110f, 120f, 135f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = Config.CAMERA_NEAR;
        cam.far = Config.CAMERA_FAR;
        camController = new CameraInputController(cam);
        camController.forwardTarget = true;
        multiplexer.addProcessor(camController);
        cam.update();
    }

    public void init(ProjectD projectD){
        rand = new Random();

        this.multiplexer = projectD.getMultiplexer(); //Handle debug camera control input

        MapParser parser = new MapParser();
        map = parser.parse("BasicMapTest");

        renderer = new RenderManager();
        renderer.init(map);
    }

    @Override
    public void start() {
        createCamera();
    }

    @Override
    public void stop() {

    }

    public void exit(){
        renderer.dispose();
    }



}
