package com.projectdgdx.game.model.interaction;

import com.projectdgdx.game.model.playables.PlayableCharacter;
import com.projectdgdx.game.model.staticInteractable.Spotlight;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.model.machineStates.iMachineState;

import java.util.List;

/**
 * All objects that can be honest interacted with by any playable character should implement this interface.
 */
public interface iHonestInteractable {
    /**
     * This method determines eht happens hen a player honest interacts with a machine.
     * @param player , the current Supervisor or Saboteur that interacts with the machine.
     */
	void honestInteract(PlayableCharacter player);
    Vector3d getPosition();
    void updateTimer();
    void setState(iMachineState newState);
}

