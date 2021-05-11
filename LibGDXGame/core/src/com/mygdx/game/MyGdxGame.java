package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends Game
{
	public static Player player1;
	public static Player player2;
	public SpriteBatch batch;
	public static OrthographicCamera camera;
	public FitViewport viewPort;
	public Sprite backgroundSprite;
	private final SerialListener listener = new SerialListener();
	public static boolean menuActive;
	public static boolean SettingsActive;
	public static boolean GameOverActive;
	private int players = 1;
	private int playerlives = 3;

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	@Override
	public void create () {
		player1 = new Player(Gdx.files.internal("Spaceship_01_GREEN.png"), new BulletType(new Texture(Gdx.files.internal("6.png")), 1, 20, 60), Gdx.graphics.getWidth()/ 4 - 64 / 2);
		player2 = new Player(Gdx.files.internal("Spaceship_01_BLUE.png"), new BulletType(new Texture(Gdx.files.internal("5.png")), 2, 20, 60), Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/ 4 - 64 / 2);
		Thread listenerThread = new Thread(listener);
		listenerThread.setDaemon(true);
		listenerThread.start();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Texture backgroundTexture = new Texture(Gdx.files.internal("BG.jpg"));
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
		viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewPort.apply();
		setScreen(new MainMenuScreen(this));
		menuActive = true;

	}
	
	public int getPlayerlives()
	{
		return playerlives;
	}
	
	public void setPlayerlives(int playerlives)
	{
		this.playerlives = playerlives;
	}
	
	@Override
	public void render ()
	{
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0, 0, 0, 1);
		
		// tell the camera to update its matrices.
		camera.update();
		
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
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
	public static void playSound(Sound s)
	{
		long id = s.play(1.0f);
		s.setPitch(id, 1);
		s.setLooping(id, false);
	}
}