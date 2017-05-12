package com.projectdgdx.game.model;


import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * The Characters are all units that move around in the factory and that you can interact with in some way.
 */
public abstract class Character extends Entity {

    public Character(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    /**
     * This method determines what happens when any character has been caught by a Supervisor.
     * When the Saboteur is caught the game ends, when a Worker is caught it strikes and when
     * another Supervisor is caught they "argue".
     */
    public abstract void beenCaught();



}
