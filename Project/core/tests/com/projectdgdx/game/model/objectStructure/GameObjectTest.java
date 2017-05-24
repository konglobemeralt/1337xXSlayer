package com.projectdgdx.game.model.objectStructure;

import com.projectdgdx.game.utils.Vector3d;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2017-05-25.
 */
public class GameObjectTest {
	@Test
	public void setPosition() throws Exception {
		GameObject gameObject = new GameObject(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "test") {
		};
		assertTrue(gameObject.getPosition().x == 0 && gameObject.getPosition().y == 0 && gameObject.getPosition().z == 0);
		gameObject.setPosition(new Vector3d(1,2,3));
		assertTrue(gameObject.getPosition().x == 1 && gameObject.getPosition().y == 2 && gameObject.getPosition().z == 3);
	}

	@Test
	public void setScale() throws Exception {
		GameObject gameObject = new GameObject(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "test") {
		};
		assertTrue(gameObject.getScale().x == 1 && gameObject.getScale().y == 1 && gameObject.getScale().z == 1);
		gameObject.setScale(new Vector3d(2,3,4));
		assertTrue(gameObject.getScale().x == 2 && gameObject.getScale().y == 3 && gameObject.getScale().z == 4);
	}

	@Test
	public void setRotation() throws Exception {
		GameObject gameObject = new GameObject(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "test") {
		};
		assertTrue(gameObject.getRotation().x == 0 && gameObject.getRotation().y == 0 && gameObject.getRotation().z == 0);
		gameObject.setRotation(new Vector3d(90,180,270));
		assertTrue(gameObject.getRotation().x == 90 && gameObject.getRotation().y == 180 && gameObject.getRotation().z == 270);
	}

	@Test
	public void setId() throws Exception {
		GameObject gameObject = new GameObject(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "test") {
		};
		assertTrue(gameObject.getId().equals("test"));
		gameObject.setId("epicid");
		assertTrue(gameObject.getId().equals("epicid"));
	}

}