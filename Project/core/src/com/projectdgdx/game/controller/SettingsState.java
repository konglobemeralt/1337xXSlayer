package com.projectdgdx.game.controller;

/**
 * SettingsState creates and renders an UIStructure to configure the game settings
 *
 * Created by Jesper on 2017-05-01.
 */

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.libgdx.MenuItemFactory;
import com.projectdgdx.game.model.input.InputModel;
import com.projectdgdx.game.utils.Config;
import com.projectdgdx.game.view.MenuView;
import java.util.List;

public class SettingsState implements iGameState {
     private InputMultiplexer multiplexer;
    private MenuView menuView;
    private MenuItemFactory menuFactory;

    private MenuButtonInputController menuButtonInputController;


    /**
     * update clears the screen and renders the settings menu
     *
     * @param projectD Project
     */
    @Override
    public void update(ProjectD projectD) {
        menuView.render();


        for(InputController inputController : projectD.getInpuControllers()) {
            InputModel inputModel = inputController.getModel();
            //Checks if escape button has been pressed.
            if (inputModel.getMenuButton().isPressed() && inputModel.getMenuButton().getPressedCount() >= 1) {
                projectD.setState(projectD.getLastGameState());
            }
        }

		menuButtonInputController.handleInput(projectD.getInpuControllers());

	}

    /**
     * Initiates the settings menu, creating all the buttons adding listners, and ordering them in a row table.
     *
     * @param projectD Project
     */
    @Override
    public void init(ProjectD projectD) {

    }

    /**
     * Starts the settings menu state, updating the buttons value.
     *
     * @param projectD Project
     */
    @Override
    public void start(ProjectD projectD) {
        menuView = new MenuView();
        menuFactory = new MenuItemFactory();
        multiplexer = projectD.getMultiplexer();

        buildMenu(projectD);
    }

    @Override
    public void stop(ProjectD projectD) {
        projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
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



        menuView.addMenuItems(menuFactory.createLabelSlider(0, 100, Config.SUN_LIGHT_R, "Sun R Slider", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        Slider slider = (Slider) actor;

                        float value = slider.getValue();
                        Config.SUN_LIGHT_R = ((int) value );
                        updateSunRLabel();
                    }

                    /**
                     * Updates the sun R label
                     **/
                    private void updateSunRLabel()
                    {
                       // float value = Config.SUN_LIGHT_R;
//
                       // sunrSlider.setValue(value);
                       // sunrSlider.setAnimateDuration(0.3f);
//
                       // sunrSliderValue.setText(String.valueOf(Config.SUN_LIGHT_R) + " %");
                       // sunrSliderValue.invalidate();
                    }

                }
        ));

        menuView.addMenuItems(menuFactory.createLabel("Settings"));

        menuView.addMenuItems(menuFactory.createLabelSlider(0, 100, Config.SUN_LIGHT_G, "Sun G Slider", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        Slider slider = (Slider) actor;

                        float value = slider.getValue();
                        Config.SUN_LIGHT_G = ((int) value );
                        //updateSunRLabel();
                    }
                }
        ));

        menuView.addMenuItems(menuFactory.createLabelSlider(0, 100, Config.SUN_LIGHT_B, "Sun B Slider", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        Slider slider = (Slider) actor;

                        float value = slider.getValue();
                        Config.SUN_LIGHT_B = ((int) value );
                        //updateSunRLabel();
                    }
                }
        ));

        menuView.addMenuItems(menuFactory.createLabelSlider(0, 100, Config.DISCO_FACTOR, "Disco Factor", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        Slider slider = (Slider) actor;

                        float value = slider.getValue();
                        Config.DISCO_FACTOR = ((int) value );
                        //updateSunRLabel();
                    }
                }
        ));

        menuView.addMenuItems(menuFactory.createLabelSlider(0, 100, Config.CAMERA_FOV, "Camera Fov", new ChangeListener() {
                    public void changed(ChangeEvent event, Actor actor) {
                        Slider slider = (Slider) actor;

                        float value = slider.getValue();
                        Config.CAMERA_FOV = ((int) value );
                        //updateSunRLabel();
                    }
                }
        ));


        menuView.addMenuItems(menuFactory.createLabelCheckBox(true, "Shadow Mapping", new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                        CheckBox box = (CheckBox) actor;
                        Config.SHADOWMAPPING_ENABLED = box.isChecked();
                   }
               }));

        List<Actor> mainMenuButton = menuFactory.createTextButton("Back to Main Menu", new ChangeListener() {
					public void changed(ChangeEvent event, Actor actor) {
						projectD.setState(GameStates.MAINMENU);
					}
				}
		);
        menuView.addMenuItems(mainMenuButton);
        menuView.init(multiplexer);

		menuButtonInputController = new MenuButtonInputController(mainMenuButton);
	}

}