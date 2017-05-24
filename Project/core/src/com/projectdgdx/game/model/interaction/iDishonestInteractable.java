package com.projectdgdx.game.model.interaction;

import com.projectdgdx.game.model.machineStates.iMachineState;
import com.projectdgdx.game.model.playables.PlayableCharacter;
import com.projectdgdx.game.model.staticInteractable.Spotlight;
import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * All objects that can be destroyed/sabotaged by the saboteur should implement this interface.
 */
public interface iDishonestInteractable {
    /**
     * Here is what happens when the saboteur destroys the machine.
     * @param player the current saboteur interacting with the machine.
     */
    void dishonestInteract(PlayableCharacter player);
    Vector3d getPosition();
    List<Spotlight> getBigDetectingSpotlights();
    /**
     * This method is used to change the machines internal state when sabotaged.
     * @param newState , the new machine state that should be set when sabotaged.
     */
    void setState(iMachineState newState);

}
