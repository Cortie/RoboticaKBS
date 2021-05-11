package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class GameOver implements Screen {
    MyGdxGame game;

    Texture GameOverButtonActive;
    Texture ScoreButtonActive;
    Texture ScoreButtonInactive;
    Texture PlayAgainButtonInactive;
    Texture PlayAgainButtonActive;
    Texture BackButtonInactive;
    Texture BackButtonActive;

    public GameOver(MyGdxGame game){
        this.game = game;
        GameOverButtonActive = new Texture("GameOverButtonActive.png");
        ScoreButtonActive = new Texture("ScoreButtonActive.png");
        ScoreButtonInactive = new Texture("ScoreButtonInactive.png");
        PlayAgainButtonInactive = new Texture("PlayAgainButtonInactive.png");
        PlayAgainButtonActive = new Texture("PlayAgainButtonActive.png");
        BackButtonInactive = new Texture("BackButtonInactive.png");
        BackButtonActive = new Texture("BackButtonActive.png");
    }
    public void selectScene() {

        SerialListener.Click = false;
        if (SerialListener.GameOverSelecter == 2) {
            System.out.println("PlayAgain");
            game.setScreen(new Gameplay(game));
            MyGdxGame.GameOverActive = false;
            SerialListener.GameOverSelecter = 1;
            game.setScoretext("0");
            game.setScore(0);

        }
        if (SerialListener.GameOverSelecter == 3) {
            System.out.println("Back");
            game.setScreen(new MainMenuScreen(game));
            SerialListener.GameOverSelecter = 1;
            MyGdxGame.GameOverActive = false;
            MyGdxGame.menuActive = true;
            SerialListener.Click = false;
            game.setScoretext("0");
            game.setScore(0);
        }

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
        game.getBigfont().draw(game.batch, game.getScoretext(),235,650);
        if(SerialListener.GameOverSelecter == 1)
        {
            game.batch.draw(BackButtonInactive, 50, 200, 277, 85);
            game.batch.draw(PlayAgainButtonInactive, 50, 350, 638, 85);
            game.batch.draw(GameOverButtonActive, 50, 750, 616, 100);
        }
        if(SerialListener.GameOverSelecter == 2)
        {
            game.batch.draw(BackButtonInactive, 50, 200, 277, 85);
            game.batch.draw(PlayAgainButtonActive, 50, 350, 638, 85);
            game.batch.draw(GameOverButtonActive, 50, 750, 616, 100);
        }
        if(SerialListener.GameOverSelecter == 3)
        {
            game.batch.draw(BackButtonActive, 50, 200, 277, 85);
            game.batch.draw(PlayAgainButtonInactive, 50, 350, 638, 85);
            game.batch.draw(GameOverButtonActive, 50, 750, 616, 100);
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
