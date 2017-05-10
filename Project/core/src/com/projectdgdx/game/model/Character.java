package com.projectdgdx.game.model;


import com.badlogic.gdx.math.Vector3;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2017-04-03.
 */
public abstract class Character extends Entity {

    public Character(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    public abstract void beenCaught();

    // Maybe cone instead of radius when checking for the players that are interesting
    private List<Character> getCharactersInRadius(List<Character> characters){
        List<Character> charactersInRadius = new ArrayList<>();
        for(Character c : characters){
            if(canCatch(c)){
                charactersInRadius.add(c);
            }
        }
        return charactersInRadius;
    }

    private Character getClosestCharacter(List<Character> charactersInRadius){
        Character closestCharacter = charactersInRadius.get(0);
        float closestDistance = charactersInRadius.get(0).getPosition().distanceTo(this.getPosition());
        for(Character c : charactersInRadius){
            float characterToThisCharacterDistance = c.getPosition().distanceTo(this.getPosition());
            if(characterToThisCharacterDistance < closestDistance){
                closestCharacter = c;
                closestDistance = characterToThisCharacterDistance;
            }
        }
        return closestCharacter;
    }

    private boolean canCatch(Character character){
        return this.getPosition().isInRadius(character.getPosition(), Config.useAbilityActDistance);
    }

    public void catsch(List<Character> characters){
        Character closestCharacter;

    }

}
