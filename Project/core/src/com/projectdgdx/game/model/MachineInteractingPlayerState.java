package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.iTimerListener;
import com.projectdgdx.game.utils.Vector3d;

/**
 * This PlayerState is for when a Supervisor or Saboteur honest interacts with a machine.
 * The will stand still for 3 seconds and then the Machines internal timer will be updated.
 */
public class MachineInteractingPlayerState implements iPlayerState, iTimerListener {
    private Machine machine;
    private PlayableCharacter currentPlayer;

    MachineInteractingPlayerState(iHonestInteractable machine, PlayableCharacter currentPlayer){
        this.currentPlayer = currentPlayer;
        this.machine = (Machine) machine;
        // Play some kind of animation
        Timer timer = new Timer(3, 1000);
        timer.addListener(this);
    }

    @Override
    public void move(Vector3d vector) {
        // Player is stationary during machine interaction
    }

    @Override
    public void timeIsUp() {
        this.machine.updateTimer();
        this.currentPlayer.setState(new NormalPlayerState(currentPlayer));
    }
}
