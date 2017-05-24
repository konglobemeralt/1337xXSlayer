package com.projectdgdx.game.model.gameplay;

import com.projectdgdx.game.utils.Vector3d;

/**
 * This interface is to symbolize the different states that a player can be in. Used to determine what
 * moves when a player is in a certain state and how.
 */
public interface iPlayerState {
    /**
     * Since what moves is dependent in what state the player is in all playables should implement
     * some kind of move method.
     * @param vector , a vector relative to the position of the object supposed to me moved.
     */
    void move(Vector3d vector);
}
