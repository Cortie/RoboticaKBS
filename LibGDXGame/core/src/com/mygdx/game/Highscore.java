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

    // Settings the sprites for the buttons
    public Highscore(MyGdxGame game){
        this.game = game;
        BackButtonInactive = new Texture("BackButtonInactive.png");
        BackButtonActive = new Texture("BackButtonActive.png");
    }

    // Giving functions to the buttons
    // This gets activated when you left click
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
        // Selecting the information from the database and drawing it on the screen
        try {
            Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost", "root", "");
            PreparedStatement stmt = conn1.prepareStatement("CREATE DATABASE IF NOT EXISTS mydb");
            stmt.execute();
            conn1.close();

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/mydb";
            String username="root", password="";
            Connection conn2=
                DriverManager.getConnection(url, username, password);

            PreparedStatement stmt2 = conn2.prepareStatement("CREATE TABLE IF NOT EXISTS " + "highscore (id INTEGER(3), Score INTEGER(255), Time DateTime)");
            stmt2.execute();
            Statement statement = conn2.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT Score, Time FROM highscore ORDER BY Score DESC LIMIT 5" );
            int rank = 1;
            int step = 150;
            while(rs.next())
            {
                Timestamp time = rs.getTimestamp("Time");
                int score = rs.getInt("Score");
                game.getFont().draw(game.batch, rank + ": ", 50, 1150 - step * rank);
                game.getFont().draw(game.batch, String.valueOf(score),250,1150 - step * rank);
                game.getFont().draw(game.batch, time.toString(), 50, 1075 - step * rank);
                rank++;
            }
            statement.close();
            conn2.close();
        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        // Cicle through active and inactive buttons
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
