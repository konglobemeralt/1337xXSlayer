package com.projectdgdx.game.model.interact;

import com.projectdgdx.game.utils.Vector3d;

/**
 * When the spotlight hovers around it acn detect things. All the these things that the spotlight should
 * detect needs ti implement this interface and be added as a listener to the spotlight.
 */
public interface iSpotlightListener {
    /**
     * Verifies if the objected listening to the Spotlight has been detected by the spotlight.
     * @param spotlightPos , the current position of the Spotlight.
     * @param radius . the radius of the Spotlight.
     * @return boolean, true if it is in the Spotlights radius else false.
     */
    boolean isDetected(Vector3d spotlightPos, int radius);

    /**
     * This method determines how the object should react when it is detected.
     */
    void detect();
}
