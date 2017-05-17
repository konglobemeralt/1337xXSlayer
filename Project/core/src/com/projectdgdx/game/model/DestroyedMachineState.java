package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * A machine will enter this state when sabotaged. It will be detectable by the Spotlight and the
 * players can't interact with it anymore.
 */
public class DestroyedMachineState implements iMachineState, iSpotlightListener {

    private boolean detected = false;
    private Vector3d machinePosition;

    public DestroyedMachineState(Vector3d machinePosition){
        this.machinePosition = machinePosition;
        EndgameHandler.getEndgameHandler().incDestroyedMachines();
        // TODO Add as SpotlightListener
    }

    @Override
    public void honestInteract(PlayableCharacter player, iHonestInteractable hi) {
        // TODO Update machine to display new model
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, iDishonestInteractable di) {
        // Play a sound? Might not be useful
    }

    @Override
    public boolean isDetected(Vector3d spotlightPos, int radius) {
        return this.machinePosition.isInRadius(new Vector3d(spotlightPos.x, 0, spotlightPos.z), radius);
    }

    @Override
    public void detect() {
        if(!this.detected) {
            this.detected = true;
            System.out.println("SABOTAGE DETECTED");
            // Play animation of machine getting destroyed/change spotlight color from green to red and play a sound

            // TODO Update machine to display new model

            // TODO reason if this should be removed as a listener and how?
        }
    }
}
