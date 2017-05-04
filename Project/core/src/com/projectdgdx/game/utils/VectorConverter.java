package com.projectdgdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Eddie on 2017-05-02.
 */
public class VectorConverter {

    public static Vector3 convertToLibgdx(Vector3d vector){
        return new Vector3(vector.x, vector.y, vector.z);
    }

    public static Vector2 convertToLibgdx(Vector2d vector){
        return new Vector2(vector.x, vector.z);
    }
}
