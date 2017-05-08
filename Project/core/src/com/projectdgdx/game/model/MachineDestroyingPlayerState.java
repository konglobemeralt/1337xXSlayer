package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.TimerListener;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class MachineDestroyingPlayerState implements PlayerState, TimerListener {

    // This is where we write what actually will happen when dishonest interacting

    DishonestInteractable machine;
    PlayableCharacter currentUser;

    MachineDestroyingPlayerState(DishonestInteractable machine, PlayableCharacter currentPlayer){
        this.machine = machine;
        this.currentUser = currentPlayer;
        // Play some kind of animation
        Timer timer = new Timer(3, 1000);
        timer.addListener(this);
    }

    @Override
    public void move(Vector3d vector) {
        // Empty since it will be more suspense if the saboteur can't move while sabotaging
    }

    @Override
    public void timeIsUp() {
        this.currentUser.setState(new NormalPlayerState());
        this.machine.setState(new DestroyedMachineState(machine.getPosition()));
    }
}
