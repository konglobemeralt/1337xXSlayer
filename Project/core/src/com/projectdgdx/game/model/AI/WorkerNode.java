package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.GameObject;
import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * Created by Emil Jansson on 2017-05-08.
 */
public class WorkerNode extends AINode {
    public WorkerNode(Vector3d position, Vector3d scale, Vector3d rotation, String id, int nodeId, List<Integer> friendList) {
        super(position, scale, rotation, id, nodeId, friendList);
    }


}
