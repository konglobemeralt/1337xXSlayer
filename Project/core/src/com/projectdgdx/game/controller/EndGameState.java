package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.projectdgdx.game.utils.Config;

import java.util.ArrayList;

/**
 * InGameState controls everything that is in game.
 * Created by Eddie on 2017-04-28.
 */
public class EndGameState implements iGameState {

	private Label endGameMessage;
	private Skin skin;
	private Table table;
	private Stage stage;
	private GameStates ending;
	private TextButton mainMenuButton;
	private InputMultiplexer multiplexer;
	private MenuButtonInputController menuButtonInputController;
	private boolean isDrawn = false;




	public EndGameState(GameStates ending){
		this.ending = ending;
	}


	private void render() {
		stage.act();
		if(!isDrawn){
			stage.draw();
			isDrawn = true;
		}

	}


	/**
	 * Handles user input. // TODO more thorough docs
	 *
	 * @param projectD ProjectD
	 */




	public void update(ProjectD projectD) {
		if(mainMenuButton.isPressed()){
			this.exit(projectD);
			projectD.setState(GameStates.MAINMENU);
		}

		menuButtonInputController.handleInput(projectD.getInpuControllers());


		render();

	}

	public void init(ProjectD projectD) {
		this.table = new Table();
		this.stage = new Stage();
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		skin = new Skin(Gdx.files.internal(Config.UI_SKIN_PATH));


		mainMenuButton = new TextButton("To Main Menu", skin);

		java.util.List<TextButton> buttons = new ArrayList<>();
		buttons.add(mainMenuButton);
		menuButtonInputController = new MenuButtonInputController(buttons);

		//End game strings
		if (this.ending == GameStates.ENDGAME_TIMER){
			endGameMessage = new Label("\nGame Over!\nTime is up, meaning the SUPERVISORS WIN\nand the SABOTEUR LOSE!", skin);
		}else if (this.ending == GameStates.ENDGAME_CAUGHT){
			endGameMessage = new Label("\nGame Over!\nThe saboteur was caught, meaning the SUPERVISORS WIN\nand the SABOTEUR LOSE!", skin);
		}else if (this.ending == GameStates.ENDGAME_STRIKE){
			endGameMessage = new Label("\nGame Over!\nThe workers are striking, meaning the SABOTEUR WIN\nand the SUPERVISORS LOSE!", skin);
		}else if (this.ending == GameStates.ENDGAME_MACHINES){
			endGameMessage = new Label("\nGame Over!\nThe machines are destroyed, meaning the SABOTEUR WIN\nand the SUPERVISORS LOSE!", skin);
		}
		endGameMessage.setFontScale(2);


		this.table.add(endGameMessage);
		this.table.row();
		this.table.add(mainMenuButton).expandX();
		this.stage.addActor(table);

	}


	@Override
	public void start(ProjectD projectD) {
		this.multiplexer = projectD.getMultiplexer();
		multiplexer.addProcessor(stage);// Make the stage consume events
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void stop(ProjectD projectD) {
		projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
		stage.clear();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {

	}

	public void exit(ProjectD projectD) {
		this.stop(projectD);
	}


	private void updateEndGameMessage() {
		endGameMessage.setText("Sätt till ngn cool sträng som beskriver hur det gick i spelet");
	}

}