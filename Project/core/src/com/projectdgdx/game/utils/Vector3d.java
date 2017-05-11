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
    
    public float distanceTo(Vector3d compVector){
        Vector3d distanceVector = new Vector3d(compVector.x-x, compVector.y-y, compVector.z-z);
        return distanceVector.getLength();
    }

    public  Vector3d normalised(){
        return new Vector3d(x/getLength(), y/getLength(), z/getLength());
    }

    public Vector3d vectorTo(Vector3d compVector){
        return new Vector3d(compVector.x-x, compVector.y-y, compVector.z-z);
    }

    public boolean isInRadius(Vector3d compVector, float radius){
        return distanceTo(compVector) <= radius;
    }

    public Vector3d scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public String toString() {
        return "[" + x + "," + y + "," + z + "]";
    }

    public float getXZAngle() {
        return (float)Math.toDegrees(Math.atan2(z, x));
    }

    public Vector3d getEuler() {
        return new Vector3d((float)Math.toRadians(x), (float)Math.toRadians(y), (float)Math.toRadians(z));
    }

}