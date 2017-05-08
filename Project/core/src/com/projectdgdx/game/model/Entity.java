package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Hampus on 2017-04-03.
 */
public abstract class Entity extends GameObject {
    public Entity(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    /**
     * Returns true if moving does not cause a collision.
     * @return True if moving does not cause a collision.
     */

    protected abstract boolean isColliding(Vector3d v);

    /**
     * Changes the entity's position by translation by vector v.
     * @param v The vector used for translation.
     */

    public void move(Vector3d v){
        if (isColliding(v)){
            this.addPositionX(v.x);
            this.addPositionY(v.y);
        }

    }
}
