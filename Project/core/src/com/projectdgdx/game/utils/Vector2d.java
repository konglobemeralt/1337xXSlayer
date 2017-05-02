package com.projectdgdx.game.utils;

/**
 * Created by konglobemeralt on 2017-04-27.
 */

public class Vector2d {

    public float x, z;

    public Vector2d(float x, float z) {
        this.x = x;
        this.z = z;
    }

    public Vector2d add(Vector2d vector) {
        x += vector.x;
        z += vector.z;
        return this;
    }

}