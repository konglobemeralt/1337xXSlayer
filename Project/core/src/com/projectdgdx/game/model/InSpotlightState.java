package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-05-03.
 */
public class InSpotlightState implements PlayerState {
    private Spotlight spotlight;

    public InSpotlightState(Spotlight spotlight){
        this.spotlight = spotlight;
    }

    @Override
    public void move(Vector3d vector) {
        this.spotlight.move(vector);
    }
}
