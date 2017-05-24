package com.projectdgdx.game.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

public class MenuView {
	private Stage stage;
	private Table table;
	private List<Actor> menuItems = new ArrayList<>();
	public void addMenuItems(List<Actor> menuItems) {
		for(Actor menuItem : menuItems) {
			this.menuItems.add(menuItem);
		}
	}

	public void init() {
		stage = new Stage();
		table = new Table();
		for(Actor menuItem : menuItems) {
			table.add(menuItem);
			table.row();
		}
	}

	public void render() {
		stage.act();
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}

}
