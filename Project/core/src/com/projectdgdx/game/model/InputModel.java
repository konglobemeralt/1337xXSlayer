package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector2d;

/**
 * Created by Eddie on 2017-04-28.
 */
public class InputModel {
    private Vector2d leftStick = new Vector2d(0,0);
    private PressedData buttonA = new PressedData();
    private PressedData buttonB = new PressedData();
    private PressedData buttonX = new PressedData();
    private PressedData buttonY = new PressedData();

    public Vector2d getLeftStick() {
        return leftStick;
    }

    public void setButtonA(boolean isPressed) {
        buttonA.setPressed(isPressed);
    }
    public void setButtonB(boolean isPressed) {
        buttonB.setPressed(isPressed);
    }
    public void setButtonX(boolean isPressed) {
        buttonX.setPressed(isPressed);
    }
    public void setButtonY(boolean isPressed) {
        buttonY.setPressed(isPressed);
    }

    public PressedData getButtonA() {
        return buttonA;
    }
    public PressedData getButtonB() {
        return buttonB;
    }
    public PressedData getButtonX() {
        return buttonX;
    }
    public PressedData getButtonY() {
        return buttonY;
    }

    public void resetButtonCounts() {
        buttonA.resetCount();
        buttonB.resetCount();
        buttonX.resetCount();
        buttonY.resetCount();
    }

}
