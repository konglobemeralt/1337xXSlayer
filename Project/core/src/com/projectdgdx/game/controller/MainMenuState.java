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

    private MenuButtonInputController menuButtonInputController;

    private List<String> levelList = new ArrayList<String>();


    private MenuView menuView;
    private MenuItemFactory menuFactory;

    @Override
    public void init(ProjectD projectD) {
        File f = new File("map/");
        levelList = new ArrayList<String>(Arrays.asList(f.list()));
    }

    @Override
    public void start(ProjectD projectD) {
        menuView = new MenuView();
        menuFactory = new MenuItemFactory();


        buildMenu(projectD);

        //Set to first selection as default



    }

    @Override
    public void update(ProjectD projectD) {

        menuView.render();
        menuButtonInputController.handleInput(projectD.getInpuControllers());

        //Handle inputs
        //menuButtonInputController.handleInput(projectD.getInpuControllers());

    }



    @Override
    public void stop(ProjectD projectD) {
        menuView.dispose();
    }

    @Override
    public void resize(int width, int height) {
        if(menuView != null) {
            menuView.resize(width, height);
        }
    }

    @Override
    public void exit(ProjectD projectD) {

    }


    private void buildMenu(final ProjectD projectD){

        //Add buttons in screen order
        //
        List<Actor> buttons = new ArrayList<>();

        menuView.addMenuItems(menuFactory.createLabel("Main Menu"));
        List<Actor> newGameButton = menuFactory.createTextButton("New Game", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        projectD.resetState(GameStates.INGAME);
                        projectD.setState(GameStates.INGAME);
                    }
                }
        );
        menuView.addMenuItems(newGameButton);

        menuView.addMenuItems(menuFactory.creadeDropDown("Level Select", levelList, new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        Config.LEVEL_IN_PLAY = ((Label)(((SelectBox) actor).getSelected())).getText().toString();

                    }
                }
        ));

        List<Actor> settingsButton = menuFactory.createTextButton("Settings", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        projectD.setState(GameStates.SETTINGS);
                    }
                }
        );
        menuView.addMenuItems(settingsButton);


        List<Actor> exitButton = menuFactory.createTextButton("Exit", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        System.out.print("New");
                        Gdx.app.exit();
                    }
                }
        );
        menuView.addMenuItems(exitButton);

        buttons.add(newGameButton.get(0));
        buttons.add(settingsButton.get(0));
        buttons.add(exitButton.get(0));
        menuButtonInputController = new MenuButtonInputController(buttons);




        menuView.init(projectD.getMultiplexer());


       // //Set up the SelectionBox with content
       // Object[] blob = new Object[levelList.size()];
       // for(int i = 0; i < levelList.size(); i++){
       //     blob[i] = new Label(levelList.get(i), skin);
       // }
       // levelSelection = new SelectBox<Object>(skin);
       // levelSelection.setItems(blob);
//
//
       //
//
       // //add listeners
       // levelSelection.addListener(new ChangeListener() {
       //     @Override
       //     public void changed(ChangeEvent event, Actor actor) {
       //         System.out.println(((Label) levelSelection.getSelected()).getText());
       //         Config.LEVEL_IN_PLAY = ((Label) levelSelection.getSelected()).getText().toString();
       //     }
       // });
    }


}