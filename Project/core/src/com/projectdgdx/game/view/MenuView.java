package com.projectdgdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.projectdgdx.game.utils.Config;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.List;

public class MenuView {
	private Stage stage;
	private List<Actor> menuItems = new ArrayList<>();


	/**
	 * Add menuItems to be used for rendering menuView after init
	 * @param menuItems List of menuItems
	 */
	public void addMenuItems(List<Actor> menuItems) {
		for(Actor menuItem : menuItems) {
			this.menuItems.add(menuItem);
		}
	}

	/**
	 * Init menu, will use all menuItems that has been added using addMenuItems
	 */
	public void init(InputMultiplexer multiplexer) {
		stage = new Stage();
		Table table = new Table();
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		for(Actor menuItem : menuItems) {
			table.add(menuItem).expandX().width(Config.MENU_X).height(Config.MENU_Y);
			table.row();
		}
		stage.addActor(table);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);


		;	}

	/**
	 * Render menu
	 */
	public void render() {
		//Clear screen
		Gdx.gl.glClearColor(Config.MENU_DEFAULTBACKGROUND_R,
				Config.MENU_DEFAULTBACKGROUND_G,
				Config.MENU_DEFAULTBACKGROUND_B,
				Config.MENU_DEFAULTBACKGROUND_A);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

	}

	public static int count = 0;

	public void dispose() {
		if(count > 0) {
			System.out.println("uas");
		}
		stage.dispose();
		count++;
	}

}
