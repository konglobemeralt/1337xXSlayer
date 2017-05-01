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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.projectdgdx.game.Config;
import com.projectdgdx.game.GameStates;
import com.projectdgdx.game.ProjectD;

import static com.badlogic.gdx.Gdx.gl20;

public class SettingsState implements GameState {

    private Skin skin;
    private Stage stage;
    private TextButton toggleShadowMapButton;
    private TextButton backButton;
    private InputMultiplexer multiplexer;

    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }

    @Override
    public void update(ProjectD projectD) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(gl20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if(toggleShadowMapButton.isPressed()){
            Config.SHADOWMAPPING_ENABLED = !Config.SHADOWMAPPING_ENABLED;
            setShadowMapButtonText();
        }

        if(backButton.isPressed()){
            this.exit();
            projectD.setState(GameStates.INGAME);
        }
    }

    private void setShadowMapButtonText(){
        if(Config.SHADOWMAPPING_ENABLED){
            toggleShadowMapButton.setText("Shadow mapping enabled");
        }
        else{
            toggleShadowMapButton.setText("Shadow mapping disabled");
        }
    }
    @Override
    public void init(ProjectD projectD) {

        this.stage = new Stage();

        createBasicSkin();
        toggleShadowMapButton = new TextButton("", skin); // Use the initialized skin
        setShadowMapButtonText();
        toggleShadowMapButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2 + 45);
        stage.addActor(toggleShadowMapButton);

        backButton = new TextButton("Start Game", skin);
        backButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2 - 45);
        stage.addActor(backButton);


        this.multiplexer = projectD.getMultiplexer();
        multiplexer.addProcessor(stage);// Make the stage consume events
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void exit() {
        stage.dispose();
    }
}

