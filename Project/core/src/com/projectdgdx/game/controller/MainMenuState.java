package com.projectdgdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.libgdx.MenuItemFactory;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.view.MenuView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main menu state displays and handles the main menu
 * Created by Eddie on 2017-04-28.
 */
public class MainMenuState implements iGameState {

    private Skin skin;
    private Stage stage;
    private TextButton newGameButton;
    private TextButton settingsButton;
    private TextButton exitButton;

    private MenuButtonInputController menuButtonInputController;

    private SelectBox<Object> levelSelection;
    private List<String> levelList = new ArrayList<String>();


    private InputMultiplexer multiplexer;
    private Label mainMenuHeading;
    private Table table;

    private MenuView menuView;
    private MenuItemFactory menuFactory;

    @Override
    public void init(ProjectD projectD) {
        File f = new File("map/");
        levelList = new ArrayList<String>(Arrays.asList(f.list()));

        menuView = new MenuView();
        menuFactory = new MenuItemFactory();
    }

    @Override
    public void start(ProjectD projectD) {
        buildMenu();

        //Set to first selection as default
        Config.LEVEL_IN_PLAY = ((Label) levelSelection.getSelected()).getText().toString();

        this.multiplexer = projectD.getMultiplexer();
        multiplexer.addProcessor(stage);// Make the stage consume events

        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void update(ProjectD projectD) {

        menuView.render();

       // if(newGameButton.isPressed()){
       //     this.exit(projectD);
       //     projectD.resetState(GameStates.INGAME);
       //     projectD.setState(GameStates.INGAME);
       // } else if(settingsButton.isPressed()){
       //     this.exit(projectD);
       //     projectD.setState(GameStates.SETTINGS);
       // } else if(exitButton.isPressed()){
       //     this.exit(projectD);
       //     Gdx.app.exit();
       // }

        //Handle inputs
        menuButtonInputController.handleInput(projectD.getInpuControllers());

    }



    @Override
    public void stop(ProjectD projectD) {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
    }

    @Override
    public void exit(ProjectD projectD) {
        //stage.dispose();
    }

    private void buildMenu(){
        this.stage = new Stage();
        skin = createBasicSkin(Config.UI_SKIN_PATH);


        //Add buttons in screen order
        //

      menuView.addMenuItems(menuFactory.createTextButton("New Game", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        System.out.print("New");
                        //projectD.resetState(GameStates.INGAME);
                        //projectD.setState(GameStates.INGAME);
                    }
                }
        ));

        menuView.addMenuItems(menuFactory.createTextButton("Settings", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        System.out.print("Settings");
                        //projectD.setState(GameStates.SETTINGS);
                    }
                }
        ));

        menuView.addMenuItems(menuFactory.createTextButton("Exit", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        System.out.print("New");
                        Gdx.app.exit();
                    }
                }
        ));




        //Set up the SelectionBox with content
        Object[] blob = new Object[levelList.size()];
        for(int i = 0; i < levelList.size(); i++){
            blob[i] = new Label(levelList.get(i), skin);
        }
        levelSelection = new SelectBox<Object>(skin);
        levelSelection.setItems(blob);


        menuView.init();

        //add listeners
        levelSelection.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(((Label) levelSelection.getSelected()).getText());
                Config.LEVEL_IN_PLAY = ((Label) levelSelection.getSelected()).getText().toString();
            }
        });
    }

    /**
     * createBasicSkin reads an internalGUI file and creates a skin
     *
     * @param path Path to the GUIskin file
     * @return A new skin
     */
    private Skin createBasicSkin(String path){
        return new Skin(Gdx.files.internal(path));
    }
}