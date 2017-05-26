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
import com.projectdgdx.game.utils.Config;

import java.util.ArrayList;

/**
 * The EndGameState will occur when the game has ended, enough worker are on strike, the saboteur is caught
 * or enough machines are destroyed. The state will display the message of who wins and that the game
 * is over.
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
	private SpriteBatch batch;
	Texture background;

	public EndGameState(GameStates ending){
		this.ending = ending;
	}

	/**
	 * Will render the stage and view the graphical representation of the EndGameState
	 */
	private void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
				(Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));


		stage.act();
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		stage.draw();

	}

	@Override
	public void update(ProjectD projectD) {
		if(mainMenuButton.isPressed()){
			this.exit(projectD);
			projectD.setState(GameStates.MAINMENU);
		}

		menuButtonInputController.handleInput(projectD.getInpuControllers());
		render();
	}

	@Override
	public void init(ProjectD projectD) {
		this.table = new Table();
		this.stage = new Stage();
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		skin = new Skin(Gdx.files.internal(Config.UI_SKIN_PATH));

		background = new Texture(Gdx.files.getLocalStoragePath() + "prettyScreenshot.png");
		mainMenuButton = new TextButton("To Main Menu", skin);

		java.util.List<Actor> buttons = new ArrayList<>();
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
		endGameMessage.setFontScale(1);

		batch = new SpriteBatch();

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