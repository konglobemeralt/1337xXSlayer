package com.projectdgdx.game.model.gameplay;

import com.projectdgdx.game.model.objectStructure.Entity;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * The Spotlight is used to detect the damage that has been done to a Machine when sabotaged.
 * When a Machine is Sabotaged it will play a sound but it won't show which machine that has
 * been destroyed. When the Spotlight hovers over the destroyed Machine
 */
public class Spotlight extends Entity {
    private List<iSpotlightListener> listeners = new ArrayList();
    private int spotlightRadius;
    private Vector3d color = new Vector3d(1, 0, 0);
    private float intensity = 0;
    public Vector3d getColor() {
        return color;
    }

    public void setColor(Vector3d color) {
        this.color = color;
    }


    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Spotlight(Vector3d position, Vector3d scale, Vector3d rotation, int spotlightRadius, int intensity, String id) {
        super(position, scale, rotation, id);
        this.spotlightRadius = spotlightRadius;
        this.intensity = intensity;

    }

    /*@Override
    public void move(Vector3d vec){                     //TODO This is an alternative move that should be used if spotlight is a physics object.
        if(listeners.size() == 0){
            super.move(vec);
        } else{
            super.move(vec);
            checkListenerDetection(this.getPosition());

        }
    }*/

    @Override
    public void move(Vector3d vec){
        this.getPosition().add(vec);
        if(!(listeners.size() == 0)){
            checkListenerDetection(this.getPosition());
        }
    }

    /**
     * This method is used when the spotlight moves to verify if it hovers over a machine that has been destroyed.
     * If a destroyed Machine is detected it will react to it.
     * @param spotlightPosition , relative position of the Spotlight.
     */
    private void checkListenerDetection(Vector3d spotlightPosition){
        for(iSpotlightListener sl : listeners){
            if(sl.isDetected(spotlightPosition, this.spotlightRadius)){
                sl.detect();
            }
        }
    }

    @Override
    public boolean isColliding(Vector3d vec) {
        return false;
    }

    /**
     * Add Listeners to the Spotlight. The Listeners listen for when the Spotlight hovers over them.
     * @param slListener
     */
    public void addListener(iSpotlightListener slListener){
        listeners.add(slListener);
    }
}
