package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * Created by Eddie on 2017-05-09.
 */
public class ModelDataHandler {

    private static List<HonestInteractable> honestInteractables;
    private static List<DishonestInteractable> dishonestInteractables;
    private static List<Character> characters;

    // Placeholder values
    private static Blackout blackout = new Blackout(new Vector3d(1,1,1), new Vector3d(1,1,1), new Vector3d(1,1,1), "Blackout");

    private static StrikeZone strikeZone;

    public static StrikeZone getStrikeZone() {
        return strikeZone;
    }

    public static void setStrikeZone(StrikeZone strikeZone) {
        ModelDataHandler.strikeZone = strikeZone;
    }

    public List<HonestInteractable> getHonestInteractables() {
        return honestInteractables;
    }

    public static void setHonestInteractables(List<HonestInteractable> honestInteractables) {
        honestInteractables = honestInteractables;
    }

    public static List<DishonestInteractable> getDishonestInteractables() {
        return dishonestInteractables;
    }

    public static void setDishonestInteractables(List<DishonestInteractable> dishonestInteractables) {
        dishonestInteractables = dishonestInteractables;
    }

    public static List<Character> getCharacters() {
        return characters;
    }

    public static void setCharacters(List<Character> characters) {
        characters = characters;
    }

    public static Blackout getBlackout() {
        return blackout;
    }

}
