package com.projectdgdx.game.gameobjects;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-04-03.
 */
public abstract class StaticObject extends GameObject {
    public StaticObject(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }
}
