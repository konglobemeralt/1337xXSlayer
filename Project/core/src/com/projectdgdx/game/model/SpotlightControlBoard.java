package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Jesper on 2017-04-24.
 */
public class SpotlightControlBoard extends StaticObject implements HonestInteractable{

    public SpotlightControlBoard(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setState(new InSpotlightState());
    }
}
