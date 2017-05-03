package com.projectdgdx.game.model;

/**
 * Created by Hampus on 2017-05-03.
 */
public class PressedData {
	private boolean pressed = false;
	private int pressedCount = 0;
	public boolean isPressed() {
		return pressed;
	}
	public int getPressedCount() {
		return pressedCount;
	}
	public void setPressed(boolean isPressed) {
		if(isPressed) {
			pressedCount++;
		}
		pressed = true;
	}
	public void resetCount() {
		pressedCount = 0;
	}
}
