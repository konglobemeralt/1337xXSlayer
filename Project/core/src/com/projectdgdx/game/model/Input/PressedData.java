package com.projectdgdx.game.model.Input;

/**
 * PressData contains information about the current state of buttons
 */
public class PressedData {
	private boolean pressed = false;
	private int pressedCount = 0;

	/**
	 *
	 * @return True if button currently is pressed
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 *
	 * @return Amount of down presses since last reset
	 */
	public int getPressedCount() {
		return pressedCount;
	}

	/**
	 * Set if button is pressed or not
	 * @param isPressed State of button, true=pressed, false=notpressed
	 */
	public void setPressed(boolean isPressed) {
		if(isPressed) {
			pressedCount++;
		}
		pressed = true;
	}

	/**
	 * Reset pressedCount
	 */
	public void resetCount() {
		pressedCount = 0;
	}
}
