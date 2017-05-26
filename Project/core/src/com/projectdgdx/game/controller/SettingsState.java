package com.projectdgdx.game.controller;

/**
 * SettingsState creates and renders an UIStructure to configure the game settings
 *
 * Created by Jesper on 2017-05-01.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.utils.Config;

import java.util.ArrayList;

public class SettingsState implements iGameState {

    private Skin skin;
    private Stage stage;

    private Label shadowMappingLabel;
    private CheckBox shadowMapCheckbox;

    private TextButton mainMenuButton;
    private InputMultiplexer multiplexer;

    private Slider fovSlider;
    private Label fovValueLabel;
    private Label fovLabel;

    private Slider aaSlider;
    private Label aaLabel;
    private Label aaValueLabel;

    private Slider discoSlider;
    private Label disoLabel;
    private Label discoValueLabel;

    private Slider sunrSlider;
    private Label sunrSliderLabel;
    private Label sunrSliderValue;
    private Slider sungSlider;
    private Label sungSliderLabel;
    private Label sungSliderValue;
    private Slider sunbSlider;
    private Label sunbSliderLabel;
    private Label sunbSliderValue;

    private Label settingsHeading;

    private Label moveSpeedLabel;
    private TextField moveSpeedIn;

    private Table table;

    private MenuButtonInputController menuButtonInputController;


    /**
     * update clears the screen and renders the settings menu
     *
     * @param projectD Project
     */
    @Override
    public void update(ProjectD projectD) {
        Gdx.gl.glClearColor(Config.MENU_DEFAULTBACKGROUND_R,
                Config.MENU_DEFAULTBACKGROUND_G,
                Config.MENU_DEFAULTBACKGROUND_B,
                Config.MENU_DEFAULTBACKGROUND_A);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        for(InputController inputController : projectD.getInpuControllers()) {
            if(inputController.getModel().getMenuButton().getPressedCount() > 0) {
                projectD.setState(GameStates.INGAME);
            }
        }

        menuButtonInputController.handleInput(projectD.getInpuControllers());

        if(mainMenuButton.isPressed()){
            this.exit(projectD);
            projectD.setState(GameStates.MAINMENU);
        }
    }

    /**
     * Initiates the settings menu, creating all the buttons adding listners, and ordering them in a row table.
     *
     * @param projectD Project
     */
    @Override
    public void init(ProjectD projectD) {

        this.stage = new Stage();

    }

    /**
     * Starts the settings menu state, updating the buttons value.
     *
     * @param projectD Project
     */
    @Override
    public void start(ProjectD projectD) {
        createMenu();

        this.multiplexer = projectD.getMultiplexer();
        multiplexer.addProcessor(stage);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);

        updateDiscoLabel();
        updateFOVlabel();
        updateAAlabel();

        updateSunRLabel();
        updateSunGLabel();
        updateSunBLabel();
    }

    @Override
    public void stop(ProjectD projectD) {
        projectD.getInpuControllers().get(0).getModel().resetButtonCounts();
        stage.clear();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void exit(ProjectD projectD) {
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

    /**
     * Updates the FOV value label
     **/
    private void updateFOVlabel()
    {
        float value = Config.CAMERA_FOV;

        fovSlider.setValue(value);
        fovSlider.setAnimateDuration(0.3f);

        fovValueLabel.setText(String.valueOf(Config.CAMERA_FOV));
        fovValueLabel.invalidate();
    }

    /**
     * Updates the disco factor value label
     **/
    private void updateDiscoLabel()
    {
        float value = Config.DISCO_FACTOR;

        discoSlider.setValue(value);
        discoSlider.setAnimateDuration(0.3f);

        discoValueLabel.setText(String.valueOf(Config.DISCO_FACTOR) + " %");
        discoValueLabel.invalidate();
    }

    /**
     * Updates the AAfactor value label
     **/
    private void updateAAlabel()
    {
        float value = Config.AA_SAMPLES;

        aaSlider.setValue(value);
        aaSlider.setAnimateDuration(0.3f);

        aaValueLabel.setText(String.valueOf(Config.AA_SAMPLES));
        aaValueLabel.invalidate();
    }

    /**
     * Updates the sun R label
     **/
    private void updateSunRLabel()
    {
        float value = Config.SUN_LIGHT_R;

        sunrSlider.setValue(value);
        sunrSlider.setAnimateDuration(0.3f);

        sunrSliderValue.setText(String.valueOf(Config.SUN_LIGHT_R) + " %");
        sunrSliderValue.invalidate();
    }

    /**
     * Updates the sun G label
     **/
    private void updateSunGLabel()
    {
        float value = Config.SUN_LIGHT_G;

        sungSlider.setValue(value);
        sungSlider.setAnimateDuration(0.3f);

        sungSliderValue.setText(String.valueOf(Config.SUN_LIGHT_G)+ " %");
        sungSliderValue.invalidate();
    }

    /**
     * Updates the sun B label
     **/
    private void updateSunBLabel()
    {
        float value = Config.SUN_LIGHT_B;

        sunbSlider.setValue(value);
        sunbSlider.setAnimateDuration(0.3f);

        sunbSliderValue.setText(String.valueOf(Config.SUN_LIGHT_B)+ " %");
        sunbSliderValue.invalidate();
    }

    private void createMenu(){
        this.stage = new Stage();

        skin = createBasicSkin(Config.UI_SKIN_PATH);

        settingsHeading = new Label("Settings Menu", skin);

        mainMenuButton = new TextButton("To Main Menu", skin);

        java.util.List<TextButton> buttons = new ArrayList<>();
        buttons.add(mainMenuButton);
       // menuButtonInputController = new MenuButtonInputController(buttons);

        /** Field of view slider  **/
        fovSlider = new Slider(5, 120, 1, false, skin);
        fovSlider.setValue(Config.CAMERA_FOV);
        fovLabel = new Label("FOV", skin);
        fovValueLabel = new Label("", skin);

        /** AA slider  **/
        aaSlider = new Slider(0, 20, 1, false, skin);
        aaSlider.setValue(Config.AA_SAMPLES);
        aaLabel = new Label("AA Samples", skin);
        aaValueLabel = new Label("", skin);

        /** Disco slider  **/
        discoSlider = new Slider(0, 100, 1, false, skin);
        discoSlider.setValue(Config.DISCO_FACTOR);
        disoLabel = new Label("Disco Factor", skin);
        discoValueLabel = new Label("", skin);

        /** Shadow mapping checkbox  **/
        shadowMappingLabel = new Label("Shadow mapping", skin);
        shadowMapCheckbox = new CheckBox("", skin);
        shadowMapCheckbox.setChecked(true);

        /** Movement Speed textfield  **/
        moveSpeedLabel = new Label("Movement Speed", skin);
        moveSpeedIn = new TextField("30", skin);
        moveSpeedIn.setMessageText("Movement Speed");
        moveSpeedIn.setPosition(30, 30);
        //stage.addActor(moveSpeedIn);


        /** Sun RGB sliders  **/
        sunrSlider = new Slider(0, 100, 1, false, skin);
        sunrSlider.setValue(Config.SUN_LIGHT_R);
        sunrSliderLabel = new Label("Sun light R:", skin);
        sunrSliderValue = new Label("", skin);

        sungSlider = new Slider(0, 100, 1, false, skin);
        sungSlider.setValue(Config.SUN_LIGHT_G);
        sungSliderLabel = new Label("Sun light G:", skin);
        sungSliderValue = new Label("", skin);

        sunbSlider = new Slider(0, 100, 1, false, skin);
        sunbSlider.setValue(Config.SUN_LIGHT_B);
        sunbSliderLabel = new Label("Sun light B:", skin);
        sunbSliderValue = new Label("", skin);



        table = new Table();

        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        table.add(settingsHeading).expandX().height(60);
        table.row();
        table.add(fovLabel).expandY();
        table.add(fovSlider);
        table.add(fovValueLabel).padRight(300);
        table.row();
        table.add(aaLabel).expandY();
        table.add(aaSlider);
        table.add(aaValueLabel).padRight(300);
        table.row();
        table.add(disoLabel).expandY();
        table.add(discoSlider);
        table.add(discoValueLabel).padRight(300);
        table.row();
        table.add(shadowMappingLabel).expandY();
        table.add(shadowMapCheckbox).padRight(160);
        table.row();
        table.add(moveSpeedLabel).expandY();
        table.add(moveSpeedIn).expandY();
        table.row();
        table.add(sunrSliderLabel).expandY();
        table.add(sunrSlider);
        table.add(sunrSliderValue).padRight(300);
        table.row();
        table.add(sungSliderLabel).expandY();
        table.add(sungSlider);
        table.add(sungSliderValue).padRight(300);
        table.row();
        table.add(sunbSliderLabel).expandY();
        table.add(sunbSlider);
        table.add(sunbSliderValue).padRight(300);
        table.row();
        table.add(mainMenuButton).expandY().width(450).height(60);
        table.row();


        table.setFillParent(true);
        table.pack();

        fovSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.CAMERA_FOV = ((int) value);
                updateFOVlabel();
            }
        });

        aaSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.AA_SAMPLES = ((int) value);
                updateAAlabel();
            }
        });

        discoSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.DISCO_FACTOR = ((int) value);
                updateDiscoLabel();
            }
        });

        sunrSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.SUN_LIGHT_R = ((int) value );
                updateSunRLabel();
            }
        });

        sungSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.SUN_LIGHT_G = ((int) value);
                updateSunGLabel();
            }
        });

        sunbSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.SUN_LIGHT_B = ((int) value);
                updateSunBLabel();
            }
        });

        shadowMapCheckbox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Config.SHADOWMAPPING_ENABLED = shadowMapCheckbox.isChecked();
            }
        });

        moveSpeedIn.setTextFieldListener(new TextField.TextFieldListener() {

            public void keyTyped (TextField textField, char key) {

                if(textField.getText().length() > 0){
                    Config.MOVE_SPEED = Integer.parseInt(textField.getText());
                }
                else{
                    Config.MOVE_SPEED = Integer.parseInt(textField.getText());
                }

            }
        });

        stage.addActor(table);

    }

}