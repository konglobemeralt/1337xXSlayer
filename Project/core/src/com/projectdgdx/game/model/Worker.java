package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.model.AI.AINode;
import com.projectdgdx.game.model.AI.WorkerState;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Hampus on 2017-04-03.
 */
public class Worker extends Character {

    private AINode targetNode;
    private AINode lastNode;
    private WorkerState state;

    public Worker(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    public void reactOnUpdate(){
        this.state.reactOnUpdate(this);
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }
}
