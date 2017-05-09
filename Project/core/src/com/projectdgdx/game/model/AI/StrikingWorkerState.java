package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.GlobalVariables;
import com.projectdgdx.game.model.ModelDataHandler;
import com.projectdgdx.game.model.Worker;
import com.projectdgdx.game.utils.Timer;

/**
 * Created by Emil Jansson on 2017-05-09.
 */
public class StrikingWorkerState implements  WorkerState {

    @Override
    public void reactOnUpdate(Worker worker) {
        Timer timer = new Timer(5,1000);
        if (timer.getTimerValue()>0){
            actAngry();
        }else{
            if(!isInStrikeZone(worker)){
                walkToStrikeZone();
            }else{
                actAngry();
            }
        }
    }

    private void walkToStrikeZone(){ //TODO Moves the worker towards the strike zone (Using A* ?)

    }

    private void actAngry(){ //TODO Animations, maybe walking around LOW PRIORITY

    }

    private boolean isInStrikeZone(Worker worker){
       return worker.getPosition().isInRadius(ModelDataHandler.getStrikeZone().getPosition(), GlobalVariables.strikeZoneRadius);
    }
}
