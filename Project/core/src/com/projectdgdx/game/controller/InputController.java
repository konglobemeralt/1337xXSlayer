package com.projectdgdx.game.controller;

import com.projectdgdx.game.model.InputModel;

import java.util.UUID;

/**
 * Created by Eddie on 2017-04-28.
 *
 * This is the abstract class controller that is a base for all sorts of inputs.
 * It contains a InputModel instance where all input data should be saved to.
 */
public abstract class InputController {
    final private UUID uuid = UUID.randomUUID();
    private InputModel inputModel;

    public void setModel(InputModel newModel){
        this.inputModel = newModel;
    }

    public InputModel getModel(){
        return inputModel;
    }

    public UUID getUUID(){
        return uuid;
    }


}
