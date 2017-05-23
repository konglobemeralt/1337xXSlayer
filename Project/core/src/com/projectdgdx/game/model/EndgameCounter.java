package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;

/**
 * Created by Emil Jansson on 2017-05-22.
 */
public class EndgameCounter {

    private int targetNumDestroyedMachines;
    private int targetNumStrikingWorkers;
    private int numDestroyedMachines = 0;
    private int numStrikingWorkers = 0;
    private Timer endgameTimer;

    public void setEndgameTimer(Timer endgameTimer) {
        this.endgameTimer = endgameTimer;
    }

    public Timer getEndgameTimer() {
        return endgameTimer;
    }


    public EndgameCounter(int targetNumDestroyedMachines, int targetNumStrikingWorkers, Timer endgameTimer){
        this.targetNumDestroyedMachines = targetNumDestroyedMachines;
        this.targetNumStrikingWorkers = targetNumStrikingWorkers;
        this.endgameTimer = endgameTimer;

    }

    public int getNumStrikingWorkers() {
        return numStrikingWorkers;
    }

    public int getNumDestroyedMachines() {

        return numDestroyedMachines;
    }

    public synchronized void incDestroyedMachines(){
        System.out.println("Num destroyed machines was:" + numDestroyedMachines + " now " + (numDestroyedMachines+1));
        this.numDestroyedMachines++;

        if (this.numDestroyedMachines >= targetNumDestroyedMachines){
            EventSender.getEventSender().sendMachinesDestroyedEnd();
        }
    }

    public synchronized void incStrikingWorkers(){
        System.out.println("Num strikers was:" + numStrikingWorkers + " now " + (numStrikingWorkers+1));
        this.numStrikingWorkers++;
        if (this.numStrikingWorkers >= targetNumStrikingWorkers){
            EventSender.getEventSender().sendStrikeEnd();
        }
    }
}
