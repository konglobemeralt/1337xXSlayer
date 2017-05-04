package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-04-25.
 */
public interface DishonestInteractable {
    void dishonestInteract(PlayableCharacter player);
    Vector3d getPosition();
}
