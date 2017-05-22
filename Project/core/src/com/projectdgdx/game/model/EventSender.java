package com.projectdgdx.game.model;

import java.util.ArrayList;
import java.util.List;

public class EventSender {

    private List<iEventListener> listeners = new ArrayList<>();

    private static EventSender eventSender = new EventSender();

    private EventSender(){    }

    public static EventSender getEventSender(){
        return eventSender;
    }

    public void sendStrikeEnd(){
        for (iEventListener listener : listeners){
            listener.reactToEvent(Events.STRIKE_END);
        }
    }

    public void sendMachinesDestroyedEnd(){
        for (iEventListener listener : listeners){
            listener.reactToEvent(Events.MACHINES_DESTROYED_END);
        }
    }

    public void sendNewStrikingWorker(){
        for (iEventListener listener : listeners){
            listener.reactToEvent(Events.SRIKING_WORKER);
        }
    }

    public void sendNewDestroyedMachine(){
            for (iEventListener listener : listeners){
                listener.reactToEvent(Events.MACHINE_DESTRUCTION);
            }
    }

    public void sendTimeOutEnd(){
        for (iEventListener endgameListener : listeners) {
            endgameListener.reactToEvent(Events.TIME_UPP);
        }
    }

    public void sendSaboteurCaughtEnd(){
        for (iEventListener endgameListener : listeners) {
            endgameListener.reactToEvent(Events.SABOTEUR_CAUGHT);
        }
    }

    public void addListener(iEventListener listener){
        this.listeners.add(listener);
    }


    public List<iEventListener> getListeners() {
        return listeners;
    }



}
