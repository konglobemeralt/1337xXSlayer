package test.com.projectdgdx.game.utils;

import com.projectdgdx.game.utils.Vector2d;
import com.projectdgdx.game.utils.Vector3d;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Eddie on 2017-05-02.
 */
public class Vector2dTest {
    Vector2d vector;

    @Before
    public void setup(){
        vector = new Vector2d(1,1);
    }

    @Test
    public void testGetValues(){
        // Verify that the values assigned when created are correct
        assertTrue(vector.x == 1 && vector.z == 1);
    }

    @Test
    public void testAddVector2d(){
        // Verify that another vector ca be af´dded and that the values are correct
        Vector2d newVec = new Vector2d(2,3);
        vector.add(newVec);

        assertTrue(vector.x == 3 && vector.z == 4);
    }

    @Test
    public void getAngleVector2d(){
        // Verify that angle calculation gets performed accurately
        Vector2d newVec = new Vector2d(0,1);
        vector.add(newVec);

        assertTrue(newVec.getAngle() == 90);
    }

    @Test
    public void getLengthVector2d(){
        // Verify that angle length calculation gets performed accurately
        Vector2d newVec = new Vector2d(0,1);
        vector.add(newVec);

        assertTrue(newVec.getLength() == 1);
    }
}
