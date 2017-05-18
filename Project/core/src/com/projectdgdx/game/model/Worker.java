package com.projectdgdx.game.model;

import com.projectdgdx.game.model.AI.BasicNode;
import com.projectdgdx.game.model.AI.NormalWorkerState;
import com.projectdgdx.game.model.AI.StrikingWorkerState;
import com.projectdgdx.game.model.AI.iWorkerState;
import com.projectdgdx.game.model.ModelStructure.Character;
import com.projectdgdx.game.utils.Vector3d;

/**
 * A worker is a Character in the game that looks is controlled by the AI unit and walks around
 * and simulate interaction with the Machines. Looks like the Supervisors and the Saboteur.
 * Walks between a network of nodes.
 */
public class Worker extends Character {

    private BasicNode targetNode;
    private BasicNode lastNode;
    private iWorkerState state;

    public Worker(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
        this.state = new NormalWorkerState();
    }

    public BasicNode getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(BasicNode targetNode) {
        this.targetNode = targetNode;
    }

    public BasicNode getLastNode() {
        return lastNode;
    }

    public void setLastNode(BasicNode lastNode) {
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
        //System.out.println(this.getPosition().toString()); //TODO Debug
    }

    @Override
    public void beenCaught() {
        this.state = new StrikingWorkerState();
    }


    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }
}
