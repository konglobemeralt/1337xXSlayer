package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Saboteur extends PlayableCharacter{
    public Saboteur(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
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
