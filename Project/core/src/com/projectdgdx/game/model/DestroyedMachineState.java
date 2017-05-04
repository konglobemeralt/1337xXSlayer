package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class DestroyedMachineState implements MachineState{

    @Override
    public void honestInteract(PlayableCharacter player, HonestInteractable hi) {
        //TODO play a sound? Might not be useful
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, DishonestInteractable di) {
        //TODO play a sound? Might not be useful
    }
}
