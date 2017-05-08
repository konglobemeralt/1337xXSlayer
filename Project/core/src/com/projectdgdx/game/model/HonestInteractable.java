package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-04-25.
 */
public interface HonestInteractable {
    public abstract void honestInteract(PlayableCharacter player);
    public abstract Vector3d getPosition();
    public void updateTimer();
}

