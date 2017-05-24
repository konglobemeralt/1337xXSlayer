package com.projectdgdx.game.model.objectStructure;

import com.projectdgdx.game.utils.Vector3d;

/**
 * The Entity class is for all GameObjects that can move. Eg. the Spotlight and all characters.
 */
public abstract class Entity extends GameObject {

    private Vector3d moveForce = new Vector3d(0,0,0);

    public Entity(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    public void setMoveForce(Vector3d vec) {
        moveForce = vec;
    }

    public Vector3d getMoveForce() {
        return moveForce;
    }

    /**
     * Returns true if moving does not cause a collision.
     * @param vec Vector3d
     * @return True if moving does not cause a collision.
     */
    protected abstract boolean isColliding(Vector3d vec);

    /**
     * Changes the entity's position by translation by vector vec.
     * @param vec The vector used for translation.
     */
    public void move(Vector3d vec){
        if (!isColliding(vec)){
            this.moveForce = vec.normalised();
        }

    }
}
