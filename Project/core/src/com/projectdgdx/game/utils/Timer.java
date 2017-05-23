package com.projectdgdx.game.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class Timer implements Runnable{
    private ArrayList<iTimerListener> listeners = new ArrayList<iTimerListener>();
    private int timerValue;
    private long ticTime;
    private Thread thread;

    private static boolean isPaused = false;

    private static List<Timer> timers = new ArrayList<>();

    private static void addTimer(Timer timer) {
        timers.add(timer);
        System.out.println(timers.size());
    }

    private static void removeTimer(Timer timer) {
        timers.remove(timer);
        isPaused = false;
    }

    public static void pauseTimers() {
        isPaused = true;
        for(Timer timer : timers) {
            timer.pause();
        }
    }

    public static void resumeTimers() {
        for(Timer timer : timers) {
            timer.resume();
        }
        isPaused = false;
    }

    public static void removeTimers() {
        for(Timer timer : timers) {
            timer.stop();
        }
        timers.clear();
    }

    public synchronized void pause() {
        thread.interrupt();
    }

    public synchronized void resume() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        thread.interrupt();
    }

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
        if(!isPaused) {
            thread = new Thread(this);
            thread.start();
        }
        addTimer(this);
    }


    @Override
    public void run() {
        while (this.timerValue > 0){
                try {
//                    System.out.println("Tick: " + this.timerValue);
                    Thread.sleep(this.ticTime);
                }catch(InterruptedException e){
//                    System.out.println("Thread has been interrupted");
                    return;
                }
                this.timerValue--;
        }

            if(!Thread.interrupted()) {
                while(isPaused) {
                    System.out.println("I'm a stupid thread");
                }
                notifyListenersTimeUp();
                removeTimer(this);
//            System.out.println(listeners.get(0).toString());
            }


    }
}
