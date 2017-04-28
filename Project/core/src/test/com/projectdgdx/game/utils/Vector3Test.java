package com.projectdgdx.game.utils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by konglobemeralt on 2017-04-27.
 */
public class Vector3Test {

    @Test
    public void setValues() throws Exception {
        Vector3 vector = new Vector3(0, 0, 0);
        assertTrue(vector.x == 0 && vector.x == 0 && vector.x == 0);
    }
}
