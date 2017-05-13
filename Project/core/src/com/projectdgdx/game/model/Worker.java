package com.projectdgdx.game.model;

import com.badlogic.gdx.Gdx;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.model.AI.AINode;
import com.projectdgdx.game.model.AI.NormalWorkerState;
import com.projectdgdx.game.model.AI.StrikingWorkerState;
import com.projectdgdx.game.model.AI.iWorkerState;
import com.projectdgdx.game.utils.Vector3d;

/**
 * A worker is a Character in the game that looks is controlled by the AI unit and walks around
 * and simulate interaction with the Machines. Looks like the Supervisors and the Saboteur.
 * Walks between a network of nodes.
 */
public class Worker extends Character {

    private AINode targetNode;
    private AINode lastNode;
    private iWorkerState state;

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

    public iWorkerState getState() {
        return state;
    }

    public void setState(iWorkerState state) {
        this.state = state;
    }

    /**
     * 
     */
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
