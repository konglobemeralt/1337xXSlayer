package com.projectdgdx.game.model.dataHolding;

import com.projectdgdx.game.model.objectStructure.GameObject;
import com.projectdgdx.game.utils.Timer;

import java.util.List;

/**
 * Created by Hampus on 2017-03-24.
 */
public class BasicMap extends Map {
    public BasicMap(List<GameObject> gameObjects, int machinesDestroyedToEnd, int strikingWorkersToEnd, Timer endgameTimer) {
        super(gameObjects, machinesDestroyedToEnd, strikingWorkersToEnd, endgameTimer);
    }
}
