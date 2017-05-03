package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public interface MachineState {
    void honestInteract(PlayableCharacter player);
    void dishonestInteract(PlayableCharacter player);
}
