package com.projectdgdx.game.gameobjects;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Eddie on 2017-04-03.
 */
public abstract class PlayableCharacter extends Character{
    public PlayableCharacter(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    public abstract void dishonestInteract();

    public abstract void useAbility();
}
