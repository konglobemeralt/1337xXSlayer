package com.projectdgdx.game.model.input;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2017-05-24.
 */
public class PressedDataTest {
	@Test
	public void isPressed() throws Exception {
		PressedData pressedData = new PressedData();
		pressedData.setPressed(true);
		assertTrue(pressedData.isPressed());
		pressedData.setPressed(false);
		assertFalse(pressedData.isPressed());
	}

	@Test
	public void getPressedCount() throws Exception {
		PressedData pressedData = new PressedData();
		assertTrue(pressedData.getPressedCount() == 0);
		pressedData.setPressed(true);
		assertTrue(pressedData.getPressedCount() == 1);
		pressedData.setPressed(false);
		assertTrue(pressedData.getPressedCount() == 1);
	}

	@Test
	public void setPressed() throws Exception {
		PressedData pressedData = new PressedData();
		pressedData.setPressed(true);
		assertTrue(pressedData.isPressed());
		pressedData.setPressed(false);
		assertFalse(pressedData.isPressed());
	}

	@Test
	public void resetCount() throws Exception {
		PressedData pressedData = new PressedData();
		pressedData.setPressed(true);
		assertTrue(pressedData.getPressedCount() == 1);
		pressedData.resetCount();
		assertTrue(pressedData.getPressedCount() == 0);
	}

}