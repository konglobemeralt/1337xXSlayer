package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Spotlight extends Entity {
    public Spotlight(Vector3 position, Vector3d scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public boolean isColliding(Vector3 vec) {
        return false;
    }
}
