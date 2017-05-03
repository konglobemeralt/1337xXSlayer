package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jesper on 2017-04-24.
 */
public class SpotlightControlBoard extends StaticObject implements HonestInteractable{

    public SpotlightControlBoard(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setState(new InSpotlightState());
    }
}
