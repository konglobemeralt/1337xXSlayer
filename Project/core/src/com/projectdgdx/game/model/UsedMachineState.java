package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class UsedMachineState implements iMachineState { //Maybe not needed. If machine can have many characters interacting at same time

    @Override
    public void honestInteract(PlayableCharacter player, iHonestInteractable hi) {
        //TODO Not sure what to do here? Should characters push current user away or be blocked?
        
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, iDishonestInteractable di) {
        //TODO Not sure what to do here? Should characters push current user away or be blocked?

    }
}
