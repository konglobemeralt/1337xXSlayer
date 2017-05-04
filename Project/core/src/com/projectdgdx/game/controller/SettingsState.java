package com.projectdgdx.game.controller;

/**
 * SettingsState creates and renders an UI to configure the game settings
 *
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


    /**
     * createBasicSkin reads an internalGUI file and creates a skin
     *
     * @param path Path to the GUIskin file
     * @return A new skin
     */
    private Skin createBasicSkin(String path){

        return new Skin(Gdx.files.internal(path));

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

        skin = createBasicSkin(Config.UI_SKIN_PATH);

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

        moveSpeedLabel = new Label("Movement Speed", skin);
        moveSpeedIn = new TextField("30", skin);
        moveSpeedIn.setMessageText("Movement Speed");
        moveSpeedIn.setPosition(30, 30);
        //stage.addActor(moveSpeedIn);

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

        sunrSlider.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Slider slider = (Slider) actor;

                float value = slider.getValue();
                Config.SUN_LIGHT_R = ((int) value);
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

        this.multiplexer = projectD.getMultiplexer();
        multiplexer.addProcessor(stage);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);


    }

    /**
     * sets he updates the FOVlabel value
     **/
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

    private void updateSunRLabel()
    {
        float value = Config.SUN_LIGHT_R;

        sunrSlider.setValue(value * 100);
        sunrSlider.setAnimateDuration(0.3f);

        sunrSliderValue.setText(String.valueOf(Config.SUN_LIGHT_R));
        sunrSliderValue.invalidate();
    }

    private void updateSunGLabel()
    {
        float value = Config.SUN_LIGHT_G;

        sungSlider.setValue(value * 100f);
        sungSlider.setAnimateDuration(0.3f);

        sungSliderValue.setText(String.valueOf(Config.SUN_LIGHT_R));
        sungSliderValue.invalidate();
    }

    private void updateSunBLabel()
    {
        float value = Config.SUN_LIGHT_B;

        sunbSlider.setValue(value * 100f);
        sunbSlider.setAnimateDuration(0.3f);

        sunbSliderValue.setText(String.valueOf(Config.SUN_LIGHT_B));
        sunbSliderValue.invalidate();
    }

    @Override
    public void start() {
        updateFOVlabel();
        updateAAlabel();

        updateSunRLabel();
        updateSunGLabel();
        updateSunBLabel();

    }

    @Override
    public void stop() {

    }

    @Override
    public void exit() {
        stage.dispose();
    }
}

