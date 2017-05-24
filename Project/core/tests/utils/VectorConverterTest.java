package utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector2d;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.VectorConverter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Eddie on 2017-05-02.
 */
public class VectorConverterTest{
    Vector2d vec2;
    Vector3d vec3;


    @Before
    public void setup(){
        vec2 = new Vector2d(1,2);
        vec3 = new Vector3d(1,2,3);
    }

    @Test
    public void testConvertVectors(){
        // Verify that vectors can be converted from our own to the libGDX vectors.
        Vector2 newVec2 = VectorConverter.convertToLibgdx(vec2);
        Vector3 newVec3 = VectorConverter.convertToLibgdx(vec3);

        // Verify type conversion
        assertEquals(newVec2.getClass().getName(), "com.badlogic.gdx.math.Vector2");
        assertEquals(newVec3.getClass().getName(), "com.badlogic.gdx.math.Vector3");

        // Verify that the dataHolding is the same as before conversion
        assertTrue(newVec2.x == 1 && newVec2.y == 2);
        assertTrue(newVec3.x == 1 && newVec3.y == 2 && newVec3.z == 3);
    }
}
