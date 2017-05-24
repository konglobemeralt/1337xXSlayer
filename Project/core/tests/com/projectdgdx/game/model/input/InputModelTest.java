package com.projectdgdx.game.model.input;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2017-05-24.
 */
public class InputModelTest {
	@Test
	public void getLeftStick() throws Exception {
		InputModel inputModel = new InputModel();
		inputModel.getLeftStick().x = 1;
		inputModel.getLeftStick().z = 2;
		assertTrue(inputModel.getLeftStick().x == 1 && inputModel.getLeftStick().z == 2);
		inputModel.resetButtonCounts();
		assertTrue(inputModel.getLeftStick().x == 1 && inputModel.getLeftStick().z == 2);
	}

	@Test
	public void setButtonA() throws Exception {
		InputModel inputModel = new InputModel();
		assertFalse(inputModel.getButtonA().isPressed());
		inputModel.setButtonA(true);
		assertTrue(inputModel.getButtonA().isPressed());
	}

	@Test
	public void setButtonB() throws Exception {
		InputModel inputModel = new InputModel();
		assertFalse(inputModel.getButtonB().isPressed());
		inputModel.setButtonB(true);
		assertTrue(inputModel.getButtonB().isPressed());
	}

	@Test
	public void setButtonX() throws Exception {
		InputModel inputModel = new InputModel();
		assertFalse(inputModel.getButtonX().isPressed());
		inputModel.setButtonX(true);
		assertTrue(inputModel.getButtonX().isPressed());
	}

	@Test
	public void setButtonY() throws Exception {
		InputModel inputModel = new InputModel();
		assertFalse(inputModel.getButtonY().isPressed());
		inputModel.setButtonY(true);
		assertTrue(inputModel.getButtonY().isPressed());
	}

	@Test
	public void setMenuButton() throws Exception {
		InputModel inputModel = new InputModel();
		assertFalse(inputModel.getMenuButton().isPressed());
		inputModel.setMenuButton(true);
		assertTrue(inputModel.getMenuButton().isPressed());
	}

	@Test
	public void resetButtonCounts() throws Exception {
		InputModel inputModel = new InputModel();
		inputModel.setButtonA(true);
		inputModel.setButtonB(true);
		inputModel.setButtonX(true);
		inputModel.setButtonY(true);
		inputModel.setMenuButton(true);

		assertTrue(inputModel.getButtonA().getPressedCount() == 1);
		assertTrue(inputModel.getButtonB().getPressedCount() == 1);
		assertTrue(inputModel.getButtonX().getPressedCount() == 1);
		assertTrue(inputModel.getButtonY().getPressedCount() == 1);
		assertTrue(inputModel.getMenuButton().getPressedCount() == 1);

		inputModel.resetButtonCounts();

		assertTrue(inputModel.getButtonA().getPressedCount() == 0);
		assertTrue(inputModel.getButtonB().getPressedCount() == 0);
		assertTrue(inputModel.getButtonX().getPressedCount() == 0);
		assertTrue(inputModel.getButtonY().getPressedCount() == 0);
		assertTrue(inputModel.getMenuButton().getPressedCount() == 0);

	}

}