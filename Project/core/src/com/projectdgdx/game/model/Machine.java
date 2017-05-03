package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Hampus on 2017-04-03.
 */

public class Machine extends StaticObject implements HonestInteractable {


    protected MachineState state;

    public Machine(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
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
