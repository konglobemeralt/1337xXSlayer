package com.projectdgdx.game.model.decorations;

import com.projectdgdx.game.model.modelStructure.StaticObject;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Decoration is a GameObject used for everything that has no function but has data needing to be displayed
 */
public class Decoration extends StaticObject {
	public Decoration(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
		super(position, scale, rotation, id);
	}
}