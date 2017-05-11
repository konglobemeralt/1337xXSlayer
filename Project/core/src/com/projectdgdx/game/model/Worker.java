package com.projectdgdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.model.AI.AINode;
import com.projectdgdx.game.model.AI.NormalWorkerState;
import com.projectdgdx.game.model.AI.StrikingWorkerState;
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
        this.state = new NormalWorkerState();
    }

    public AINode getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(AINode targetNode) {
        this.targetNode = targetNode;
    }

    public AINode getLastNode() {
        return lastNode;
    }

    public void setLastNode(AINode lastNode) {
        this.lastNode = lastNode;
    }

    public WorkerState getState() {
        return state;
    }

    public void setState(WorkerState state) {
        this.state = state;
    }

    public void reactOnUpdate(){
        this.state.reactOnUpdate(this);
    }

    @Override
    public void beenCaught() {
        this.state = new StrikingWorkerState();
    }

    @Override
    public void move(Vector3d vector3d){
        super.move(vector3d.scale(Config.MOVE_SPEED).scale(Gdx.graphics.getDeltaTime())); //TODO remove libGDX dependency
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }
}
