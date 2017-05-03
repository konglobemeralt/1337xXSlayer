package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hampus on 2017-04-03.
 */
public class Machine extends StaticObject implements HonestInteractable {
    public Machine(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void honestInteract(PlayableCharacter player){

    }

}
