package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    MyGdxGame game;

    Texture SettingsButtonInactive;
    Texture SettingsButtonActive;
    Texture PlayButtonActive;
    Texture PlayButtonInactive;
    Texture ExitButtonActive;
    Texture ExitButtonInactive;
    Texture HighscoreButtonActive;
    Texture HighscoreButtonInactive;


// texures aan de buttons geven
    public MainMenuScreen(MyGdxGame game){
        this.game = game;
        SettingsButtonActive = new Texture("SettingsButtonActive.png");
        SettingsButtonInactive = new Texture("SettingsButtonInactive.png");
        PlayButtonActive = new Texture("PlayButtonActive.png");
        PlayButtonInactive = new Texture("PlayButtonInactive.png");
        ExitButtonActive = new Texture("ExitButtonActive.png");
        ExitButtonInactive = new Texture("ExitButtonInactive.png");
        HighscoreButtonActive = new Texture("HighscoreButtonActive.png");
        HighscoreButtonInactive = new Texture("HighscoreButtonInactive.png");
    }
    //hier wordt functies aan de buttons geven
    // als op de linker knop wordt gedrukt word deze functie uitgevoerd
    public void selectScene(){
        SerialListener.Click = false;
        if (SerialListener.MainMenuSelecter == 1){
            game.setScreen(new Gameplay(game));
            MyGdxGame.menuActive= false;
            SerialListener.Click = false;
            game.setScoretext("0");
            game.setScore(0);
        }
        if (SerialListener.MainMenuSelecter == 2){
            game.setScreen(new Settings(game));
            MyGdxGame.SettingsActive = true;
            SerialListener.Click = false;
            MyGdxGame.menuActive= false;
            SerialListener.SettingSelecter = 1;

        }
        if(SerialListener.MainMenuSelecter == 3){
            game.setScreen(new Highscore(game));
            MyGdxGame.menuActive = false;
            SerialListener.Click = false;
            MyGdxGame.HighscoreActive = true;
        }
        if (SerialListener.MainMenuSelecter == 4){
            Gdx.app.exit();
        }
    }


    @Override
    public void show() {

    }

    @Override
    // hier worden alle buttons + background gedrawed
    public void render(float delta) {
    Gdx.gl.glClearColor(0,0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    game.batch.begin();
    game.backgroundSprite.draw(game.batch);
    // cicle voor de active en inactive buttons
    if(SerialListener.MainMenuSelecter == 1)
    {
        game.batch.draw(ExitButtonInactive, 50, 100, 200, 85);
        game.batch.draw(HighscoreButtonInactive, 50,250, 579, 85);
        game.batch.draw(SettingsButtonInactive, 50, 400, 480, 85);
        game.batch.draw(PlayButtonActive, 50, 550, 271, 85);
    }
        if(SerialListener.MainMenuSelecter == 2)
        {
            game.batch.draw(ExitButtonInactive, 50, 100, 200, 85);
            game.batch.draw(HighscoreButtonInactive, 50,250, 579, 85);
            game.batch.draw(SettingsButtonActive, 50, 400, 480, 85);
            game.batch.draw(PlayButtonInactive, 50, 550, 271, 85);
        }
        if(SerialListener.MainMenuSelecter == 3)
        {
            game.batch.draw(ExitButtonInactive, 50, 100, 200, 85);
            game.batch.draw(HighscoreButtonActive, 50,250, 579, 85);
            game.batch.draw(SettingsButtonInactive, 50, 400, 480, 85);
            game.batch.draw(PlayButtonInactive, 50, 550, 271, 85);
        }
        if(SerialListener.MainMenuSelecter == 4)
        {
            game.batch.draw(ExitButtonActive, 50, 100, 200, 85);
            game.batch.draw(HighscoreButtonInactive, 50,250, 579, 85);
            game.batch.draw(SettingsButtonInactive, 50, 400, 480, 85);
            game.batch.draw(PlayButtonInactive, 50, 550, 271, 85);
        }
    if(SerialListener.Click){
        selectScene();

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
    

    @Override
    public void dispose() {

    }
}
