package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.libgdx.MenuItemFactory;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.view.MenuView;

import java.util.ArrayList;

/**
 * The EndGameState will occur when the game has ended, enough worker are on strike, the saboteur is caught
 * or enough machines are destroyed. The state will display the message of who wins and that the game
 * is over.
 */
public class EndGameState implements iGameState {

	private String endGameMessage;
	private GameStates ending;
	private MenuButtonInputController menuButtonInputController;
	private SpriteBatch batch;
	Texture background;

	private MenuView menuView;
	private MenuItemFactory menuFactory;

	public EndGameState(GameStates ending){
		this.ending = ending;
	}

	/**
	 * Will render the stage and view the graphical representation of the EndGameState
	 */
	private void render() {
		menuView.render();

	}

	@Override
	public void update(ProjectD projectD) {

		//menuButtonInputController.handleInput(projectD.getInpuControllers());
		render();
	}

	@Override
	public void init(ProjectD projectD) {

	}


	@Override
	public void start(final ProjectD projectD) {
		menuView = new MenuView();
		menuFactory = new MenuItemFactory();



		//End game strings
		if (this.ending == GameStates.ENDGAME_TIMER){
			endGameMessage = "\nGame Over!\nTime is up, meaning the SUPERVISORS WIN\nand the SABOTEUR LOSE!";
		}else if (this.ending == GameStates.ENDGAME_CAUGHT){
			endGameMessage = "\nGame Over!\nThe saboteur was caught, meaning the SUPERVISORS WIN\nand the SABOTEUR LOSE!";
		}else if (this.ending == GameStates.ENDGAME_STRIKE){
			endGameMessage = "\nGame Over!\nThe workers are striking, meaning the SABOTEUR WIN\nand the SUPERVISORS LOSE!";
		}else if (this.ending == GameStates.ENDGAME_MACHINES){
			endGameMessage = "\nGame Over!\nThe machines are destroyed, meaning the SABOTEUR WIN\nand the SUPERVISORS LOSE!";
		}

		batch = new SpriteBatch();

		menuView.addMenuItems(menuFactory.createLabel(endGameMessage));
		menuView.addMenuItems(menuFactory.createTextButton("Back To Main Menu", new ChangeListener() {
					public void changed(ChangeEvent event, Actor actor) {
						projectD.setState(GameStates.MAINMENU);
					}
				}
		));

		menuView.init(projectD.getMultiplexer());
	}

	@Override
	public void stop(ProjectD projectD) {
		projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
		//stage.clear();
		//stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void exit(ProjectD projectD) {
		this.stop(projectD);
	}




}