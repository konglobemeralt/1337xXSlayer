package com.projectdgdx.game.gameobjects;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-03-26.
 */
public abstract class GameObject {
    private Vector3 position;
    private Vector3 scale;
    private Vector3 rotation;
    private String id;

    public GameObject(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public String getId() {
        return id;
    }
}
