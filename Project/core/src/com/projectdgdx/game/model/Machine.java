package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-04-03.
 */

public class Machine extends StaticObject implements HonestInteractable {


    protected MachineState state;

    public Machine(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
        this.state = new UnusedMachine();
    }
//w
    @Override
    public void honestInteract(PlayableCharacter player) { //TODO  test commit
        state.honestInteract(player);
    }


    public void dishonestInteract(PlayableCharacter player) {
        state.dishonestInteract(player);
    }

}
