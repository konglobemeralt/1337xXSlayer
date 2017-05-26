package com.projectdgdx.game.model.UIStructure;

/**
 * Created by Jesper on 2017-05-26.
 *
 */
public abstract class UIitem {
    private String title;

    UIitem(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
