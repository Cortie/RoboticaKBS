package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends Game
{
	public SpriteBatch batch;
	public static OrthographicCamera camera;
	public FitViewport viewPort;
	public Sprite backgroundSprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Texture backgroundTexture = new Texture(Gdx.files.internal("BG.jpg"));
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
		viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewPort.apply();
		setScreen(new MainMenuScreen(this));
	}
	@Override
	public void render ()
	{
		batch.setProjectionMatrix(camera.combined);
		super.render();
	}
	@Override
	public void dispose () {

	}
	@Override
	public void resize(int width, int height)
	{
		viewPort.update(width, height, true);
		super.resize(width, height);
	}
}