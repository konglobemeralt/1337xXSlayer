package com.projectdgdx.game.model.decorations;

import com.projectdgdx.game.model.objectStructure.GameObject;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;

/**
 * The blackout class is a "graphical" class that is based on an ability that the Saboteur has. A
 * radius around the saboteur will become black and therefore creates an opportunity for the
 * saboteur to hide and escape.
 */
public class Blackout extends GameObject implements iTimerListener {

    /**
     * @param position The origo of the blackout
     * @param scale
     * @param rotation
     * @param id An id used to identify its graphical object in the view
     */

    public Blackout(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    /**
     * This method will activate the blackout, which means a timer will be started and the
     * black cylinder will pop up in a radius around the player.
     */
    public void activate(){
        Timer blackoutTime = new Timer(3,1000);
        blackoutTime.addListener(this);
        blackoutTime.start();

        // Show the blackout block
    }

    @Override
    public void timeIsUp() {
        // Hide the blackout block
    }
}
