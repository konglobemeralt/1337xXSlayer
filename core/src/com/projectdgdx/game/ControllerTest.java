package com.projectdgdx.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-03-21.
 */
public class ControllerTest implements ControllerListener {

    Controller controller;
    int num;
    PerspectiveCamera camera;
    public ControllerTest(Controller controller, int num, PerspectiveCamera camera) {
        this.controller = controller;
        this.num = num;
        controller.addListener(this);
        this.camera = camera;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println(num + ": Button down!  " + buttonCode);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        System.out.println(num + ": axisMoved: " + axisCode + "   " + value);
        camera.rotateAround(Vector3.Zero, new Vector3(0,1,0),1f);
        camera.update();
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
