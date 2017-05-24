package utils;

import com.projectdgdx.game.model.ai.Worker;
import com.projectdgdx.game.model.ai.WorkerNode;
import com.projectdgdx.game.model.dataHolding.GameObjectInit;
import com.projectdgdx.game.model.decorations.Decoration;
import com.projectdgdx.game.model.gameplay.Machine;
import com.projectdgdx.game.model.gameplay.Saboteur;
import com.projectdgdx.game.model.gameplay.SpotlightControlBoard;
import com.projectdgdx.game.model.gameplay.Supervisor;
import com.projectdgdx.game.model.objectStructure.GameObject;
import com.projectdgdx.game.utils.Vector3d;
import org.junit.Test;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2017-04-07.
 */
public class GameObjectInitTest {
    @Test
    public void convert() throws Exception {
        GameObjectInit gameObjectInit = new GameObjectInit("Machine");
        assertTrue(gameObjectInit.convert() instanceof Machine);

        gameObjectInit = new GameObjectInit("SomethingThatDoesNotExists");
        assertTrue(gameObjectInit.convert() == null);
    }

    @Test
    public void changeValue() throws Exception {
        int newXPosition = 21032103;
        int newYPosition = 99332103;
        int newZPosition = 59434103;

        GameObjectInit gameObjectInit = new GameObjectInit("Machine");
        gameObjectInit.changeValue("x", Integer.toString(newXPosition));
        gameObjectInit.changeValue("y", Integer.toString(newYPosition));
        gameObjectInit.changeValue("z", Integer.toString(newZPosition));

        GameObject gameObject = gameObjectInit.convert();
        assertTrue(gameObject instanceof Machine);
        assertTrue(gameObject.getPosition().x == newXPosition);
        assertTrue(gameObject.getPosition().y == newYPosition);
        assertTrue(gameObject.getPosition().z == newZPosition);
    }

    @Test
    public void cloneGameObjectInit() throws Exception {
        GameObjectInit gameObjectInit = new GameObjectInit("Saboteur");
        gameObjectInit.changeValue("x", "213123");
        gameObjectInit.changeValue("y", "231");
        gameObjectInit.changeValue("z", "6666664");
        Saboteur saboteur = (Saboteur)gameObjectInit.convert();
        GameObjectInit clonedGameObjectInit = gameObjectInit.clone();
        Saboteur clonedSaboteur = (Saboteur)clonedGameObjectInit.convert();

        assertTrue(saboteur.getPosition().equals(clonedSaboteur.getPosition()));
        assertTrue(saboteur.getScale().equals(clonedSaboteur.getScale()));
        assertTrue(saboteur.getId().equals(clonedSaboteur.getId()));

        gameObjectInit.changeValue("x", "843858");
        gameObjectInit.changeValue("y", "50059");
        gameObjectInit.changeValue("z", "4283");

        Saboteur saboteur2 = (Saboteur)gameObjectInit.convert();
        Saboteur clonedSaboteur2 = (Saboteur)clonedGameObjectInit.convert();

        assertFalse(saboteur2.getPosition().equals(clonedSaboteur2.getPosition()));
        assertTrue(saboteur2.getScale().equals(clonedSaboteur2.getScale()));
        assertTrue(saboteur2.getId().equals(clonedSaboteur2.getId()));
    }

    @Test
    public void testGameObjectTypes() {
        GameObjectInit gameObjectInit = new GameObjectInit("Machine");
        assertTrue(gameObjectInit.convert() instanceof Machine);

        gameObjectInit = new GameObjectInit("SpotControl");
        assertTrue(gameObjectInit.convert() instanceof SpotlightControlBoard);

        gameObjectInit = new GameObjectInit("Worker");
        assertTrue(gameObjectInit.convert() instanceof Worker);

        gameObjectInit = new GameObjectInit("Supervisor");
        assertTrue(gameObjectInit.convert() instanceof Supervisor);

        gameObjectInit = new GameObjectInit("Saboteur");
        assertTrue(gameObjectInit.convert() instanceof Saboteur);


        gameObjectInit = new GameObjectInit("WorkerNode");
        assertTrue(gameObjectInit.convert() instanceof WorkerNode);


        gameObjectInit = new GameObjectInit("Floor");
        assertTrue(gameObjectInit.convert() instanceof Decoration);

        gameObjectInit = new GameObjectInit("THISONEDOESNOTBELONG");
        assertTrue(gameObjectInit.convert() == null);
    }

    @Test
    public void testSetValues() {
        float x = 1;
        float y = 2;
        float z = 3;

        float scaleX = 4;
        float scaleY = 5;
        float scaleZ = 6;

        float rotationX = 7;
        float rotationY = 8;
        float rotationZ = 9;

        //Node
        int nodeId = 1337;
        List<Integer> nodeFriends = new ArrayList<>();
        nodeFriends.add(1);
        nodeFriends.add(3);
        nodeFriends.add(4);

        GameObjectInit gameObjectInit = new GameObjectInit("WorkerNode");
        gameObjectInit.changeValue("x", Float.toString(x));
        gameObjectInit.changeValue("y", Float.toString(y));
        gameObjectInit.changeValue("z", Float.toString(z));

        gameObjectInit.changeValue("rotationX", Float.toString(rotationX));
        gameObjectInit.changeValue("rotationY", Float.toString(rotationY));
        gameObjectInit.changeValue("rotationZ", Float.toString(rotationZ));

        gameObjectInit.changeValue("scaleX", Float.toString(scaleX));
        gameObjectInit.changeValue("scaleY", Float.toString(scaleY));
        gameObjectInit.changeValue("scaleZ", Float.toString(scaleZ));

        gameObjectInit.changeValue("nodeId", Integer.toString(nodeId));
        gameObjectInit.changeValue("nodeFriends", "1,3,4");

        WorkerNode workerNode = (WorkerNode)gameObjectInit.convert();

        assertTrue(workerNode.getPosition().equals(new Vector3d(x,y,z)));
        assertTrue(workerNode.getScale().equals(new Vector3d(scaleX,scaleY,scaleZ)));
        assertTrue(workerNode.getRotation().equals(new Vector3d(rotationX,rotationY,rotationZ)));
        assertTrue(workerNode.getNodeId() == nodeId);

        List<Integer> nodeFriendsIds = workerNode.getNodeFriendIds();
        assertTrue(nodeFriendsIds.contains(1));
        assertTrue(nodeFriendsIds.contains(3));
        assertTrue(nodeFriendsIds.contains(4));
        assertTrue(nodeFriendsIds.size() == 3);

        //Change not existing value
        gameObjectInit.changeValue("nada", "epicValue");
        gameObjectInit.changeValue("12312", "kafmaf");
        gameObjectInit.changeValue("xyz", "123");
        WorkerNode workerNode2 = (WorkerNode)gameObjectInit.convert();
        assertTrue(workerNode2.getPosition().equals(new Vector3d(x,y,z)));
        assertTrue(workerNode2.getScale().equals(new Vector3d(scaleX,scaleY,scaleZ)));
        assertTrue(workerNode2.getRotation().equals(new Vector3d(rotationX,rotationY,rotationZ)));
        assertTrue(workerNode2.getNodeId() == nodeId);

        List<Integer> nodeFriendsIds2 = workerNode2.getNodeFriendIds();
        assertTrue(nodeFriendsIds2.contains(1));
        assertTrue(nodeFriendsIds2.contains(3));
        assertTrue(nodeFriendsIds2.contains(4));
        assertTrue(nodeFriendsIds2.size() == 3);

    }

}