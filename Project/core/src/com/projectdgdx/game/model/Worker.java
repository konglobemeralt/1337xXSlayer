package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-04-03.
 */
public class Worker extends Character {
    public Worker(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void setStartingState() {
    }

    @Override
    public boolean isColliding(Vector3 vec) {
        return false;
    }
}
