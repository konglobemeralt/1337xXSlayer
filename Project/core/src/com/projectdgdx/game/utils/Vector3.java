package com.projectdgdx.game.utils;

/**
 * Created by Hampus on 2017-03-31.
 */
public class Vector3  {

    private double x, y, z;
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
