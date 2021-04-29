package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.SerialListener;

public class MainMenuScreen implements Screen {
    MyGdxGame game;

    Texture SettingsButtonInactive;
    Texture SettingsButtonActive;
    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture ExitButtonActive;
    Texture ExitButtonInactive;
    private static Sound buttonSwitch;

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
    buttonSwitch = Gdx.audio.newSound(Gdx.files.internal("Game_menu.wav"));
    }

    @Override
    public void render(float delta) {
    Gdx.gl.glClearColor(0,0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.batch.begin();
    game.backgroundSprite.draw(game.batch);
    if(SerialListener.selectedmenu == 1)
    {
        game.batch.draw(ExitButtonInactive, 100, 100, 330, 150);
        game.batch.draw(SettingsButtonInactive, 100, 350, 384, 85);
        game.batch.draw(PlayButtonActive, 100, 500, 330, 150);
    }
    if(SerialListener.selectedmenu == 2)
    {
        game.batch.draw(ExitButtonInactive, 100, 100, 330, 150);
        game.batch.draw(SettingsButtonActive, 100, 350, 384, 85);
        game.batch.draw(PlayButtonInactive, 100, 500, 330, 150);
    }
    if(SerialListener.selectedmenu == 3)
    {
        game.batch.draw(ExitButtonActive, 100, 100, 330, 150);
        game.batch.draw(SettingsButtonInactive, 100, 350, 384, 85);
        game.batch.draw(PlayButtonInactive, 100, 500, 330, 150);
    }

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
    
    public static void playSound()
    {
        long id = buttonSwitch.play(1.0f);
        buttonSwitch.setPitch(id, 1);
        buttonSwitch.setLooping(id, false);
    }
    @Override
    public void dispose() {

    }
}
