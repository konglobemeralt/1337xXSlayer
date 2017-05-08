package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class DestroyedMachineState implements MachineState, SpotlightListener{

    boolean detected = false;
    private Vector3d machinePosition;

    public DestroyedMachineState(Vector3d machinePosition){
        this.machinePosition = machinePosition;
    }

    @Override
    public void honestInteract(PlayableCharacter player, HonestInteractable hi) {
        //TODO play a sound? Might not be useful
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, DishonestInteractable di) {
        //TODO play a sound? Might not be useful
    }

    @Override
    public boolean isDetected(Vector3d pos) {
        return true;
    }

    @Override
    public void detect() {
        // Play animation
    }
}
