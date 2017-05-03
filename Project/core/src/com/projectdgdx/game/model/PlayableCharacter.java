package com.projectdgdx.game.model;

import com.badlogic.gdx.math.Vector3;

import java.util.List;

/**
 * Created by Eddie on 2017-04-03.
 */
public abstract class PlayableCharacter extends Character {

    public PlayableCharacter(Vector3 position, Vector3 scale, Vector3 rotation, String id) {
        super(position, scale, rotation, id);
    }

    /**
     * Recieves the movement inputs and delegates it to the state machine.
     * TODO Will buttons be handled here or go directly to the functions?
     * TODO  ANSWER: Will call functions. We want to separate all input handling from the model.
     */

    public void reactToInput(){
        this.state.move();
    }

    /**
     * The character preforms a covert action.
     */

    public abstract void dishonestInteract();

    /**
     * The character uses it's special ability.
     */
    public abstract void useAbility();

    /**
     * The character tries to interact with nearby interactable objects.
     * @param interactables The list of objects the character tries to interact with.
     */

    public void honestInteract(List<HonestInteractable> interactables){
        for (HonestInteractable i: interactables){
            if (canHonestInteract(i)){
                i.honestInteract();
            }
        }
    }

    /**
     *
     * @param i
     * @return
     */
    protected boolean canHonestInteract(HonestInteractable i){
        float value = this.getPosition().dst2(i.getPosition()) - GlobalVariables.machineActDistance;
        return value < 0;
    }

    @Override
    protected void setStartingState(){
        this.state = new NormalPlayerState();
    }



}
