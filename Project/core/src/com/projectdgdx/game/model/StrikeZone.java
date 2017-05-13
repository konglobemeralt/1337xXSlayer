package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * The StrikeZone is where the Workers go when they've been caught by any Supervisor.
 * When a certain amount of Workers Have entered the StrikeZone the Supervisors have lost the game.
 */
public class StrikeZone extends GameObject{

    public StrikeZone(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }
}
