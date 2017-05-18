package com.projectdgdx.game.controller;

import com.projectdgdx.game.model.Input.InputModel;

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


    /**
     *
     * @param newModel InputModel to be associated with this InputController
     */
    public void setModel(InputModel newModel){
        this.inputModel = newModel;
    }

    /**
     *
     * @return InputModel that has all data associated with this InputController
     */
    public InputModel getModel(){
        return inputModel;
    }

    /**
     *
     * @return A unique id for this InputController
     */
    public UUID getUUID(){
        return uuid;
    }


}
