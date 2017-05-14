package com.projectdgdx.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Hampus on 2017-05-04.
 *
 * This class handles inputs from a keyboard. It extends InputController and is therefore a valid input for the game.
 */
public class KeyboardController extends InputController implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 51 || keycode == 19) { //Up
            getModel().getLeftStick().z = -1;
        }
        if(keycode == 29 || keycode == 21) { //Left
            getModel().getLeftStick().x = -1;
        }

        if(keycode == 47 || keycode == 20) { //Down
            getModel().getLeftStick().z = 1;
        }

        if(keycode == 32 || keycode == 22) { //Right
            getModel().getLeftStick().x = 1;
        }

        if(keycode == Input.Keys.C) { //A button
            getModel().setButtonA(true);
        }

        if(keycode == Input.Keys.X) { //B button
            getModel().setButtonB(true);
        }

        if(keycode == 131) { //ESCAPE
            getModel().setMenuButton(true);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // A really small value is kept to keep angle
        if(keycode == 51 || keycode == 19) { //Up
            getModel().getLeftStick().z = 0;
        }
        if(keycode == 29 || keycode == 21) { //Left
            getModel().getLeftStick().x = 0;
        }

        if(keycode == 47 || keycode == 20) { //Down
            getModel().getLeftStick().z = 0;
        }

        if(keycode == 32 || keycode == 22) { //Right
            getModel().getLeftStick().x = 0;
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
