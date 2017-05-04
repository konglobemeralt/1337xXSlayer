package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class MachineInteractingPlayerState implements CharacterState {
    private HonestInteractable machine;

    MachineInteractingPlayerState(HonestInteractable machine){
        this.machine = machine;
    }
    @Override
    public void move(Vector3d vector) {

    }
}
