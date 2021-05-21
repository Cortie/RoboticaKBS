package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameOver implements Screen {
    MyGdxGame game;

    Texture GameOverButtonActive;
    Texture ScoreButtonActive;
    Texture ScoreButtonInactive;
    Texture PlayAgainButtonInactive;
    Texture PlayAgainButtonActive;
    Texture BackButtonInactive;
    Texture BackButtonActive;
    static final String NO_URL = "jdbc:mysql://localhost";
    static final String SQLdb = "CREATE DATABASE IF NOT EXISTS mydb";
    static final String SQLtable = "CREATE TABLE IF NOT EXISTS " + "highscore (id INTEGER(3), Score INTEGER(255) NOT NULL, Time DateTime PRIMARY KEY NOT NULL)";
    static final String DB_URL = "jdbc:mysql://localhost/mydb";
    static final String USER = "root";
    static final String PASS = "";

    // Settings the sprites for the buttons
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
    // Giving functions to the buttons
    // This gets activated when you left click
    public void selectScene() {
            // inserting the score, date and time into the database
        try{
            Connection conn1 = DriverManager.getConnection(NO_URL, USER, PASS);
            PreparedStatement stmt = conn1.prepareStatement(SQLdb);
            stmt.execute();
            conn1.close();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn2= DriverManager.getConnection(DB_URL, USER, PASS);
            // Execute a query
            PreparedStatement stmt2 = conn2.prepareStatement(SQLtable);
            stmt2.execute();
            PreparedStatement statement = conn2.prepareStatement("INSERT INTO highscore (score, time) VALUES(?, ?)");
            statement.setInt(1, game.getScore());
            statement.setTimestamp(2, timestamp);
            int i = statement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

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
        // Draw Score of the game
        game.getBigfont().draw(game.batch, game.getScoretext(),235,650);
        // Cicle through active and inactive buttons
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
