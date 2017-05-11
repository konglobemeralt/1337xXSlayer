package com.projectdgdx.game.controller;

import com.projectdgdx.game.ProjectD;

/**
 * This Interface provides a neat interface for states. All of these methods will be called by the main controller.
 *
 * Created by Eddie on 2017-04-28.
 */
public interface iGameState {
    /**
     * Updates the current gameState.
     *
     * @param projectD ProjectD
     *
     */
    void update(ProjectD projectD);

    /**
     * Initates the current gameState.
     *
     * @param projectD ProjectD
     *
     */
    void init(ProjectD projectD);

    /**
     * Exits the current gameState.
     *
     * @param projectD ProjectD
     *
     */
    void exit(ProjectD projectD);

    /**
     * Starts the current gameState.
     *
     * @param projectD ProjectD
     *
     */
    void start(ProjectD projectD);

    /**
     * Stops the current gameState.
     *
     * @param projectD ProjectD
     *
     */
    void stop(ProjectD projectD);
}
