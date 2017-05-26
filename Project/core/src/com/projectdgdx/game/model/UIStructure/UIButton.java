package com.projectdgdx.game.model.UIStructure;

/**
 * Created by Jesper on 2017-05-26.
 */
public class UIButton extends UIitem {

    String buttonText;

    UIButton(String buttonText){
        super("Button");
        this.buttonText = buttonText;
    }
}
