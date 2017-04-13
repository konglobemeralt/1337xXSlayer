//package test;
//
//import com.badlogic.gdx.math.Vector3;
//import com.projectdgdx.game.gameobjects.GameObject;
//import com.projectdgdx.game.gameobjects.Supervisor;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.Assert;
//
//
//public class GameObjectTest {
//
//    Vector3 testPos;
//    Vector3 testRotate;
//    Vector3 testScale;
//    String testId;
//    GameObject testGO;
//
//    @Before
//    public void setup(){
//        testPos = new Vector3(1,1,1);
//        testScale = new Vector3(1,1,1);
//        testRotate = new Vector3(1,1,1);
//        testId = "testId";
//        // Since GameObject is an abstract class it is needed to create an instance of one of its
//        // subclasses to test the code inherited from GameObject.
//        testGO = new Supervisor(testPos, testScale, testRotate, testId);
//    }
//
//    @Test
//    public void testAttributes(){
//        // Verify that no attributes are set to null
//        Assert.assertTrue(testGO.getPosition() != null);
//        Assert.assertTrue(testGO.getRotation() != null);
//        Assert.assertTrue(testGO.getScale() != null);
//        Assert.assertTrue(testGO.getId() != null);
//
//        // Verify that all attributes return what they're supposed to
//        Assert.assertEquals(testPos, testGO.getPosition());
//        Assert.assertEquals(testRotate, testGO.getRotation());
//        Assert.assertEquals(testScale, testGO.getScale());
//        Assert.assertEquals(testId, testGO.getId());
//    }
//
//    @Test
//    public void testSetters(){
//        // Verify that a -value can be increased
//        testGO.addPositionX(0.2f);
//        Assert.assertEquals(1.2f, testGO.getPosition().x, 0.000001);
//
//        // Verify that a -value can be decreased
//        testGO.addPositionX(-0.4f);
//        Assert.assertEquals(0.8f, testGO.getPosition().x, 0.000001);
//
//    }
//
//}
