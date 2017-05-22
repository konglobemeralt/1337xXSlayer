package com.projectdgdx.game.model.AI;

import com.projectdgdx.game.model.Worker;

/**
 * Created by Emil Jansson on 2017-05-09.
 */
public interface iWorkerState {

    /**
     * Perform the actions expected for a worker in this state.
     * @param worker The worker who is in this state.
     */

	void reactOnUpdate(Worker worker);
	void beenCaught(Worker worker);
}
