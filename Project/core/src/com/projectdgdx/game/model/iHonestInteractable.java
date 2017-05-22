package com.projectdgdx.game.model;

import com.projectdgdx.game.model.Playables.PlayableCharacter;
import com.projectdgdx.game.utils.Vector3d;

/**
 * All objects that can be honest interacted with by any playable character should implement this interface.
 */
public interface iHonestInteractable {
    /**
     * This method determines eht happens hen a player honest interacts with a machine.
     * @param player , the current Supervisor or Saboteur that interacts with the machine.
     */
	void honestInteract(PlayableCharacter player);
    Vector3d getPosition();
}

