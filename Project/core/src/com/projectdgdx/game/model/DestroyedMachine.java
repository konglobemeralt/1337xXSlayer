package com.projectdgdx.game.model;

/**
 * Created by Emil Jansson on 2017-05-03.
 */
public class DestroyedMachine implements MachineState{

    @Override
    public void honestInteract(PlayableCharacter player) {
        //TODO play a sound? Might not be useful
    }

    @Override
    public void dishonestInteract(PlayableCharacter player) {
        //TODO play a sound? Might not be useful
    }
}
