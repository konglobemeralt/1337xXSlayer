package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class UnusedMachineState implements MachineState {

    @Override
    public void honestInteract(PlayableCharacter player, HonestInteractable hi) {
        player.setState(new MachineInteractingPlayerState(hi, player));
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, DishonestInteractable di) {
        player.setState(new MachineDestroyingPlayerState(di, player));

    }
}
