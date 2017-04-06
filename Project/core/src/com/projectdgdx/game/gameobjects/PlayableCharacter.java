package com.projectdgdx.game.gameobjects;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Eddie on 2017-04-03.
 */
public abstract class PlayableCharacter extends Character implements ControllerListener {
    public PlayableCharacter(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    public void connected(Controller controller){
        System.out.println("Connected");
    }
    public void disconnected(Controller controller){
        System.out.println("Disconnected");
    }
    public boolean buttonDown (Controller controller, int buttonCode){
        if(buttonCode == 0){
            honestInteract();
        }
        if(buttonCode == 2){
            dishonestInteract();
        }
        if (buttonCode == 3){
            useAbility();
        }

        return false;
    }
    public boolean buttonUp (Controller controller, int buttonCode){
        return false;

    }
    public boolean axisMoved (Controller controller, int axisCode, float value){
        /*System.out.println("Axis code: "+axisCode);
        System.out.println("Axis value: "+value);
        System.out.println("-------------------------------------------------------------------");*/
        if(axisCode == 0 || axisCode == 2){
            this.addPositionY(value);
        }
        if(axisCode == 1 || axisCode == 3){
            this.addPositionX(value);
        }

        return false;

    }
    public boolean povMoved (Controller controller, int povCode, PovDirection value){
        return false;

    }
    public boolean xSliderMoved (Controller controller, int sliderCode, boolean value){
        return false;

    }
    public boolean ySliderMoved (Controller controller, int sliderCode, boolean value){
        return false;

    }
    public boolean accelerometerMoved (Controller controller, int accelerometerCode, Vector3 value){
        return false;

    }

    public abstract void dishonestInteract();

    public abstract void useAbility();
}
