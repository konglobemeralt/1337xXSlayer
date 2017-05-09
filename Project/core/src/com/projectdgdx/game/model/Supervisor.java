package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Supervisor extends PlayableCharacter {
    public Supervisor(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void dishonestInteract(List<DishonestInteractable> dishonestInteractables) {
        // Should be empty since a supervisor can not sabotage the machines.
    }

    @Override
    public void useAbility() {

    }

    @Override
    public void beenCaught() {
        this.setState(new CapturedPlayerState(this));
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }
}
