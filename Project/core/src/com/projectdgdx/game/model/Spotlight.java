package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Spotlight extends Entity {
    private List<SpotlightListener> listeners = new ArrayList();
    private int spotlightRadius;

    public Spotlight(Vector3d position, Vector3d scale, Vector3d rotation, String id, int spotlightRadius) {
        super(position, scale, rotation, id);
        this.spotlightRadius = spotlightRadius;
    }

    @Override
    public  void move(Vector3d v){
        if(listeners.size() == 0){
            super.move(v);
        } else{
            checkListenerDetection(v);
            super.move(v);
        }
    }

    private void checkListenerDetection(Vector3d v){
        for(SpotlightListener sl : listeners){
            if(sl.isDetected(v, this.spotlightRadius)){
                sl.detect();
            }
        }
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }

    void addListener(SpotlightListener slListener){
        listeners.add(slListener);
    }
}
