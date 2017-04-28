package com.projectdgdx.game.controller;

import com.projectdgdx.game.ProjectD;

/**
 * Created by Eddie on 2017-04-28.
 */
public interface GameState {
    void update(ProjectD projectD);
    void init(ProjectD projectD);
    void exit();
}
