package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Spotlight extends Entity {
    private List<iSpotlightListener> listeners = new ArrayList();
    private int spotlightRadius;

    public Spotlight(Vector3d position, Vector3d scale, Vector3d rotation, String id, int spotlightRadius) {
        super(position, scale, rotation, id);
        this.spotlightRadius = spotlightRadius;
    }

    @Override
    public void move(Vector3d vec){
        if(listeners.size() == 0){
            super.move(vec);
        } else{
            super.move(vec);
            checkListenerDetection(this.getPosition());

        }
    }

    /**
     * This method is used when the spotlight moves to verify if it hovers over a machine that has been destroyed.
     * @param v position of the Spotlight.
     */
    private void checkListenerDetection(Vector3d v){
        for(iSpotlightListener sl : listeners){
            if(sl.isDetected(v, this.spotlightRadius)){
                sl.detect();
            }
        }
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }

    void addListener(iSpotlightListener slListener){
        listeners.add(slListener);
    }
}
