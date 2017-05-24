package com.projectdgdx.game.model.objectStructure;

import com.projectdgdx.game.utils.Vector3d;

/**
 * The StaticObjects aren't necessarily different from the GameObjects, it is more of an abstract
 * layer to separate the objects in the view that stand still and the ones that move and create a more
 * logical hierarchical inheritance structure.
 */
public abstract class StaticObject extends GameObject {
    public StaticObject(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }
}
