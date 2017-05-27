package com.projectdgdx.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Hampus on 2017-05-04.
 *
 * This class handles inputs from a keyboard. It extends InputController and is therefore a valid input for the game.
 */
public class KeyboardController extends InputController implements InputProcessor {

    private boolean upDown = false;
    private boolean downDown = false;
    private boolean leftDown = false;
    private boolean rightDown = false;

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 51 || keycode == 19) { //Up
            upDown = true;
            getModel().getLeftStick().z = -1;
        }
        if(keycode == 29 || keycode == 21) { //Left
            leftDown = true;
            getModel().getLeftStick().x = -1;
        }

        if(keycode == 47 || keycode == 20) { //Down
            downDown = true;
            getModel().getLeftStick().z = 1;
        }

        if(keycode == 32 || keycode == 22) { //Right
            rightDown = true;
            getModel().getLeftStick().x = 1;
        }

        if(keycode == 31) { //C button
            getModel().setButtonA(true);
        }

        if(keycode == 52) { //X button
            getModel().setButtonB(true);
        }

        if(keycode == 54) { //Z button
            getModel().setButtonX(true);
        }

        if(keycode == 131) { //ESCAPE
            getModel().setMenuButton(true);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == 51 || keycode == 19) { //Up
            upDown = false;
            if(downDown) {
                getModel().getLeftStick().z = 1;
            }else {
                getModel().getLeftStick().z = 0;
            }
        }
        if(keycode == 29 || keycode == 21) { //Left
            leftDown = false;
            if(rightDown) {
                getModel().getLeftStick().x = 1;
            }else {
                getModel().getLeftStick().x = 0;
            }
        }

        if(keycode == 47 || keycode == 20) { //Down
            downDown = false;
            if(upDown) {
                getModel().getLeftStick().z = -1;
            }else {
                getModel().getLeftStick().z = 0;
            }
        }

        if(keycode == 32 || keycode == 22) { //Right
            rightDown = false;
            if(leftDown) {
                getModel().getLeftStick().x = -1;
            }else {
                getModel().getLeftStick().x = 0;
            }
        }

        if(keycode == 31) { //A button
            getModel().setButtonA(false);
        }

        if(keycode == 52) { //B button
            getModel().setButtonB(false);
        }

        if(keycode == 54) { //X button
            getModel().setButtonX(false);
        }

        if(keycode == 131) { //ESCAPE
            getModel().setMenuButton(false);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
