package com.projectdgdx.game.model;

import com.projectdgdx.game.Config;
import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * Created by Eddie on 2017-04-03.
 */
public abstract class PlayableCharacter extends Character {

    protected iPlayerState state;

    public PlayableCharacter(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
        this.setStartingState();
    }

    /**
     * Recieves the movement inputs and delegates it to the state machine.
     * TODO Will buttons be handled here or go directly to the functions?
     * TODO  ANSWER: Will call functions. We want to separate all input handling from the model.
     */

    public void reactToInput(){
        this.state.move(new Vector3d(1,1,1));
    }

    /**
     * Sets the starting state of a character
     * @param newState iPlayerState
     */
    public void setState(iPlayerState newState){
        this.state = newState;
    }

    /**
     * The character preforms a covert action.
     * @param List<iDishonestInteractable> dishonestInteractables
     */
    public abstract void dishonestInteract(List<iDishonestInteractable> dishonestInteractables);

    /**
     * The character uses it's special ability.
     */
    public abstract void useAbility();

    /**
     * The character tries to interact with nearby interactable objects.
     * @param interactables The list of objects the character tries to interact with.
     */

    public void honestInteract(List<iHonestInteractable> interactables){
        for (iHonestInteractable i: interactables){
            if (canHonestInteract(i)){
                i.honestInteract(this);
            }
        }
    }

    /**
     *
     * @param hi iHonestInteractable
     * @return boolean value depending on if HonestInteract is possible or not
     */
    protected boolean canHonestInteract(iHonestInteractable hi){
        float value = this.getPosition().distanceTo(hi.getPosition()) - Config.HONEST_ACT_DISTANCE;
        return value < 0;
    }

    protected void setStartingState(){
        this.state = new NormalPlayerState();
    }

    @Override
    public void move(Vector3d vec){
        if(!isColliding(vec)){
            this.state.move(vec);
        }
    }

}
