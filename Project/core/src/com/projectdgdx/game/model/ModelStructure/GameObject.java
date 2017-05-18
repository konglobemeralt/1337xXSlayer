package com.projectdgdx.game.model.ModelStructure;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Basic GameObject data structure. The foundation of our model hierarchy. All model object
 * in the game inherit from GameObject.
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

    /**
     * The id is ued to determine which graphical 3D object that should be connected to the object in the view.
     * @return the id corresponding to a graphical model in the view
     */
    public String getId() {
        return id;
    }

    /**
     * Increase or decrease the x value of the objects position.
     * @param x ,the float value that will be added to the current position.
     */
    public void addPositionX(float x){
        this.position.x += x;
    }

    /**
     * Increase or decrease the z value of the objects position.
     * @param z ,the float value that will be added to the current position.
     */
    public void addPositionZ(float z){
        this.position.z += z;
    }

    /**
     * Increase or decrease the y value of the objects position.
     * @param y ,the float value that will be added to the current position.
     */
    public void addPositionY(float y){
        this.position.y += y;
    }

}
