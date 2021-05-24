package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class SubmitScore implements Screen
{
    private MyGdxGame game;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public SubmitScore(MyGdxGame game)
    {
        this.game = game;
    }
    @Override
    public void show()
    {
    
    }
    
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        //for(int i = 0; i < alphabet.length())
    }
    
    @Override
    public void resize(int width, int height)
    {
    
    }
    
    @Override
    public void pause()
    {
    
    }
    
    @Override
    public void resume()
    {
    
    }
    
    @Override
    public void hide()
    {
    
    }
    
    @Override
    public void dispose()
    {
    
    }
}
