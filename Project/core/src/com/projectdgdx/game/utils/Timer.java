package com.projectdgdx.game.utils;

import java.util.ArrayList;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class Timer implements Runnable{
    private ArrayList<iTimerListener> listeners = new ArrayList<iTimerListener>();
    private int timerValue;
    private long ticTime;

    public Timer(int timerValue, long ticTime){
        this.timerValue = timerValue;
                this.ticTime = ticTime;
    }

    public void addListener(iTimerListener listener){
        listeners.add(listener);
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public void setTicTime(long ticTime) {
        this.ticTime = ticTime;
    }

    public int getTimerValue() {

        return timerValue;
    }

    public long getTicTime() {
        return ticTime;
    }

    private void notifyListenersTimeUp(){
        for (iTimerListener listener : listeners){
            listener.timeIsUp();
        }
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        while (this.timerValue > 0){
            try {
                //wait(this.ticTime);
                Thread.sleep(this.ticTime);
            }catch(Exception e){}
            this.timerValue--;
        }
        notifyListenersTimeUp();
    }
}
