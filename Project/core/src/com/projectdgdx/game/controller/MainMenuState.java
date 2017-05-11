package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;
import com.projectdgdx.game.model.InputModel;
import com.projectdgdx.game.utils.Vector3d;

import static com.badlogic.gdx.Gdx.gl20;

/**
 * Main menu state displays and handles the main menu
 * Created by Eddie on 2017-04-28.
 */
public class MainMenuState implements GameState {

    private Skin skin;
    private Stage stage;
    private TextButton newGameButton;
    private TextButton settingsButton;
    private TextButton exitButton;

    private InputMultiplexer multiplexer;
    private Label mainMenuHeading;
    private Table table;
    private int controllerPosition = 0;

    /**
     * createBasicSkin reads an internalGUI file and creates a skin
     *
     * @param path Path to the GUIskin file
     * @return A new skin
     */
    private Skin createBasicSkin(String path){

        return new Skin(Gdx.files.internal(path));
       // //Create a font
       // BitmapFont font = new BitmapFont();
       // skin = new Skin();
       // skin.add("default", font);
//
       // //Create a texture
       // Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
       // pixmap.setColor(Color.WHITE);
       // pixmap.fill();
       // skin.add("background",new Texture(pixmap));
//
       // //Create a button style
       // TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
       // textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
       // textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
       // textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
       // textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
       // textButtonStyle.font = skin.getFont("default");
       // skin.add("default", textButtonStyle);
    }

    @Override
    public void update(ProjectD projectD) {
        Gdx.gl.glClearColor(Config.MENU_DEFAULTBACKGROUND_R,
                Config.MENU_DEFAULTBACKGROUND_G,
                Config.MENU_DEFAULTBACKGROUND_B,
                Config.MENU_DEFAULTBACKGROUND_A);
        Gdx.gl.glClear(gl20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if(newGameButton.isPressed()){
            this.exit(projectD);
            projectD.setState(GameStates.INGAME);
        }
        else if(settingsButton.isPressed()){
            this.exit(projectD);
            projectD.setState(GameStates.SETTINGS);
        }

        else if(exitButton.isPressed()){
            this.exit(projectD);
            Gdx.app.exit();
        }


        //TODO more logic for handeling movement of controllers or keyboards in menus
        for(InputController inputController : projectD.getInpuControllers()) {
            InputModel inputModel = inputController.getModel();

            float controllerValue = inputModel.getLeftStick().z;
            if(controllerValue != 0) {
                if(controllerValue < 0 && controllerPosition > 0) {
                    controllerPosition--;
                }else if(controllerValue < 0 && controllerPosition < 4) {
                    controllerPosition++;
                }
//                switch (controllerValue) {
//                }
            }

            if(inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1){
                this.stop(projectD);
                projectD.setState(GameStates.SETTINGS);
            }
        }
    }

    @Override
    public void init(ProjectD projectD) {
    }

    @Override
    public void start(ProjectD projectD) {
        this.stage = new Stage();

        skin = createBasicSkin(Config.UI_SKIN_PATH);

        mainMenuHeading = new Label("Project D", skin);

        newGameButton = new TextButton("New game", skin); // Use the initialized skin

        settingsButton = new TextButton("Settings", skin);

        exitButton = new TextButton("Exit Game", skin);

        table = new Table();

        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table.add(mainMenuHeading).expandX().height(60);
        table.row();
        table.add(newGameButton).expandX().width(600).height(60);
        table.row();
        table.add(settingsButton).expandX().width(600).height(60);
        table.row();
        table.add(exitButton).expandX().width(600).height(60);
        table.row();

        stage.addActor(table);

        this.multiplexer = projectD.getMultiplexer();
        multiplexer.addProcessor(stage);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void stop(ProjectD projectD) {
        stage.dispose();
    }

    @Override
    public void exit(ProjectD projectD) {
        //stage.dispose();
    }


}
