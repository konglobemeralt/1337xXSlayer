package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class DestroyedMachineState implements iMachineState, iSpotlightListener {

    private boolean detected = false;
    private Vector3d machinePosition;

    public DestroyedMachineState(Vector3d machinePosition){
        this.machinePosition = machinePosition;
    }

    @Override
    public void honestInteract(PlayableCharacter player, iHonestInteractable hi) {
        // Play a sound? Might not be useful
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, iDishonestInteractable di) {
        // Play a sound? Might not be useful
    }

    @Override
    public boolean isDetected(Vector3d spotlightPos, int radius) {
        return this.machinePosition.isInRadius(spotlightPos, radius);
    }

    @Override
    public void detect() {
        if(!this.detected) {
            this.detected = true;
            // Play animation of machine getting destroyed

            // TODO reason if this should be removed as a listener and how?
        }
    }
}
