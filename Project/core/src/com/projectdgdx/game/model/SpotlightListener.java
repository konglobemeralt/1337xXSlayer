package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-05-08.
 */
public interface SpotlightListener {
    boolean isDetected(Vector3d pos);
    void detect();
}
