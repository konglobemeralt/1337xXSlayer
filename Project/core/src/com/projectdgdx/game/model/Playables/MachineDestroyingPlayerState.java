package com.projectdgdx.game.model.Playables;

import com.projectdgdx.game.model.MachineStates.DestroyedMachineState;
import com.projectdgdx.game.model.StaticInteractable.Machine;
import com.projectdgdx.game.model.StaticInteractable.Spotlight;
import com.projectdgdx.game.model.iDishonestInteractable;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;

/**
 * This is the PlayerState that the Saboteur enters when destroying a Machine.The Saboteur will stand
 * still in 3 seconds before the Machine is destroyed.
 */
public class MachineDestroyingPlayerState implements iPlayerState, iTimerListener {

    Machine machine;
    PlayableCharacter currentUser;

    public MachineDestroyingPlayerState(iDishonestInteractable machine, PlayableCharacter currentPlayer){
        this.machine = (Machine) machine;
        this.currentUser = currentPlayer;
        // Play some kind of animation
        Timer timer = new Timer(3, 1000);
        timer.addListener(this);
        timer.start();
    }

    @Override
    public void move(Vector3d vector) {
        currentUser.setMoveForce(new Vector3d(0,0,0));
    }

    @Override
    public void timeIsUp() {
        System.out.println("Destroyed machine!");
        DestroyedMachineState destroyedState = new DestroyedMachineState(machine);
        this.currentUser.setState(new NormalPlayerState(currentUser));
        this.machine.setState(destroyedState);

        System.out.println("Spotlights from Machine size: "+this.machine.getBigDetectingSpotlights().size());
        for(Spotlight mainSpotlight : this.machine.getBigDetectingSpotlights()){
            mainSpotlight.addListener(destroyedState);
        }
    }
}
