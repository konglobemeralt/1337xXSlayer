package com.projectdgdx.game.model.StaticInteractable;

import com.projectdgdx.game.model.MachineStates.DestroyedMachineState;
import com.projectdgdx.game.model.MachineStates.iMachineState;
import com.projectdgdx.game.model.ModelStructure.StaticObject;
import com.projectdgdx.game.model.Playables.PlayableCharacter;
import com.projectdgdx.game.model.iDishonestInteractable;
import com.projectdgdx.game.model.iHonestInteractable;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;

import java.util.List;

/**
 * The Machine class is the interactable machine object that is a main part of the game logic.
 * The Supervisors quest is to save the Machines from getting destroyed by the Saboteur.
 */

public class Machine extends StaticObject implements iHonestInteractable, iDishonestInteractable, iTimerListener {

    private Timer machineCounter;
    private Spotlight spot;
    private List<Spotlight> bigDetectingSpotlights;

    protected iMachineState state;

    public Machine(Vector3d position, Vector3d scale, Vector3d rotation, String id, iMachineState state) {
        super(position, scale, rotation, id);
        this.state = state;

        this.spot = new Spotlight(new Vector3d(position.x, 30, position.z),
                new Vector3d(1, 1, 1),
                new Vector3d(1, 1, 1), 5, 100, "spotlight.machine") ;
        this.spot.setColor(new Vector3d(0, 1, 0));

        this.machineCounter = new Timer(30, 1000);
        machineCounter.addListener(this);
        this.machineCounter.start();
    }

    public Spotlight getSpotLight() {
        return spot;
    }

    public void setSpotLight(Spotlight spot) {
        this.spot = spot;
    }

    public void setBigDetectingSpotlights(List<Spotlight> bigDetectingSpotlights) {
        this.bigDetectingSpotlights = bigDetectingSpotlights;
    }

    public List<Spotlight> getBigDetectingSpotlights() {
        return bigDetectingSpotlights;
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setMoveForce(new Vector3d(0,0,0));
        state.honestInteract(player, this);
    }

    @Override
    public void dishonestInteract(PlayableCharacter player) {
        state.dishonestInteract(player, this);
    }

    @Override
    public void setState(iMachineState newState) {
        this.state = newState;
    }

    public Timer getMachineCounter() {
        return machineCounter;
    }

    @Override
    public void timeIsUp() {
        this.setState(new DestroyedMachineState(this));
        // TODO Update machine to display new model
    }

    /**
     * Thi method is used to update the internal timer of the machine. If the timer goes down to 0
     * the machine will set itself to destroyed.
     */
    public void updateTimer() {
        this.machineCounter.setTimerValue(30);
    }


    public void updateSpotlight(){
        float timerValue = this.machineCounter.getTimerValue();
        float calcVal = (((timerValue ) * (1)) / (30));
        this.spot.setColor(new Vector3d(1-calcVal, calcVal * 1.5f, 0));

        if(timerValue <= Config.MACHINELIGHT_BLINK_TIME){
            if(timerValue%2==0) {
                this.spot.setIntensity(Config.MACHINELIGHT_MAX_INTENSITY);
            }
            else{
                this.spot.setIntensity(Config.MACHINELIGHT_BASE_INTENSITY);
            }
        }

    }

}
