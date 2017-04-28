package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-04-03.
 */
public abstract class Entity extends GameObject {
    public Entity(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    /**
     * Returns true if moving does not cause a collision.
     * @return True if moving does not cause a collision.
     */

    protected abstract boolean isColliding(Vector3 v);

    /**
     * Changes the entity's position by translation by vector v.
     * @param v The vector used for translation.
     */

    public void move(Vector3 v){
        if (isColliding(v)){
            this.addPositionX(v.x);
            this.addPositionY(v.y);
        }

    }
}
