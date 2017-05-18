package com.projectdgdx.game.model.MachineStates;

import com.projectdgdx.game.model.Playables.PlayableCharacter;
import com.projectdgdx.game.model.Playables.MachineDestroyingPlayerState;
import com.projectdgdx.game.model.Playables.MachineInteractingPlayerState;
import com.projectdgdx.game.model.iDishonestInteractable;
import com.projectdgdx.game.model.iHonestInteractable;

/**
 * This state is the "normal" state for the machine. This is when nobody uses it and it isn't
 * destroyed/sabotaged. Is in a standby mode waiting for someone to interact with it.
 */
public class UnusedMachineState implements iMachineState {

    @Override
    public void honestInteract(PlayableCharacter player, iHonestInteractable hi) {
        player.setState(new MachineInteractingPlayerState(hi, player));
        System.out.println("Interacting with working machine");
    }

    @Override
    public void dishonestInteract(PlayableCharacter player, iDishonestInteractable di) {
        player.setState(new MachineDestroyingPlayerState(di, player));

    }
}
