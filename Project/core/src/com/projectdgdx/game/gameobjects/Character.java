package com.projectdgdx.game.gameobjects;


import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-04-03.
 */
public abstract class Character extends Entity {
    public Character(Vector3 position, Vector3 scale, Vector3 size, Vector3 rotation, String id) {
        super(position, size, scale, rotation, id);
    }
}
