package com.projectdgdx.game.controller;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;
import com.projectdgdx.game.model.Entity;
import com.projectdgdx.game.model.GameObject;

/**
 * Created by Hampus on 2017-05-11.
 */
public class GameObjectContainer implements Disposable {
	protected GameObject gameObject;
	protected btRigidBody physicsObject;
	protected ModelInstance graphicObject;
	protected MyMotionState motionState;

	static class MyMotionState extends btMotionState {
		Matrix4 transform;

		@Override
		public void getWorldTransform (Matrix4 worldTrans) {
			worldTrans.set(transform);
		}

		@Override
		public void setWorldTransform (Matrix4 worldTrans) {
			System.out.println("ASDSDAOKDS");
			transform.set(worldTrans);
		}
	}

	public GameObjectContainer(GameObject gameObject, ModelInstance graphicObject) {

		//Set internal data
		this.gameObject = gameObject;
		this.graphicObject = graphicObject;
		if(graphicObject != null ) {
			this.motionState = new MyMotionState();

			//Set mass
			float mass;
			if(gameObject instanceof Entity) {
				mass = 1;
			} else {
				mass = 0;
			}

			//Calculate bounding box and set to shape
			BoundingBox boundingBox = graphicObject.model.calculateBoundingBox(new BoundingBox());
			btCollisionShape collisionShape = new btBoxShape(boundingBox.getDimensions(new Vector3()).scl(0.5f));

			//Calculate localInertia
			Vector3 localInertia = new Vector3(0,0,0);
			if (mass > 0f) {
				collisionShape.calculateLocalInertia(mass, localInertia);
			} else {
				localInertia.set(0, 0, 0);
			}

			//Set internal physics object
			this.physicsObject = new btRigidBody(0f, null, collisionShape, localInertia);
		}

	}

	@Override
	public void dispose () {
		if(physicsObject != null ) {
			physicsObject.getCollisionShape().dispose();
			physicsObject.dispose();
			motionState.dispose();
		}
	}

	public ModelInstance getGraphicObject() {
		return this.graphicObject;
	}
}