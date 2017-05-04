package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class UsedMachineState implements MachineState { //Maybe not needed. If machine can have many characters interacting at same time

    @Override
    public void honestInteract(PlayableCharacter player, HonestInteractable hi) {
        //TODO Not sure what to do here? Schould characters push current user away or be blocked?
        
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, DishonestInteractable di) {
        //TODO Not sure what to do here? Schould characters push current user away or be blocked?

    }
}
