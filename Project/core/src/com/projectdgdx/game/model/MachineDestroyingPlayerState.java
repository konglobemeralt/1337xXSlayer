package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class MachineDestroyingPlayerState implements CharacterState {
    DishonestInteractable machine;
    MachineDestroyingPlayerState(DishonestInteractable machine){
        this.machine = machine;
    }
    @Override
    public void move(Vector3d vector) {
        
    }
}
