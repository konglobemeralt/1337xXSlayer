package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.iTimerListener;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Hampus on 2017-04-03.
 */

public class Machine extends StaticObject implements iHonestInteractable, iDishonestInteractable, iTimerListener {

    private Timer machineCounter;

    protected iMachineState state;

    public Machine(Vector3d position, Vector3d scale, Vector3d rotation, String id) {
        super(position, scale, rotation, id);
        this.state = new UnusedMachineState();

        this.machineCounter = new Timer(30, 1000);
        machineCounter.addListener(this);
    }

    @Override
    public void honestInteract(PlayableCharacter player) { //TODO  test commit
        state.honestInteract(player, this);
    }


    public void dishonestInteract(PlayableCharacter player) {
        state.dishonestInteract(player, this);
    }

    @Override
    public void setState(iMachineState newState) {
        this.state = newState;
    }

    @Override
    public void timeIsUp() {
        this.setState(new DestroyedMachineState(this.getPosition()));
    }

    public void updateTimer() {
        this.machineCounter.setTimerValue(30);
    }
}
