package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-05-08.
 */
public interface iSpotlightListener {
    boolean isDetected(Vector3d spotlightPos, int radius);
    void detect();
}
