package com.projectdgdx.game.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.model.input.InputModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MenuButtonInputController makes it easy to add user inputs to menus.
 * Give it a list of buttons in the correct order and it will now be easy to move around with both keyboard and xbox controllers.
 * Created by Hampus on 2017-05-16.
 */
public class MenuButtonInputController {

	private List<Actor> buttons;
	private Map<InputController, Float> lastInputValues = new HashMap<>();
	private int controllerPosition = 0;

	public MenuButtonInputController(List<Actor> buttons) {
		this.buttons = buttons;
	}

	/**
	 * handleInput will handle inputs from a list of controllers. It will use it's internal list of buttons
	 * that is sent through the constructor. It passes a pressed button event to the button when a controller gets pressed.
	 * @param inputControllers A list of InputControllers that should be used for looking for inputs.
	 */
	public void handleInput(List<InputController> inputControllers) {

		//Reset color for currently selected button
		buttons.get(controllerPosition).setColor(Color.LIGHT_GRAY);

		for(InputController inputController : inputControllers) {
			InputModel inputModel = inputController.getModel();

			if(!lastInputValues.containsKey(inputController)) {
				lastInputValues.put(inputController, 0f);
			}
			float lastInputValue = lastInputValues.get(inputController);
			float controllerValue = inputModel.getLeftStick().z;

			if(controllerValue != 0 && convertToMaxMin(controllerValue) != convertToMaxMin(lastInputValue)) {

				if(controllerValue < 0 && controllerPosition > 0) {
					controllerPosition--;
				}else if(controllerValue > 0 && controllerPosition < buttons.size() - 1) {
					controllerPosition++;
				}
//				System.out.println(controllerPosition);
				buttons.get(controllerPosition).setColor(Color.GREEN);
			}

			if(inputModel.getButtonA().getPressedCount() > 0) {

				//Press button
				InputEvent touchDown = new InputEvent();
				touchDown.setType(InputEvent.Type.touchDown);
				buttons.get(controllerPosition).fire(touchDown);

				System.out.println("TKSOAKODSOAK");
				ChangeListener.ChangeEvent changeEvent = new ChangeListener.ChangeEvent();
				buttons.get(controllerPosition).fire(changeEvent);

				//Unpress button
				InputEvent touchUp = new InputEvent();
				touchUp.setType(InputEvent.Type.touchUp);
				buttons.get(controllerPosition).fire(touchUp);
			}

			inputModel.resetButtonCounts();
			lastInputValues.replace(inputController, convertToMaxMin(controllerValue));
		}

		//Set new button color
		buttons.get(controllerPosition).setColor(Color.GREEN);
	}

	/**
	 *
	 * @param value value to convert.
	 * @return Return -1, 0, -1 depending on value.
	 */
	private float convertToMaxMin(float value) {
		if(value == 0) {
			return 0f;
		}
		if(value < 0) {
			return -1f;
		}else {
			return 1f;
		}
	}
}
