package com.projectdgdx.game.model.Decorations;

import com.projectdgdx.game.model.ModelStructure.StaticObject;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Decoration is a GameObject used for everything that has no function, but still data that has to be displayed
 */
public class Decoration extends StaticObject {
	public Decoration(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
		super(position, scale, rotation, id);
	}
}