package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.EventSender;
import com.projectdgdx.game.model.Worker;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Vector3d;

/**
 * When a Worker is caught by the Supervisor it will enter the StrikingWorkerState. It will send
 * an event to the EventSender.
 */
public class StrikingWorkerState implements iWorkerState {

    public StrikingWorkerState(){
        EventSender.getEventSender().sendNewStrikingWorker();
    }

    @Override
    public void reactOnUpdate(Worker worker) {
        if (worker.getPosition().y<500){
            worker.move(new Vector3d(0,1,0));
        }else{
            worker.move(new Vector3d(0,0,0));
        }
    }

    @Override
    public void beenCaught(Worker worker) {

    }

//    @Override
//    public void reactOnUpdate(Worker worker) {
//        Timer timer = new Timer(5,1000);
//        timer.start();
//        if (timer.getTimerValue()>0){
//            actAngry(worker);
//        }else{
//            if(!isInStrikeZone(worker)){
//                walkToStrikeZone(worker);
//            }else{
//                actAngry(worker);
//            }
//        }
//    }
//
//    private void walkToStrikeZone(Worker worker){ //TODO Moves the worker towards the strike zone (Using A* ?)
//        worker.move(new Vector3d(0,0,0));
//    }
//
//    private void actAngry(Worker worker){ //TODO Animations, maybe walking around LOW PRIORITY
//        worker.move(new Vector3d(0,0,0));
//    }
//
//    private boolean isInStrikeZone(Worker worker){
//       return false; //TODO
//               //worker.getPosition().isInRadius(ModelDataHandler.getStrikeZone().getPosition(), Config.STRIKE_ZONE_RADIUS);
//    }
//
//    @Override
//    public void beenCaught(Worker worker) {
//        //Do nothing, is already caught
//    }
}
