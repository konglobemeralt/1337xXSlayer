package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * The Entity class is for all GameObjects that can move. Eg the Spotlight and all characters.
 */
public abstract class Entity extends GameObject {
    public Entity(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
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
            this.addPositionX(vec.x);
            this.addPositionZ(vec.z);
        }

    }
}
