package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-04-25.
 */
public interface iDishonestInteractable {
    void dishonestInteract(PlayableCharacter player);
    Vector3d getPosition();
    void setState(iMachineState newState);

}
