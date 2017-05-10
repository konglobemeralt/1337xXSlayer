package test.com.projectdgdx.game.utils;
import com.projectdgdx.game.utils.Vector3d;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    @Test
    public void testVectorDistance(){
        // Verify that the distance between two vectors/points is correct according to linear algebra
        Vector3d distVec = new Vector3d(1,1,2);
        int distance = 1;
        assertTrue(Math.abs(vector.distanceTo(distVec)-distance) < 0.000001);

    }

    @Test
    public void testGetLength(){
        // Verify that the length returned is right according to pythagoras theorem.
        Vector3d testVector = new Vector3d(23,86,52);
        assertEquals((float) Math.sqrt(23f*23f + 86f*86f + 52f*52f), testVector.getLength(), 0.000001);
    }

    @Test
    public void testNormalised(){
        // Verify that the normalised vectors length is equal to 1.
        Vector3d testVector = new Vector3d(54,23,57);
        assertEquals(1f, testVector.normalised().getLength(), 0.000001);
    }

    @Test
    public void testVectorTo(){
        // TODO vet ej vad skriva
        Vector3d testVector1 = new Vector3d(74,82,17);
        Vector3d testVector2 = new Vector3d(-13, 52, -64);
        Vector3d Vector1to2  = testVector1.vectorTo(testVector2);
        assertTrue(Math.abs(Vector1to2.x - (-13-74)) < 0.000001);
        assertTrue(Math.abs(Vector1to2.y - (52-82)) < 0.000001);
        assertTrue(Math.abs(Vector1to2.z - (-64-17)) < 0.000001);
    }

    @Test
    public void testIsInRadius(){
        // Verify that a vector inside the radius returns true while a vector outside of the radius returns false.
        Vector3d testVector1 = new Vector3d(74,82,17);
        Vector3d testVector2 = new Vector3d(-13, 52, -64);
        Vector3d testVector3 = new Vector3d(-18, 52, -64);
        float testRadius     = 16;
        assertFalse(testVector1.isInRadius(testVector2, testRadius));
        assertTrue(testVector2.isInRadius(testVector3, testRadius));
    }
}
