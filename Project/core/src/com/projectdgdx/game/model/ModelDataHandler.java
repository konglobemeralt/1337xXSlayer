package com.projectdgdx.game.model;

import java.util.List;

/**
 * Created by Eddie on 2017-05-09.
 */
public class ModelDataHandler {

    private List<HonestInteractable> honestInteractables;
    private List<DishonestInteractable> dishonestInteractables;
    private List<Character> characters;

    public List<HonestInteractable> getHonestInteractables() {
        return honestInteractables;
    }

    public void setHonestInteractables(List<HonestInteractable> honestInteractables) {
        this.honestInteractables = honestInteractables;
    }

    public List<DishonestInteractable> getDishonestInteractables() {
        return dishonestInteractables;
    }

    public void setDishonestInteractables(List<DishonestInteractable> dishonestInteractables) {
        this.dishonestInteractables = dishonestInteractables;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }


}
