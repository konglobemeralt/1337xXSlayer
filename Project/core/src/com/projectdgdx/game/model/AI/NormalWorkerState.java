package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.GlobalVariables;
import com.projectdgdx.game.model.Worker;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-09.
 */
public class NormalWorkerState implements WorkerState {

    @Override
    public void reactOnUpdate(Worker worker) { //TODO
        if (worker.getPosition().isInRadius(worker.getTargetNode().getPosition(), GlobalVariables.workerNodeRadius)){
            worker.setLastNode(worker.getTargetNode());
            worker.setTargetNode(worker.getTargetNode().getNextNode());
        }

        Vector3d vectorToTarget = worker.getPosition().vectorTo(worker.getTargetNode().getPosition());
        worker.move(vectorToTarget.normalised());
    }
}
