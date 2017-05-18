package com.projectdgdx.game.model.PlayerStates;

import com.projectdgdx.game.model.PlayableCharacter;
import com.projectdgdx.game.utils.Vector3d;

/**
 * This is the regular player state when a Supervisor or Saboteur isn't interacting with anything.
 * It just walks around.
 */
public class NormalPlayerState implements iPlayerState {
    private PlayableCharacter character;

    public NormalPlayerState(PlayableCharacter character){
        this.character = character;
    }

    @Override
    public void move(Vector3d vector) {
        this.character.setMoveForce(vector);
    }
}
