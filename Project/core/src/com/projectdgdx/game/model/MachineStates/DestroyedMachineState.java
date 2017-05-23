package com.projectdgdx.game.model.MachineStates;

import com.projectdgdx.game.model.EventSender;
import com.projectdgdx.game.model.Playables.PlayableCharacter;
import com.projectdgdx.game.model.StaticInteractable.Machine;
import com.projectdgdx.game.model.StaticInteractable.iSpotlightListener;
import com.projectdgdx.game.model.iDishonestInteractable;
import com.projectdgdx.game.model.iHonestInteractable;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;

/**
 * A machine will enter this state when sabotaged. It will be detectable by the Spotlight and the
 * players can't interact with it anymore.
 */
public class DestroyedMachineState implements iMachineState, iSpotlightListener {

    private boolean detected = false;
    private Machine machine;

    public DestroyedMachineState(Machine machine){
        System.out.println("New state created");
        this.machine = machine;
        EventSender.getEventSender().sendNewDestroyedMachine();
    }

    @Override
    public void honestInteract(PlayableCharacter player, iHonestInteractable hi) {
        this.machine.getMachineCounter().setTimerValue(0);
        // TODO Update machine to display new model
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, iDishonestInteractable di) {
        // Play a sound? Might not be useful
    }

    @Override
    public void destroyedByTime(Machine machine) {
        //Nothing happens
    }

    @Override
    public boolean isDetected(Vector3d spotlightPos, int radius) {
        return this.machine.getPosition().isInRadius(new Vector3d(spotlightPos.x, 0, spotlightPos.z), radius);
    }

    @Override
    public void detect() {
        if(!this.detected) {
            this.detected = true;
            if(Config.DEBUG){
                System.out.println("SABOTAGE DETECTED");
            }
            this.machine.getMachineCounter().setTimerValue(0);
            // Play animation of machine getting destroyed/change spotlight color from green to red and play a sound

            // TODO Update machine to display new model

            // TODO reason if this should be removed as a listener and how?
        }
    }
}
