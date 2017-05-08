package com.projectdgdx.game.utils;

/**
 * Created by konglobemeralt on 2017-04-27.
 */

public class Vector3d {

    public float x, y, z;

    public Vector3d(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d add(Vector3d vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }

    public float getLength() {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

<<<<<<< HEAD
=======
    public float distanceTo(Vector3d compVector){
        Vector3d distanceVector = new Vector3d(compVector.x-x, compVector.y-y, compVector.z-z);
        return distanceVector.getLength();
    }

>>>>>>> e36d0f6f23f4fc51cd95d70a46c2f917f6dd6d2c
}