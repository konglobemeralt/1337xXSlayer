package com.projectdgdx.game.model.gameplay;

import com.projectdgdx.game.utils.Vector3d;

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
}
