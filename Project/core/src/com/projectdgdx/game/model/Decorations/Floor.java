package com.projectdgdx.game.model.Decorations;

import com.projectdgdx.game.model.ModelStructure.StaticObject;
import com.projectdgdx.game.utils.Vector3d;

/**
 * The Floor class is purely made for the GUI and for some collision handling.
 */
public class Floor extends StaticObject {
	public Floor(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
		super(position, scale, rotation, id);
	}
}
