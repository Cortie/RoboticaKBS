package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter{
	private Texture bulletImage;
	private Texture shipImage;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private static Rectangle ship;
	private Array<Rectangle> raindrops;
	private Array<Ellipse> bullets;
	private SerialListener listener = new SerialListener();
	private Thread ListenerThread;
	private Sound hit;
	private static Texture backgroundTexture;
	private static Sprite backgroundSprite;
	private long lastDropTime;
	private long lastShot;
	private long shotSpeed = 750000000;
	
	@Override
	public void create () {
		// load the images for the droplet and the bucket, 64x64 pixels each
		bulletImage = new Texture(Gdx.files.internal("4.png"));
		shipImage = new Texture(Gdx.files.internal("Spaceship_01_PURPLE.png"));
		Gdx.graphics.setWindowedMode(1920, 1080);
		Gdx.graphics.setResizable(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		ship = new Rectangle();
		ship.x = Gdx.graphics.getWidth()/ 2 - 64 / 2;
		ship.y = 20;
		ship.width = 64;
		ship.height = 64;
		raindrops = new Array<Rectangle>();
		bullets = new Array<Ellipse>();
		ListenerThread = new Thread(listener);
		ListenerThread.setDaemon(true);
		ListenerThread.start();
		backgroundTexture = new Texture(Gdx.files.internal("BG.jpg"));
		backgroundSprite = new Sprite(backgroundTexture);
		hit = Gdx.audio.newSound(Gdx.files.internal("game_explosion.wav"));
	}
	private void loadTextures()
	{
		backgroundTexture = new Texture(Gdx.files.internal("BG.jpg"));
	}
	public void renderBackground()
	{
		backgroundSprite.draw(batch);
	}
	
	@Override
	public void render ()
	{
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		// tell the camera to update its matrices.
		camera.update();
		
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);
		
		// begin a new batch and draw the bucket and
		// all drops
		batch.begin();
		renderBackground();
		batch.draw(shipImage, ship.x, ship.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(bulletImage, raindrop.x, raindrop.y);
		}
		for(Ellipse bullet: bullets)
		{
			batch.draw(bulletImage, bullet.x, bullet.y);
		}
		batch.end();

		// make sure the bucket stays within the screen bounds
		if(ship.x < 0) ship.x = 0;
		if(ship.x > Gdx.graphics.getWidth() - 64) ship.x = Gdx.graphics.getWidth() - 64;
		
		// check if we need to create a new raindrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		if(TimeUtils.nanoTime() - lastShot > shotSpeed) spawnBullet();
		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the latter case we play back
		// a sound effect as well.
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if(raindrop.overlaps(ship)) {
				long id = hit.play(1.0f);
				hit.setPitch(id, 1);
				hit.setLooping(id, false);
				iter.remove();
			}
		}
		for(Iterator<Ellipse> iter = bullets.iterator(); iter.hasNext();)
		{
			Ellipse bullet = iter.next();
			bullet.y += 200 * Gdx.graphics.getDeltaTime();
			if(bullet.y + 64 > Gdx.graphics.getHeight()) iter.remove();
			
		}
	}
	
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
	private void spawnBullet()
	{
		Ellipse bullet = new Ellipse();
		bullet.x = ship.x;
		bullet.y = ship.y + 64;
		bullet.width = 64;
		bullet.height = 64;
		bullets.add(bullet);
		lastShot = TimeUtils.nanoTime();
	}
	@Override
	public void dispose () {
		bulletImage.dispose();
		shipImage.dispose();
		batch.dispose();
		hit.dispose();
	}
	public static void moveLeft()
	{
		ship.x -= 300 * Gdx.graphics.getDeltaTime();
	}
	public static void moveRight()
	{
		ship.x += 300 * Gdx.graphics.getDeltaTime();
	}
}