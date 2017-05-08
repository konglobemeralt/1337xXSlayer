package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class MachineDestroyingPlayerState implements PlayerState {

    // This is where we write what actually will happen when dishonest interacting

    DishonestInteractable machine;

    MachineDestroyingPlayerState(DishonestInteractable machine){
        this.machine = machine;
    }

    @Override
    public void move(Vector3d vector) {

    }
}
