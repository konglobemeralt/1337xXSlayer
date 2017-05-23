package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;

/**
 * The EndGameCounter is a data handler that handles all data surrounding the end of the game.
 * This includes number of destroyed Machines, striking worker and the overall timer.
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

    /**
     * Increments the number of destroyed Machines and sends an event to the EventSender if enough
     * Machines have been destroyed to end the game.
     */
    public void incDestroyedMachines(){
        System.out.println("Num destroyed machines was:" + numDestroyedMachines + " now " + (numDestroyedMachines+1));
        this.numDestroyedMachines++;

        if (this.numDestroyedMachines >= targetNumDestroyedMachines){
            EventSender.getEventSender().sendMachinesDestroyedEnd();
        }
    }

    /**
     * Increments the number of striking Workers and sends an event to the EventSender if enough
     * Workers strike to end the game.
     */
    public void incStrikingWorkers(){
        System.out.println("Num strikers was:" + numStrikingWorkers + " now " + (numStrikingWorkers+1));
        this.numStrikingWorkers++;
        if (this.numStrikingWorkers >= targetNumStrikingWorkers){
            EventSender.getEventSender().sendStrikeEnd();
        }
    }
}
