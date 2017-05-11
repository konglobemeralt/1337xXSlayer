package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-05-03.
 */
public class InSpotlightPlayerState implements iPlayerState {
    private Spotlight spotlight;

    public InSpotlightPlayerState(Spotlight spotlight){
        this.spotlight = spotlight;
    }

    @Override
    public void move(Vector3d vector) {
        this.spotlight.move(vector);
    }
}
