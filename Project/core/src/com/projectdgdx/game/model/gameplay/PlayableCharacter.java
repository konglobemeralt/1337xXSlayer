package com.projectdgdx.game.model.gameplay;

import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * The PlayableCharacter is the abstract superclass for all the objects that a physical player can control
 * eg. Supervisors and Saboteurs.
 */
public abstract class PlayableCharacter extends Character {

    private iPlayerState state;

    public PlayableCharacter(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    /**
     * Sets the internal state of a character.
     * @param newState iPlayerState
     */
    public void setState(iPlayerState newState){
        this.state = newState;
    }

    public iPlayerState getState() {
        return state;
    }

    /**
     * The character preforms a covert action.
     * @param dishonestInteractables , a list of the objects that can be interacted with in a dishonest way.
     */
    public abstract void dishonestInteract(List<iDishonestInteractable> dishonestInteractables);

    /**
     * The character uses it's special ability.
     */
    public abstract void useAbility(List<Character> characters);

    /**
     * The character tries to gameplay with nearby honest objects.
     * @param interactables The list of objects the character tries to honest gameplay with.
     */
    public void honestInteract(List<iHonestInteractable> interactables){
        for (iHonestInteractable i: interactables){
            if (canHonestInteract(i)){
                System.out.println("HonestInteract"); //TODO this is debug
                i.honestInteract(this);
            }
        }
    }

    /**
     * Verifies if any honest object is close enough to gameplay with.
     * @param hi iHonestInteractable
     * @return boolean value depending on if HonestInteract is possible or not
     */
    protected boolean canHonestInteract(iHonestInteractable hi){
        float value = this.getPosition().distanceTo(hi.getPosition()) - Config.HONEST_ACT_DISTANCE;
        return value < 0;
    }

    @Override
    public void move(Vector3d vec){
        if(!isColliding(vec)){
            this.state.move(vec);
        }
    }

}
