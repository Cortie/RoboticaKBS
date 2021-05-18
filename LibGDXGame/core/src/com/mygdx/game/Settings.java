package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class Settings implements Screen {
    MyGdxGame game;

    Texture BackButtonInactive;
    Texture BackButtonActive;
    Texture SoundButtonInactive;
    Texture SoundButtonActive;
    Texture OnePlayerButtonInactive;
    Texture OnePlayerButtonActive;
    Texture TwoPlayerButtonInactive;
    Texture TwoPlayerButtonActive;

    // texures aan de buttons geven
    public Settings(MyGdxGame game){
        this.game = game;
        BackButtonInactive = new Texture("BackButtonInactive.png");
        BackButtonActive = new Texture("BackButtonActive.png");
        SoundButtonInactive = new Texture("SoundButtonInactive.png");
        SoundButtonActive = new Texture("SoundButtonActive.png");
        OnePlayerButtonInactive = new Texture("1PlayerButtonInactive.png");
        OnePlayerButtonActive = new Texture("1PlayerButtonActive.png");
        TwoPlayerButtonInactive = new Texture("2PlayerButtonInactive.png");
        TwoPlayerButtonActive = new Texture("2PlayerButtonActive.png");
    }
    //hier wordt functies aan de buttons geven
    // als op de linker knop wordt gedrukt word deze functie uitgevoerd
    public void selectScene() {

        SerialListener.Click = false;
        if (SerialListener.SettingSelecter == 1) {
            System.out.println("Sounds");

        }
        if (SerialListener.SettingSelecter == 2) {
            System.out.println("1 player");
            game.setPlayercount(1);
            SerialListener.Click = false;
        }
        if (SerialListener.SettingSelecter == 3) {
            System.out.println("2 Player");
            game.setPlayercount(2);
            SerialListener.Click = false;
        }
        if (SerialListener.SettingSelecter == 4) {
            System.out.println("Back");
            game.setScreen(new MainMenuScreen(game));
            MyGdxGame.SettingsActive = false;
            SerialListener.Click = false;
            MyGdxGame.menuActive= true;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // hier worden alle buttons + background gedrawed
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        // cicle voor de active en inactive buttons
        if(SerialListener.SettingSelecter == 1)
        {
            game.batch.draw(BackButtonInactive, 50, 200, 277, 85);
            game.batch.draw(TwoPlayerButtonInactive, 50, 350, 554, 85);
            game.batch.draw(OnePlayerButtonInactive, 50, 500, 519, 85);
            game.batch.draw(SoundButtonActive, 50, 650, 415, 85);
        }
        if(SerialListener.SettingSelecter == 2)
        {
            game.batch.draw(BackButtonInactive, 50, 200, 277, 85);
            game.batch.draw(TwoPlayerButtonInactive, 50, 350, 554, 85);
            game.batch.draw(OnePlayerButtonActive, 50, 500, 519, 85);
            game.batch.draw(SoundButtonInactive, 50, 650, 415, 85);
        }
        if(SerialListener.SettingSelecter == 3)
        {
            game.batch.draw(BackButtonInactive, 50, 200, 277, 85);
            game.batch.draw(TwoPlayerButtonActive, 50, 350, 554, 85);
            game.batch.draw(OnePlayerButtonInactive, 50, 500, 519, 85);
            game.batch.draw(SoundButtonInactive, 50, 650, 415, 85);
        }
        if(SerialListener.SettingSelecter == 4)
        {
            game.batch.draw(BackButtonActive, 50, 200, 277, 85);
            game.batch.draw(TwoPlayerButtonInactive, 50, 350, 554, 85);
            game.batch.draw(OnePlayerButtonInactive, 50, 500, 519, 85);
            game.batch.draw(SoundButtonInactive, 50, 650, 415, 85);
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
