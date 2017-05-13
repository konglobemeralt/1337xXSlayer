package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

/**
 * THe SpotlightControlBoard is the holder of the Spotlight and the object that all
 * PlayableCharacters interact with to enter their InSpotlightPlayerState so that they can move the Spotlight
 * and detect destroyed Machines.
 */
public class SpotlightControlBoard extends StaticObject implements iHonestInteractable {
    private Spotlight spotlight;

    public SpotlightControlBoard(Vector3d position, Vector3d scale, Vector3d rotation, String id, Spotlight spotlight) {
        super(position, scale, rotation, id);
        this.spotlight = spotlight;
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setState(new InSpotlightPlayerState(this.spotlight));
        spotlight.setColor(new Vector3d(0, 1, 0));
    }

    public Spotlight getSpotlight() {
        return spotlight;
    }

}
