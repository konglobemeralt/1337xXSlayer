package com.projectdgdx.game.controller;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.model.InputModel;

/**
 * This controller handles input from a xBox controller. It is a valid input for the game.
 *
 * Created by Eddie on 2017-04-28.
 */
public class XboxController extends InputController implements ControllerListener {

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println("ButtonDown: " + buttonCode);
        if(buttonCode == 0) { // a
            this.getModel().setButtonA(true);
        }
        if(buttonCode == 1) { // b
            this.getModel().setButtonB(true);
        }
        if(buttonCode == 2) { // x
            this.getModel().setButtonX(true);
        }
        if(buttonCode == 3) { // y
            this.getModel().setButtonY(true);
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        System.out.println("ButtonUp: " + buttonCode);
        if(buttonCode == 0) { // a
            System.out.println(this.getModel().getButtonA().getPressedCount());
            this.getModel().setButtonA(false);
        }
        if(buttonCode == 1) { // b
            this.getModel().setButtonB(false);
        }
        if(buttonCode == 2) { // x
            this.getModel().setButtonX(false);
        }
        if(buttonCode == 3) { // y
            this.getModel().setButtonY(false);
        }
        return false;
    }


    /**
     * This method checks for the deadzone of a xbox controller.
     * @param value
     * @return True or false of being within the deadzone.
     */
    private boolean isWithinDeadZone(float value) {
        return (value <= 0.2f && value >= -0.2f);
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
//        System.out.println("AxisCode: " + axisCode + " : " + value);

//        System.out.println(value);
        if(isWithinDeadZone(value)) {
            value = 0;
        }
        if(axisCode == 1 ) { //Left-right left stick
            getModel().getLeftStick().x = value;
        }
        if(axisCode == 0) { //Top-down left stick
            getModel().getLeftStick().z = -value;
        }
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
