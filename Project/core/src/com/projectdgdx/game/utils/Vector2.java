package com.projectdgdx.game.utils;

/**
 * Created by konglobemeralt on 2017-04-27.
 */

public class Vector2 {

    public float x, z;

    Vector2(float x, float z) {
        this.x = x;
        this.z = z;
    }

    public Vector2 add(Vector2 vector) {
        x += vector.x;
        z += vector.z;
        return this;
    }

}