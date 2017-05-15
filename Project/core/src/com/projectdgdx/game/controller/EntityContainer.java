package com.projectdgdx.game.controller;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.model.Map;
import com.projectdgdx.game.utils.VectorConverter;

/**
 * Created by Hampus on 2017-05-11.
 */
public class EntityContainer extends GameObjectContainer {
	public EntityContainer(GameObject gameObject, ModelInstance graphicObject, btDynamicsWorld dynamicsWorld, Map map) {
		super(gameObject, graphicObject, dynamicsWorld, map);
		physicsObject.setLinearFactor(new Vector3(1,1,1));
		physicsObject.setAngularFactor(new Vector3(0,0,0));
	}

	public void applyForce(Vector3 forceVector) {
		this.physicsObject.applyCentralImpulse(forceVector);
	}

	public void updateRotation(Vector3 rotation) {
		Matrix4 transform = physicsObject.getWorldTransform();
		Vector3 position = transform.getTranslation(new Vector3().add(0,0.2f,0));
		Vector3 scale = VectorConverter.convertToLibgdx(gameObject.getScale());
		Quaternion quaternion = new Quaternion();
		quaternion.setEulerAngles(rotation.y, 0, 0);

		physicsObject.setWorldTransform(new Matrix4(position, quaternion, scale));
	}
}
