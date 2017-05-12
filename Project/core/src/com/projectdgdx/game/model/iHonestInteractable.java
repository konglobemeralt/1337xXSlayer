package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * All objects that can be honest interacted with by any playable character should implement this interface.
 */
public interface iHonestInteractable {
    /**
     * This method determines eht happens hen a player honest interacts with a machine.
     * @param player , the current Supervisor or Saboteur that interacts with the machine.
     */
    public abstract void honestInteract(PlayableCharacter player);
    public abstract Vector3d getPosition();
}

