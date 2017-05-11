package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Jesper on 2017-04-24.
 */
public class SpotlightControlBoard extends StaticObject implements HonestInteractable{
    private Spotlight spotlight;

    public SpotlightControlBoard(Vector3d position, Vector3d scale, Vector3d rotation, String id, Spotlight spotlight) {
        super(position, scale, rotation, id);
        this.spotlight = spotlight;
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setState(new InSpotlightPlayerState(this.spotlight));
    }

    public Spotlight getSpotlight() {
        return spotlight;
    }

}
