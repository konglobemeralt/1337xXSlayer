package com.projectdgdx.game.model.PlayerStates;

import com.projectdgdx.game.model.PlayableCharacter;
import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.iTimerListener;
import com.projectdgdx.game.utils.Vector3d;

/**
 * This state is used when a Supervisor catch another Supervisor. The Supervisors will stand still
 * and "argue" for 3 seconds before they both can move again.
 */
public class CapturedPlayerState implements iPlayerState, iTimerListener {

    PlayableCharacter player;

    public CapturedPlayerState(PlayableCharacter player){ //TODO Should change catcher to stay still
        System.out.println("Captured at " + player.getPosition().toString());
        this.player = player;

        Timer capturedTime = new Timer(3, 1000);
        capturedTime.addListener(this);
        capturedTime.start();
    }

    @Override
    public void move(Vector3d vector) {
        // Empty since the supervisors are "arguing" about that one caught the other and therefore stand still
        this.player.setMoveForce(new Vector3d(0,0,0));
    }

    @Override
    public void timeIsUp() {
        this.player.setState(new NormalPlayerState(player));
    }
}
