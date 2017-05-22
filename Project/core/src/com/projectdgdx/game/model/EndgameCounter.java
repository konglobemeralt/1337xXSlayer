package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-22.
 */
public class EndgameCounter {

    private int targetNumDestroyedMachines;
    private int targetNumStrikingWorkers;
    private int numDestroyedMachines = 0;
    private int numStrikingWorkers = 0;

    public EndgameCounter(int targetNumDestroyedMachines, int targetNumStrikingWorkers){
        this.targetNumDestroyedMachines = targetNumDestroyedMachines;
        this.targetNumStrikingWorkers = targetNumStrikingWorkers;
    }

    public int getNumStrikingWorkers() {
        return numStrikingWorkers;
    }

    public int getNumDestroyedMachines() {

        return numDestroyedMachines;
    }

    public void incDestroyedMachines(){
        System.out.println("Num destroyed machines was:" + numDestroyedMachines + " now " + (numDestroyedMachines+1));
        this.numDestroyedMachines++;

        if (this.numDestroyedMachines >= targetNumDestroyedMachines){
            EventSender.getEventSender().sendMachinesDestroyedEnd();
        }
    }

    public void incStrikingWorkers(){
        this.numStrikingWorkers++;
        if (this.numStrikingWorkers >= targetNumStrikingWorkers){
            EventSender.getEventSender().sendStrikeEnd();
        }
    }
}
