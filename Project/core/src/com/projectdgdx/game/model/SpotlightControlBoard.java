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
        this.spotlight.setColor(new Vector3d(0.2f, 0.1f, 0.3f));
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setMoveForce(new Vector3d(0,0,0));
        if (player.state.getClass().equals(new InSpotlightPlayerState(spotlight).getClass())){ //TODO this class check is weird
            player.setState(new NormalPlayerState(player));
            spotlight.setIntensity(500);
        }else{
            player.setState(new InSpotlightPlayerState(this.spotlight));
            spotlight.setIntensity(1500);
        }

    }

    public Spotlight getSpotlight() {
        return spotlight;
    }

}
