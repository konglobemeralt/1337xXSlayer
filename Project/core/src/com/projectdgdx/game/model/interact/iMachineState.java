package com.projectdgdx.game.model.interact;

/**
 * Machines can have internal states to determine if they're workable or if they're destroyed.
 * All these states need to implement this interface.
 */
public interface iMachineState {
    /**
     * What happens when a player honest interacts with the machine.
     * @param player , the player interacting with the machine.
     * @param hi , the iHonestInteractable that is interacted with.
     */
    void honestInteract(PlayableCharacter player, iHonestInteractable hi);
    /**
     * What happens when a player dishonest interacts with the machine.
     * @param player , the player interacting with the machine.
     * @param di , the iDishonestInteractable that is interacted with.
     */
    void dishonestInteract(PlayableCharacter player, iDishonestInteractable di);

    /**
     * What happens when the internal timer of a Machine has ticked down to 0.
     * @param machine , the Machine with the internal timer that has ended.
     */
    void destroyedByTime(Machine machine);
}
