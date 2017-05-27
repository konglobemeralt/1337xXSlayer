package com.projectdgdx.game.model.useCases;

import com.projectdgdx.game.model.gameplay.*;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DishonestInteractTest{
    PlayableCharacter character;
    Machine machine;
    List<iDishonestInteractable> dishonests;

    @Before
    public void setup() throws Exception{
        character = new Saboteur(new Vector3d(1,1,1),new Vector3d(1,1,1),new Vector3d(1,1,1), "Supervisor");
        character.setState(new NormalPlayerState(character));
        machine = new Machine(new Vector3d(1,1,1),new Vector3d(1,1,1),new Vector3d(1,1,1), "Machine", new UnusedMachineState());
        dishonests = new ArrayList<>();
    }

    @Test
    public void testDishonestInteraction()throws Exception{
        // Creating some necessary structures for the test
        dishonests.add(machine);

        // Verify that the states of the machine and the character are correct when honest interacting
        // Before interacting
        assertEquals(this.character.getState().getClass().toString(), "class com.projectdgdx.game.model.gameplay.NormalPlayerState");

        // When interacting
        character.dishonestInteract(dishonests);
        assertEquals(this.character.getState().getClass().toString(), "class com.projectdgdx.game.model.gameplay.MachineDestroyingPlayerState");
        assertEquals(this.machine.getState().getClass().toString(), "class com.projectdgdx.game.model.gameplay.UnusedMachineState");
    }
}