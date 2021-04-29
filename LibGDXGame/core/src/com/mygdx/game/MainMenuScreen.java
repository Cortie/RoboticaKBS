package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {
    MyGdxGame game;

    Texture SettingsButtonInactive;
    Texture SettingsButtonActive;
    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture ExitButtonActive;
    Texture ExitButtonInactive;
    int ButtonCicle;

    public MainMenuScreen(MyGdxGame game){
        this.game = game;
        SettingsButtonActive = new Texture("SettingsButtonActive.png");
        SettingsButtonInactive = new Texture("SettingsButtonInactive.png");
        PlayButtonActive = new Texture("PlayButtonActive.png");
        PlayButtonInactive = new Texture("PlayButtonInactive.png");
        ExitButtonActive = new Texture("ExitButtonActive.png");
        ExitButtonInactive = new Texture("ExitButtonInactive.png");
    }

    @Override
    public void show() {
    
    }

    @Override
    public void render(float delta) {
    Gdx.gl.glClearColor(0,0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.batch.begin();
    game.backgroundSprite.draw(game.batch);

    game.batch.draw(ExitButtonInactive, 100, 100, 330, 150);
    game.batch.draw(SettingsButtonInactive, 100, 350, 384, 85);
    game.batch.draw(PlayButtonActive, 100, 500, 330, 150);

    game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
