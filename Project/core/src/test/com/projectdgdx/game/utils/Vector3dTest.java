package test.com.projectdgdx.game.utils;
import com.projectdgdx.game.utils.Vector3d;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by konglobemeralt on 2017-04-27.
 */
public class Vector3dTest {
    Vector3d vector;

    @Before
    public void create(){
        vector = new Vector3d(1, 1, 1);
    }


    @Test
    public void testGetValues() throws Exception {
        // Verify that all values have been rightfully assigned
        assertTrue(vector.x == 1 && vector.y == 1 && vector.z == 1);
    }

    @Test
    public void testAddVector(){
        // Verify that another vector3d will be added correctly
        Vector3d newVec = new Vector3d(2,3,4);
        vector.add(newVec);

        assertTrue(vector.x == 3 && vector.y == 4 && vector .z == 5);
    }
}
