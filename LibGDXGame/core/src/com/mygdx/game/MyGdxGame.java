package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
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
	private final SerialListener listener = new SerialListener(this);
	private final PiListener PiListener = new PiListener();
	public static boolean menuActive;
	public static boolean SettingsActive;
	public static boolean GameOverActive;
	public static boolean HighscoreActive;
	private int playercount = 1;
	private int playerlives = 3;
	private int score = 0;
	private String Scoretext = "0";
	private BitmapFont font;
	private BitmapFont Bigfont;
	private Array<PowerUp> powers = new Array<>();
	private int bossScore;
	private float volume = 1.0f;
	private float pitch = 1;
	
	@Override
	public void create () {
		player1 = new Player(Gdx.files.internal("Spaceship_01_GREEN.png"), new BulletType(new Texture(Gdx.files.internal("6.png")), 1, 20, 60), Gdx.graphics.getWidth()/ 4 - 64 / 2);
		if(playercount == 2)
		{
			player2 = new Player(Gdx.files.internal("Spaceship_01_BLUE.png"), new BulletType(new Texture(Gdx.files.internal("5.png")), 2, 20, 60), Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/ 4 - 64 / 2);
		}
		font = new BitmapFont(Gdx.files.internal("Scorefont.fnt"));
		Bigfont = new BitmapFont(Gdx.files.internal("BigScorefont.fnt"));
		Thread listenerThread = new Thread(listener);
		listenerThread.setDaemon(true);
		listenerThread.start();
		Thread PiListenerThread = new Thread(PiListener);
		PiListenerThread.setDaemon(true);
		PiListenerThread.start();
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
	//sets view when window is resized
	@Override
	public void resize(int width, int height)
	{
		viewPort.update(width, height, true);
		super.resize(width, height);
	}
	//plays a given sound at the current game volume
	public void playSound(Sound s)
	{
		long id = s.play(volume);
		s.setPitch(id, pitch);
		s.setLooping(id, false);
	}
	
	
	public int getPlayerlives()
	{
		return playerlives;
	}
	public void setPlayerlives(int playerlives)
	{
		this.playerlives = playerlives;
	}
	public Array<PowerUp> getPowers()
	{
		return powers;
	}
	public BitmapFont getFont() {
		return font;
	}
	public BitmapFont getBigfont() {
		return Bigfont;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		bossScore = bossScore + (score - this.score);
		this.score = score;
	}
	public String getScoretext() {
		return Scoretext;
	}
	public void setScoretext(String scoretext) {
		Scoretext = scoretext;
	}
	public int getPlayercount() {
		return playercount;
	}
	public void setPlayercount(int playercount) {
		this.playercount = playercount;
	}
	public float getVolume()
	{
		return volume;
	}
	public void setVolume(float volume)
	{
		this.volume = volume;
	}
}