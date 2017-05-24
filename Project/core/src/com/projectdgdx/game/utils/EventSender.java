package com.projectdgdx.game.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The EventSender is a layer between the Model and its listeners used for communicating when something happen
 * in the Model to other parts of the program.
 */
public class EventSender {

    private List<iEventListener> listeners = new ArrayList<>();

    private static EventSender eventSender = new EventSender();

    private EventSender(){    }

    public static EventSender getEventSender(){
        return eventSender;
    }

    /**
     * Sends the event to the listeners that enough Workers are on strike and its time to end the game.
     */
    public void sendStrikeEnd(){
        for (iEventListener listener : listeners){
            listener.reactToEvent(Events.STRIKE_END);
        }
    }

    /**
     * Sends the event to the listeners that enough Machines have been destroyed and its time to end the game.
     */
    public void sendMachinesDestroyedEnd(){
        for (iEventListener listener : listeners){
            listener.reactToEvent(Events.MACHINES_DESTROYED_END);
        }
    }

    /**
     * Sends the event to the listeners that a Worker is on strike.
     */
    public void sendNewStrikingWorker(){
        for (iEventListener listener : listeners){
            listener.reactToEvent(Events.SRIKING_WORKER);
        }
    }

    /**
     * Sends the event to the listeners that a Machine is destroyed.
     */
    public void sendNewDestroyedMachine(){
            for (iEventListener listener : listeners){
                listener.reactToEvent(Events.MACHINE_DESTRUCTION);
            }
    }

    /**
     * Sends the event to the listeners that the game timer is out of time.
     */
    public void sendTimeOutEnd(){
        for (iEventListener endgameListener : listeners) {
            endgameListener.reactToEvent(Events.TIME_UPP);
        }
    }

    /**
     * Sends the event to the listeners that the Saboteur has been caught.
     */
    public void sendSaboteurCaughtEnd(){
        for (iEventListener endgameListener : listeners) {
            endgameListener.reactToEvent(Events.SABOTEUR_CAUGHT);
        }
    }

    /**
     * Add a listener to the EventSender.
     */
    public void addListener(iEventListener listener){
        this.listeners.add(listener);
    }

    public List<iEventListener> getListeners() {
        return listeners;
    }



}
