package com.projectdgdx.game.model.ai;

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

	/**
	 * Since the worker delgates what it should do when caught to the state.
	 * @param worker
	 */
	void beenCaught(Worker worker);
}
