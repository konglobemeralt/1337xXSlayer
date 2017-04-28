package com.projectdgdx.game.controller;

import com.projectdgdx.game.model.InputModel;

/**
 * Created by Eddie on 2017-04-28.
 */
public abstract class InputController {
    final private String id = "";
    private InputModel inputModel;

    public void setModel(InputModel newModel){
        this.inputModel = newModel;
    }

    public InputModel getModel(){
        return inputModel;
    }

    public String getId(){
        return id;
    }


}
