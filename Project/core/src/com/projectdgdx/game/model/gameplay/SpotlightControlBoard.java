package com.projectdgdx.game.model.gameplay;

import com.projectdgdx.game.model.objectStructure.StaticObject;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.utils.Vector3d;
import com.projectdgdx.game.utils.iTimerListener;

/**
 * THe SpotlightControlBoard is the holder of the Spotlight and the object that all
 * PlayableCharacters gameplay with to enter their InSpotlightPlayerState so that they can move the Spotlight
 * and detect destroyed Machines.
 */
public class SpotlightControlBoard extends StaticObject implements iHonestInteractable, iTimerListener {
    private Spotlight spotlight;

    private Timer spotlightTimer;

    public SpotlightControlBoard(Vector3d position, Vector3d scale, Vector3d rotation, String id, Spotlight spotlight) {
        super(position, scale, rotation, id);
        this.spotlight = spotlight;
        this.spotlight.setColor(new Vector3d(Config.SPOT_LIGHT_R, Config.SPOT_LIGHT_G, Config.SPOT_LIGHT_B));
    }

    @Override
    public void honestInteract(PlayableCharacter player) {
        player.setMoveForce(new Vector3d(0,0,0));
        if (player.getState().getClass().equals(new InSpotlightPlayerState(spotlight).getClass())){
            player.setState(new NormalPlayerState(player));
            updateTimer();
        }else{
            player.setState(new InSpotlightPlayerState(this.spotlight));
            this.spotlight.setIntensity(700);
        }

    }

    public Spotlight getSpotlight() {
        return spotlight;
    }


    public void updateTimer() {
        this.spotlightTimer = new Timer(3, 20);
        this.spotlightTimer.addListener(this);
        this.spotlightTimer.start();
    }



    public void updateSpotlight(){
        if(spotlightTimer != null){
            if(spotlightTimer.getTimerValue() != 0){
            float timerValue = this.spotlightTimer.getTimerValue();
            float calcVal = (((timerValue) * (700 - 300)) / (5)) + 300;
            this.spotlight.setIntensity(calcVal);
            }
        }


    }

    @Override
    public void timeIsUp() {

    }


}
