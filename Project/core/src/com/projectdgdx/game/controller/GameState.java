package com.projectdgdx.game.controller;

import com.projectdgdx.game.ProjectD;

/**
 * This Interface provides a neat interface for states. All of these methods will be called by the main controller.
 *
 * Created by Eddie on 2017-04-28.
 */
public interface GameState {
    void update(ProjectD projectD);
    void init(ProjectD projectD);
    void exit(ProjectD projectD);

    void start(ProjectD projectD);
    void stop(ProjectD projectD);
}
