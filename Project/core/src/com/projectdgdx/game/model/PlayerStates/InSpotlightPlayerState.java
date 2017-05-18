package com.projectdgdx.game.model.PlayerStates;

import com.projectdgdx.game.model.Spotlight;
import com.projectdgdx.game.utils.Vector3d;

/**
 * This player state is for when any player interacts with the spotlight.
 */
public class InSpotlightPlayerState implements iPlayerState {
    private Spotlight spotlight;

    public InSpotlightPlayerState(Spotlight spotlight){
        this.spotlight = spotlight;
    }

    /**
     * Instead of using the usual move method that moves the player controlled character the
     * player will now move the Spotlight instead.
     * @param vector , the input from the controller. The vector is relative to the Spotlights position.
     */
    @Override
    public void move(Vector3d vector) {
        this.spotlight.move(vector);
    }
}
