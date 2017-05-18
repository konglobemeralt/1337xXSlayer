package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * The Supervisors are the "honest" player controlled characters in the game that are supposed
 * catch the Saboteur before too much damage has been done.
 */
public class Supervisor extends PlayableCharacter {
    public Supervisor(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void dishonestInteract(List<iDishonestInteractable> dishonestInteractables) {
        // Should be empty since a supervisor can not sabotage the machines.
    }

    @Override
    public void useAbility(List<Character> characters) {
        this.catsch(characters);
    }

    /**
     * Gets all of the characters that are in a radius around the Supervisors. Used to determine
     * which character that the Supervisor is supposed to catch.
     * @param characters , a list of all Characters in the game.
     * @return a list containing all the characters that are in the specified radius.
     */
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

    /**
     * This method will determine the closest character of all character in the radius.
     * @param charactersInRadius , a list containing the characters that are inr adius.
     * @return the character closest to hte PlayableCharacter.
     */
    private Character getClosestCharacter(List<Character> charactersInRadius){ //TODO Checks the first character twice

        Character closestCharacter = new Worker(new Vector3d(0,0,0), new Vector3d(0,0,0), new Vector3d(0,0,0), "ss");
        float closestDistance = 10000;
        for(Character c : charactersInRadius){
            if (!(c == this)){
                float characterToThisCharacterDistance = c.getPosition().distanceTo(this.getPosition());
                if(characterToThisCharacterDistance < closestDistance){
                    closestCharacter = c;
                    closestDistance = characterToThisCharacterDistance;
                }
            }
        }
        return closestCharacter;
    }

    /**
     * Verifies if the character in the radius Character is catchable.
     * @param character , the character to verify if it is in radius.
     * @return true if the character is in the catching radius else false.
     */
    private boolean canCatch(Character character){
        return this.getPosition().isInRadius(character.getPosition(), Config.USE_ABILITY_ACT_DISTANCE);
    }

    /**
     * The actual method that actually catches the Character. Runs all methods that verifies if
     * a character is catchable and then catch the Character. (Misspelled because of the keyword catch in java)
     * @param characters , all Characters in the game.
     */
    public void catsch(List<Character> characters){
        List<Character> charactersInRadius = getCharactersInRadius(characters);
        Character closestCharacter = getClosestCharacter(charactersInRadius);
        closestCharacter.beenCaught();
    }

    @Override
    public void beenCaught() {
        this.setState(new CapturedPlayerState(this));
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }

}
