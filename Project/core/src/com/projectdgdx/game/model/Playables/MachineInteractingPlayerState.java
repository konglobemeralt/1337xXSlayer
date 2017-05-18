package com.projectdgdx.game.model.Playables;

import com.projectdgdx.game.model.StaticInteractable.Machine;
import com.projectdgdx.game.model.iHonestInteractable;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;

/**
 * This PlayerState is for when a Supervisor or Saboteur honest interacts with a machine.
 * The will stand still for 3 seconds and then the Machines internal timer will be updated.
 */
public class MachineInteractingPlayerState implements iPlayerState, iTimerListener {
    private Machine machine;
    private PlayableCharacter currentPlayer;

    public MachineInteractingPlayerState(iHonestInteractable machine, PlayableCharacter currentPlayer){
        this.currentPlayer = currentPlayer;
        this.machine = (Machine) machine;
        // Play some kind of animation
        Timer timer = new Timer(3, 1000);
        timer.addListener(this);
        timer.start();
    }

    @Override
    public void move(Vector3d vector) {
        currentPlayer.setMoveForce(new Vector3d(0,0,0));
        // Player is stationary during machine interaction
    }

    @Override
    public void timeIsUp() {
        this.machine.updateTimer();
        this.currentPlayer.setState(new NormalPlayerState(currentPlayer));
    }
}
