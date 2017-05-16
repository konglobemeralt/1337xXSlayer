package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.EndgameHandler;
import com.projectdgdx.game.model.Worker;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-09.
 */
public class StrikingWorkerState implements iWorkerState {

    public StrikingWorkerState(){
        EndgameHandler.getEndgameHandler().incStrikers();
    }

    @Override
    public void reactOnUpdate(Worker worker) {
        Timer timer = new Timer(5,1000);
        timer.start();
        if (timer.getTimerValue()>0){
            actAngry(worker);
        }else{
            if(!isInStrikeZone(worker)){
                walkToStrikeZone(worker);
            }else{
                actAngry(worker);
            }
        }
    }

    private void walkToStrikeZone(Worker worker){ //TODO Moves the worker towards the strike zone (Using A* ?)
        worker.move(new Vector3d(0,0,0));
    }

    private void actAngry(Worker worker){ //TODO Animations, maybe walking around LOW PRIORITY
        worker.move(new Vector3d(0,0,0));
    }

    private boolean isInStrikeZone(Worker worker){
       return false; //TODO
               //worker.getPosition().isInRadius(ModelDataHandler.getStrikeZone().getPosition(), Config.STRIKE_ZONE_RADIUS);
    }
}
