package com.projectdgdx.game.utils;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Eddie on 2017-05-02.
 */
public class Vector3dConverter {
    public static Vector3 convertToLibgdx(Vector3d vector){
        return new Vector3(vector.x, vector.y, vector.z);
    }

}
