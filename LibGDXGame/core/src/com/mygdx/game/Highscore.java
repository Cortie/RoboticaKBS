package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.*;

public class Highscore implements Screen {
    MyGdxGame game;

    Texture BackButtonInactive;
    Texture BackButtonActive;

    public Highscore(MyGdxGame game){
        this.game = game;
        BackButtonInactive = new Texture("BackButtonInactive.png");
        BackButtonActive = new Texture("BackButtonActive.png");
    }
    public void selectScene() {

        SerialListener.Click = false;

        if (SerialListener.HighscoreSelecter == 2) {
            System.out.println("Back");
            game.setScreen(new MainMenuScreen(game));
            SerialListener.HighscoreSelecter = 1;
            MyGdxGame.HighscoreActive = false;
            MyGdxGame.menuActive = true;
            SerialListener.Click = false;
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
        
            String url = "jdbc:mysql://localhost/mydb";
            String username="root", password="";
        
            Connection conn=
                    DriverManager.getConnection(url, username, password);
        
        
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT Score, DateTime highscore (Score, DateTime) ORDER BY Score DESC LIMIT 5" );
            
            int rank = 1;
            int step = 150;
            while(rs.next())
            {
                Timestamp time = rs.getTimestamp("DateTime");
                int score = rs.getInt("Score");
                game.getBigfont().draw(game.batch, rank + ": ", 50, 1150 - step * rank);
                game.getBigfont().draw(game.batch, String.valueOf(score),250,1150 - step * rank);
                game.getBigfont().draw(game.batch, time.toString(), 450, 1150 - step * rank);
            }
            statement.close();
            conn.close();
        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        /*
        game.getBigfont().draw(game.batch, "1: ",50,1000);
        game.getBigfont().draw(game.batch, game.getScoretext(),250,1000);
        game.getBigfont().draw(game.batch, "2: ",50,850);
        game.getBigfont().draw(game.batch, game.getScoretext(),250,850);
        game.getBigfont().draw(game.batch, "3: ",50,700);
        game.getBigfont().draw(game.batch, game.getScoretext(),250,700);
        game.getBigfont().draw(game.batch, "4: ",50,550);
        game.getBigfont().draw(game.batch, game.getScoretext(),250,550);
        game.getBigfont().draw(game.batch, "5: ",50,400);
        game.getBigfont().draw(game.batch, game.getScoretext(),250,400);
        */
        if(SerialListener.HighscoreSelecter == 1)
        {
            game.batch.draw(BackButtonInactive, 50, 150, 277, 85);

        }
        if(SerialListener.HighscoreSelecter == 2)
        {
            game.batch.draw(BackButtonActive, 50, 150, 277, 85);

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
