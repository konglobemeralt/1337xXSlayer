package com.projectdgdx.game.libgdx;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;
import com.projectdgdx.game.model.AI.BasicNode;
import com.projectdgdx.game.model.Decorations.Decoration;
import com.projectdgdx.game.model.Map;
import com.projectdgdx.game.model.ModelStructure.Character;
import com.projectdgdx.game.model.ModelStructure.Entity;
import com.projectdgdx.game.model.ModelStructure.GameObject;
import com.projectdgdx.game.model.ModelStructure.StaticObject;
import com.projectdgdx.game.utils.VectorConverter;

/**
 * Created by Hampus on 2017-05-11.
 */
public class GameObjectContainer implements Disposable {
	protected GameObject gameObject;
	protected btRigidBody physicsObject;
	protected ModelInstance graphicObject;
	protected MyMotionState motionState;

	//Collision flags
	final static short STATIC_FLAG = 1<<8;
	final static short ENTITY_FLAG = 1<<9;
	final static short ALL_FLAG = -1;

	class MyMotionState extends btMotionState {
		Matrix4 transform;

		@Override
		public void getWorldTransform (Matrix4 worldTrans) {
			worldTrans.set(transform);
		}

		@Override
		public void setWorldTransform (Matrix4 worldTrans) {
			gameObject.setPosition(VectorConverter.convertFromLibgdx(worldTrans.getTranslation(new Vector3())));
//			System.out.println(gameObject.getClass().getName() + "  " + gameObject.getScale().toString());
//			Quaternion orientation = transform.getRotation(new Quaternion());
//
//			worldTrans.set(VectorConverter.convertToLibgdx(gameObject.getPosition()), orientation, VectorConverter.convertToLibgdx(gameObject.getScale()));

			transform.set(worldTrans);
		}
	}

	public GameObjectContainer(GameObject gameObject, ModelInstance graphicObject, btDynamicsWorld dynamicsWorld, Map map) {

		//Set internal data
		this.gameObject = gameObject;
		this.graphicObject = graphicObject;
		this.physicsObject = generatePhysicsProperties();


		//Disable movements in y:
		physicsObject.setLinearFactor(new Vector3(1,1,1));

		// Disable deactivation of physics object for moving objects
		if(gameObject instanceof Entity) {
			physicsObject.setActivationState(Collision.DISABLE_DEACTIVATION);
		}


		//Setup motion state
		MyMotionState motionState = new MyMotionState();
		motionState.transform = graphicObject.transform;
		physicsObject.setMotionState(motionState);

		//Set binding to GameObject within Map
		physicsObject.setUserValue(map.getGameObjects().indexOf(gameObject));
		physicsObject.setCollisionFlags(physicsObject.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);

		//Add PhysicsObject to dynamicworld, depends on GameObject type
		if(gameObject instanceof Entity) {
			dynamicsWorld.addRigidBody(physicsObject, ENTITY_FLAG, STATIC_FLAG);
		}else if(gameObject instanceof BasicNode) {
			physicsObject = null;
		} else if(gameObject instanceof Decoration) {
			//Ignore decorations it's pointless for now
		} else if(gameObject instanceof StaticObject) {
			dynamicsWorld.addRigidBody(physicsObject, STATIC_FLAG, ENTITY_FLAG);
		}


	}

	public btRigidBody generatePhysicsProperties() {
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
			BoundingBox boundingBox = graphicObject.calculateBoundingBox(new BoundingBox());
			btCollisionShape collisionShape;

			if(gameObject instanceof Character) {
				collisionShape = new btCylinderShape(new Vector3(1,1,4));
			}else {
				collisionShape = new btBoxShape(boundingBox.getDimensions(new Vector3()).scl(0.5f));
			}

			//Calculate localInertia
			Vector3 localInertia = new Vector3(0,0,0);
			if (mass > 0f) {
				collisionShape.calculateLocalInertia(mass, localInertia);
			} else {
				localInertia.set(0, 0, 0);
			}

			//Return physics object properties
			return new btRigidBody(mass, null, collisionShape, localInertia);
		}
		return null;

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

	public btRigidBody getPhysicsObject() {
		return this.physicsObject;
	}

	public GameObject getGameObject() {
		return this.gameObject;
	}
}