package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public interface iMachineState {
    void honestInteract(PlayableCharacter player, iHonestInteractable hi);
    void dishonestInteract(PlayableCharacter player, iDishonestInteractable di);
}
