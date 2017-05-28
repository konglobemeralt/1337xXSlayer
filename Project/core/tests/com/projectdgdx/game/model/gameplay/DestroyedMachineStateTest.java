package com.projectdgdx.game.model.gameplay;

import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eddie on 2017-05-27.
 */
public class DestroyedMachineStateTest {
    DestroyedMachineState destState;
    Machine machine;
    PlayableCharacter player;

    @Before
    public void setup(){
        machine = new Machine(new Vector3d(1,1,1),new Vector3d(1,1,1),new Vector3d(1,1,1), "Machine", new UnusedMachineState());
        destState = new DestroyedMachineState(machine);
        player = new Supervisor(new Vector3d(1,1,1),new Vector3d(1,1,1),new Vector3d(1,1,1), "Player");
    }

    @Test
    public void testHonestInteract(){
        assertEquals(Config.MACHINE_TIMER, machine.getMachineCounter().getTimerValue());
        destState.honestInteract(player, machine);
        assertEquals(0, machine.getMachineCounter().getTimerValue());
    }

    @Test
    public void testDetection(){
        assertTrue(destState.isDetected(new Vector3d(2,1,3), 10));
        assertFalse(destState.isDetected(new Vector3d(2,1,3), 1));

        // Verify that the DestroyedMachineStates is detected.
        assertEquals(Config.MACHINE_TIMER, machine.getMachineCounter().getTimerValue());
        assertFalse(destState.isDetected());

        destState.detect();
        assertTrue(destState.isDetected());
        assertEquals(0, machine.getMachineCounter().getTimerValue());


    }
}
