package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Basic GameObject data structure.
 *
 * Created by Hampus on 2017-03-26.
 */
public abstract class GameObject {
    private Vector3d position;
    private Vector3d scale;
    private Vector3d rotation;
    private String id;

    public GameObject(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.id = id;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public void setScale(Vector3d scale) {
        this.scale = scale;
    }

    public void setRotation(Vector3d rotation) {
        this.rotation = rotation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public Vector3d getScale() {
        return scale;
    }

    public String getId() {
        return id;
    }

    public void addPositionX(float x){
        this.position.x += x;
    }

    public void addPositionZ(float z){
        this.position.z += z;
    }

    public void addPositionY(float y){
        this.position.y += y;
    }

}
