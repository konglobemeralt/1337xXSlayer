package com.projectdgdx.game.controller;

/**
 * Created by Jesper on 2017-05-01.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;

import static com.badlogic.gdx.Gdx.gl20;

public class SettingsState implements GameState {

    private Skin skin;
    private Stage stage;

    private Label shadowMappingLabel;
    private CheckBox shadowMapCheckbox;


    //private TextButton toggleShadowMapButton;
    private TextButton backButton;
    private InputMultiplexer multiplexer;

    private Slider fovSlider;
    private Label fovValueLabel;
    private Label fovLabel;

    private Slider aaSlider;
    private Label aaLabel;
    private Label aaValueLabel;


    private Label settingsHeading;
    private TextField moveSpeedIn;

    private Table table;

    private void createBasicSkin(){

        skin = new Skin(Gdx.files.internal("GUIStyle/uiskin.json"));

        //  //Create a font
     //  BitmapFont font = new BitmapFont();
     //  skin = new Skin();
     //  skin.add("default", font);

     //  //Create a texture
     //  Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
     //  pixmap.setColor(Color.WHITE);
     //  pixmap.fill();
     //  skin.add("background",new Texture(pixmap));

     //  //Create a button style
     //  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
     //  textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
     //  textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
     //  textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
     //  textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
     //  textButtonStyle.font = skin.getFont("default");
     //  skin.add("default", textButtonStyle);

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


        if(backButton.isPressed()&& Gdx.input.justTouched()){
            this.exit();
            projectD.setState(GameStates.INGAME);
        }
    }


    @Override
    public void init(ProjectD projectD) {

        this.stage = new Stage();

        createBasicSkin();

        settingsHeading = new Label("Settings Menu", skin);


        //toggleShadowMapButton = new TextButton("", skin); // Use the initialized skin
        //setShadowMapButtonText();
        //toggleShadowMapButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2 + 45);
        //stage.addActor(toggleShadowMapButton);

        backButton = new TextButton("Start Game", skin);
        //backButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2 - 45);
        //stage.addActor(backButton);

        fovSlider = new Slider(5, 120, 1, false, skin);
        fovSlider.setValue(Config.CAMERA_FOV);
        fovLabel = new Label("FOV", skin);
        fovValueLabel = new Label("", skin);

        aaSlider = new Slider(0, 20, 1, false, skin);
        aaSlider.setValue(Config.AA_SAMPLES);
        aaLabel = new Label("AA Samples", skin);
        aaValueLabel = new Label("", skin);

        shadowMappingLabel = new Label("Shadow mapping", skin);
        shadowMapCheckbox = new CheckBox("", skin);
        shadowMapCheckbox.setChecked(true);

        //moveSpeedIn = new TextField("5", skin);
        //moveSpeedIn.setMessageText("Movement Speed");
        //moveSpeedIn.setPosition(30, 30);
        //stage.addActor(moveSpeedIn);

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
        table.add(shadowMappingLabel).expandY();
        table.add(shadowMapCheckbox).padRight(300);;
        table.row();
        table.add(backButton).expandY().width(450).height(60);
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

        shadowMapCheckbox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Config.SHADOWMAPPING_ENABLED = shadowMapCheckbox.isChecked();
            }
        });

        stage.addActor(table);

        this.multiplexer = projectD.getMultiplexer();
        multiplexer.addProcessor(stage);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void updateFOVlabel()
    {
        float value = Config.CAMERA_FOV;

        fovSlider.setValue(value);
        fovSlider.setAnimateDuration(0.3f);

        fovValueLabel.setText(String.valueOf(Config.CAMERA_FOV));
        fovValueLabel.invalidate();
    }

    private void updateAAlabel()
    {
        float value = Config.AA_SAMPLES;

        aaSlider.setValue(value);
        aaSlider.setAnimateDuration(0.3f);

        aaValueLabel.setText(String.valueOf(Config.AA_SAMPLES));
        aaValueLabel.invalidate();
    }

    @Override
    public void start() {
        updateFOVlabel();
        updateAAlabel();
    }

    @Override
    public void stop() {

    }

    @Override
    public void exit() {
        stage.dispose();
    }
}

