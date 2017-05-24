package com.projectdgdx.game.libgdx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.utils.Config;

import java.util.ArrayList;
import java.util.List;


public class MenuItemFactory {

	private static Skin skin = new Skin(Gdx.files.internal(Config.UI_SKIN_PATH));
	public static List<Actor> createLabelSlider(float min, float max, float startValue, String title, ChangeListener changeListener) {
		List<Actor> list = new ArrayList<>();
		list.add(new Label(title, skin));
		Slider slider = new Slider(min, max, 1, false, skin);
		slider.addListener(changeListener);
		list.add(slider);
		return list;
	}

	public static List<Actor> createLabelCheckBox(float min, float max, float startValue, String title, ChangeListener changeListener) {
		List<Actor> list = new ArrayList<>();
		list.add(new Label(title, skin));
		CheckBox checkBox = new CheckBox("", skin);
		checkBox.addListener(changeListener);
		list.add(checkBox);
		return list;
	}

	public static List<Actor> createTextButton(String title, ChangeListener changeListener) {
		List<Actor> list = new ArrayList<>();
		TextButton button = new TextButton(title, skin);
		button.addListener(changeListener);
		list.add(button);
		return list;
	}
}
