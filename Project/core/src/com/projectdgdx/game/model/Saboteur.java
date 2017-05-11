package com.projectdgdx.game.model;

import com.projectdgdx.game.Config;
import com.projectdgdx.game.utils.Vector3d;

import java.util.List;

/**
 * Created by Eddie on 2017-04-03.
 */
public class Saboteur extends PlayableCharacter{

    private int blakoutsLeft = 2;

    public Saboteur(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
    }

    @Override
    public void dishonestInteract(List<DishonestInteractable> dishonestInteractables) {
        for(DishonestInteractable di : dishonestInteractables){
            if(this.canDishonestInteract(di)){
                di.dishonestInteract(this);
            }
        }
    }

    private boolean canDishonestInteract(DishonestInteractable di){
        float value = this.getPosition().distanceTo(di.getPosition()) - Config.HONEST_ACT_DISTANCE;
        return value < 0;
    }

    @Override
    public void useAbility(){
        ModelDataHandler.getBlackout().setPosition(this.getPosition());
        ModelDataHandler.getBlackout().activate();
    }

    @Override
    public void beenCaught() {
        // Here we want to change state to "EndGameState" probably by using an "EndGameListener"-isch kind of solution
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }
}
