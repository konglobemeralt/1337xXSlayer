package com.projectdgdx.game.model;

/**
 * The EventListener listens to the EventSender and react on the event(s) that it sends.
 */
public interface iEventListener {

    /**
     * React to the sent event from the Model.
     * @param event , the event sent from the model.
     */
    void reactToEvent(Events event);
}
