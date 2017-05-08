package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.TimerListener;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Emil Jansson on 2017-05-04.
 */
public class MachineInteractingPlayerState implements PlayerState, TimerListener {
    private HonestInteractable machine;
    private PlayableCharacter currentPlayer;

    MachineInteractingPlayerState(HonestInteractable machine, PlayableCharacter currentPlayer){
        this.currentPlayer = currentPlayer;
        this.machine = machine;
        Timer timer = new Timer(5, 1000);
        timer.addListener(this);
    }

    @Override
    public void move(Vector3d vector) {
        // Player is stationary during machine interaction
    }

    @Override
    public void timeIsUp() {
        this.currentPlayer.setState(new NormalPlayerState());
    }
}
