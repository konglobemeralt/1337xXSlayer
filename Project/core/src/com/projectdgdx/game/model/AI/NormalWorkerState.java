package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.GlobalVariables;
import com.projectdgdx.game.model.Worker;

/**
 * Created by Emil Jansson on 2017-05-09.
 */
public class NormalWorkerState implements WorkerState {

    @Override
    public void reactOnUpdate(Worker worker) { //TODO
        if (worker.getPosition().isInRadius(worker.getTargetNode().getPosition(), GlobalVariables.workerNodeRadius));
    }
}
