package com.projectdgdx.game.model.objectStructure;

import com.projectdgdx.game.utils.Vector3d;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {
	@Test
	public void setMoveForce() throws Exception {
		Entity entity = new Entity(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "Test") {
			@Override
			protected boolean isColliding(Vector3d vec) {
				return false;
			}
		};
		assertTrue(entity.getMoveForce().x == 0 && entity.getMoveForce().y == 0 && entity.getMoveForce().z == 0);
		entity.setMoveForce(new Vector3d(1,1,1));
		assertTrue(entity.getMoveForce().x == 1 && entity.getMoveForce().y == 1 && entity.getMoveForce().z == 1);
	}

	@Test
	public void getMoveForce() throws Exception {
		Entity entity = new Entity(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "Test") {
			@Override
			protected boolean isColliding(Vector3d vec) {
				return false;
			}
		};
		assertTrue(entity.getMoveForce().x == 0 && entity.getMoveForce().y == 0 && entity.getMoveForce().z == 0);
	}

	@Test
	public void move() throws Exception {
		Entity entity = new Entity(new Vector3d(0,0,0), new Vector3d(1,1,1), new Vector3d(0,0,0), "Test") {
			@Override
			protected boolean isColliding(Vector3d vec) {
				return false;
			}
		};
		assertTrue(entity.getMoveForce().x == 0 && entity.getMoveForce().y == 0 && entity.getMoveForce().z == 0);
		entity.move(new Vector3d(1,0,0));
		assertTrue(entity.getMoveForce().x == 1 && entity.getMoveForce().y == 0 && entity.getMoveForce().z == 0);

	}

}