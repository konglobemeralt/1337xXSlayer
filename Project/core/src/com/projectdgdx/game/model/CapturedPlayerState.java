package com.projectdgdx.game.model;

import com.projectdgdx.game.utils.Timer;
import com.projectdgdx.game.utils.iTimerListener;
import com.projectdgdx.game.utils.Vector3d;

/**
 * Created by Eddie on 2017-05-09.
 */
public class CapturedPlayerState implements iPlayerState, iTimerListener {

    PlayableCharacter player;

    public CapturedPlayerState(PlayableCharacter player){
        this.player = player;

        Timer capturedTime = new Timer(3, 1000);
        capturedTime.addListener(this);
    }

    @Override
    public void move(Vector3d vector) {
        // Empty since the supervisors are "arguing" about that one caught the other and therefore stand still
    }

    @Override
    public void timeIsUp() {
        this.player.setState(new NormalPlayerState());
    }
}
