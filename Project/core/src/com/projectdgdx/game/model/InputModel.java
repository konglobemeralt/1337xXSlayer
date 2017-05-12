package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector2d;

/**
 * InputModel provides a way to store input data which enables multiple types of controllers.
 * Like xbox game pad and keyboard. Is used as a wrapper class to adapt the controllers specific
 * input to one unison input that the model can use.
 *
 * Interface looks like the one on a xbox game pad because that is what our game is based around
 * but it works just as well with the keyboard.
 */
public class InputModel {
    private Vector2d leftStick = new Vector2d(0,0);
    private PressedData buttonA = new PressedData();
    private PressedData buttonB = new PressedData();
    private PressedData buttonX = new PressedData();
    private PressedData buttonY = new PressedData();
    private PressedData menuButton = new PressedData();

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
    public void setMenuButton(boolean isPressed) {
        menuButton.setPressed(isPressed);
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
    public PressedData getMenuButton() {
        return menuButton;
    }

    public void resetButtonCounts() {
        buttonA.resetCount();
        buttonB.resetCount();
        buttonX.resetCount();
        buttonY.resetCount();
        menuButton.resetCount();
    }

}
