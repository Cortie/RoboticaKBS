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
	public static OrthographicCamera camera;
	private SpriteBatch batch;
	public static Player player1;
	private Array<Rectangle> raindrops;
	//private Viewport viewPort;
	private Array<Ellipse> bullets;
	private SerialListener listener = new SerialListener();
	private Sound hit;
	private static Sprite backgroundSprite;
	private long lastDropTime;
	private long lastShot;
	private long shotSpeed = 750000000;
	
	@Override
	public void create () {
		bulletImage = new Texture(Gdx.files.internal("4.png"));
		camera = new OrthographicCamera();
		
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//viewPort = new ScreenViewport(camera);
		batch = new SpriteBatch();
		player1 = new Player(Gdx.files.internal("Spaceship_01_PURPLE.png"));
		raindrops = new Array<Rectangle>();
		bullets = new Array<Ellipse>();
		Thread listenerThread = new Thread(listener);
		listenerThread.setDaemon(true);
		listenerThread.start();
		Texture backgroundTexture = new Texture(Gdx.files.internal("BG.jpg"));
		backgroundSprite = new Sprite(backgroundTexture);
		hit = Gdx.audio.newSound(Gdx.files.internal("game_explosion.wav"));
	}
	@Override
	public void render ()
	{
		backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
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
		backgroundSprite.draw(batch);
		batch.draw(player1.getShipImg(), player1.x, player1.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(bulletImage, raindrop.x, raindrop.y);
		}
		for(Ellipse bullet: bullets)
		{
			batch.draw(bulletImage, bullet.x, bullet.y);
		}
		batch.end();
		
		// check if we need to create a new raindrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		if(TimeUtils.nanoTime() - lastShot > shotSpeed) spawnBullet();
		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the latter case we play back
		// a sound effect as well.
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.setY(raindrop.getY() - 200 * Gdx.graphics.getDeltaTime());
			if(raindrop.getY() + 64 < 0) iter.remove();
			if(raindrop.overlaps(player1.getArea())) {
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
		raindrop.setX(MathUtils.random(0, camera.viewportWidth-64));
		raindrop.setY(camera.viewportHeight);
		raindrop.setSize(64, 64);
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
	private void spawnBullet()
	{
		Ellipse bullet = new Ellipse();
		bullet.x = player1.x;
		bullet.y = player1.y + 64;
		bullet.width = 64;
		bullet.height = 64;
		bullets.add(bullet);
		lastShot = TimeUtils.nanoTime();
	}
	@Override
	public void dispose () {
		bulletImage.dispose();
		player1.getShipImg().dispose();
		batch.dispose();
		hit.dispose();
	}
}