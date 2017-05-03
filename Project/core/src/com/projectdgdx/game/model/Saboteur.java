package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Saboteur extends PlayableCharacter{
    public Saboteur(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void dishonestInteract() {

    }

    @Override
    public void useAbility() {

    }

    @Override
    public boolean isColliding(Vector3 vec) {
        return false;
    }
}
