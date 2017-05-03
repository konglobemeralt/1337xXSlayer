package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector2d;

/**
 * Created by Eddie on 2017-04-28.
 */
public class InputModel {
    private Vector2d leftStick = new Vector2d(0,0);

    //This method is not pure.
    public Vector2d getLeftStick() {
        return leftStick;
    }

}
