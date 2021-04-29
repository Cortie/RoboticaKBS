package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

public class Gameplay implements Screen
{
    private Texture bulletImage;
    public static OrthographicCamera camera;
    public static Player player1;
    public static Player player2;
    private Array<Rectangle> raindrops;
    private SerialListener listener = new SerialListener();
    private Sound hit;
    private Array<EnemyType> enemylvls;
    private Array<Enemy> enemies;
    private static Sprite backgroundSprite;
    private long lastDropTime;
    private long lastShot;
    private int players = 1;
    private Viewport viewPort;
    private MyGdxGame game;
    
    public Gameplay(MyGdxGame game)
    {
        this.game = game;
    }
    
    @Override
    public void show()
    {
        enemylvls = new Array<EnemyType>();
        for(int i = 0; i < 4; i++)
        {
            String key = "";
            int nummer = i + 2;
            switch(i)
            {
                case 0:
                    key = "Big";
                    break;
                case 1:
                    key = "Standard";
                    break;
                case 2:
            
            }
            enemylvls.add(new EnemyType(new Texture(Gdx.files.internal("Spaceship_0" + nummer + "_RED.png"))));
        }
        Thread listenerThread = new Thread(listener);
        listenerThread.setDaemon(true);
        listenerThread.start();
        Texture backgroundTexture = new Texture(Gdx.files.internal("BG.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
        bulletImage = new Texture(Gdx.files.internal("4.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player1 = new Player(Gdx.files.internal("Spaceship_01_GREEN.png"), new BulletType(new Texture(Gdx.files.internal("6.png"))), Gdx.graphics.getWidth()/ 4 - 64 / 2);
        player2 = new Player(Gdx.files.internal("Spaceship_01_BLUE.png"), new BulletType(new Texture(Gdx.files.internal("5.png"))), Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/ 4 - 64 / 2);
        enemies = new Array<Enemy>();
        hit = Gdx.audio.newSound(Gdx.files.internal("game_explosion.wav"));
        viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewPort.apply();
    }
    
    @Override
    public void render(float delta)
    {
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0, 1);
    
        // tell the camera to update its matrices.
        camera.update();
    
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
    
        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.draw(player1.getShipImg(), player1.x, player1.y);
        if(players == 2)
        {
            game.batch.draw(player2.getShipImg(), player2.x, player2.y);
        }
        for(Enemy enemy: enemies) {
            game.batch.draw(enemy.getType().getShipImg(), enemy.getShip().getX(), enemy.getShip().getY());
        }
        for(Bullet bullet: player1.getBullets())
        {
            game.batch.draw(bullet.getType().getBulletImg(), bullet.x, bullet.y);
        }
        game.batch.end();
    
        // check if we need to create a new raindrop
        if(TimeUtils.millis() - lastDropTime > 0)
        {
            float position;
            int rand = MathUtils.random(0,2);
            float screentop = camera.viewportHeight;
            if(rand == 0)
            {
                position = camera.viewportWidth/2 - enemylvls.get(rand).getSize();
                spawnEnemy(position , screentop, enemylvls.get(rand));
                spawnEnemy(position + 150, screentop + 150, enemylvls.get(rand));
                spawnEnemy(position - 150, screentop + 150, enemylvls.get(rand));
                lastDropTime = TimeUtils.millis() + 5000;
            }
            if(rand == 1)
            {
                position = MathUtils.random(0, Gdx.graphics.getWidth() - 70);
                for(int i = 0; i < 5; i++)
                {
                    spawnEnemy(position, screentop, enemylvls.get(rand));
                    if(i == 0)
                    {
                        spawnEnemy(position + 90, screentop + 50, enemylvls.get(rand));
                        spawnEnemy(position - 90, screentop + 50, enemylvls.get(rand));
                    }
                    screentop += 75;
                }
                lastDropTime = TimeUtils.millis() + 3500;
            }
        }
        if(TimeUtils.nanoTime() - lastShot > player1.getShotSpeed())
        {
            Bullet bullet = new Bullet(player1.getType());
            bullet.spawnBullet(player1.x + 26, player1.y + 64);
            lastShot = TimeUtils.nanoTime();
        }
        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the latter case we play back
        // a sound effect as well.
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();
            if(enemy.getType().equals(enemylvls.get(0)))
            {
            
            }
            enemy.getShip().setY(enemy.getShip().getY() - 150 * Gdx.graphics.getDeltaTime());
            if(enemy.getShip().getY() + 64 < 0) iter.remove();
            if(enemy.getShip().overlaps(player1.getArea())) {
                long id = hit.play(1.0f);
                hit.setPitch(id, 1);
                hit.setLooping(id, false);
                iter.remove();
            }
        }
        for(Iterator<Bullet> iter = player1.getBullets().iterator(); iter.hasNext();)
        {
            Bullet bullet = iter.next();
            bullet.y += 200 * Gdx.graphics.getDeltaTime();
            if(bullet.y + 64 > Gdx.graphics.getHeight()) iter.remove();
            for(Iterator<Enemy> iter2 = enemies.iterator(); iter2.hasNext();)
            {
                Enemy enemy = iter2.next();
                if(enemy.getShip().overlaps(bullet.getHitbox()))
                {
                    enemy.setHealth(enemy.getHealth() - 1);
                    long id = hit.play(1.0f);
                    hit.setPitch(id, 1);
                    hit.setLooping(id, false);
                    if(enemy.getHealth() == 0)
                    {
                        iter2.remove();
                    }
                }
            }
        }
    }
    
    @Override
    public void resize(int width, int height){
        viewPort.update(width, height, true);
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
        for(Bullet bullet: player1.getBullets())
        {
            bullet.getType().getBulletImg().dispose();
        }
        for(Enemy enemy: enemies)
        {
            enemy.getType().getShipImg().dispose();
        }
        player1.getShipImg().dispose();
        hit.dispose();
        game.batch.dispose();
    }
    private void spawnEnemy(float position, float height, EnemyType type)
    {
        Enemy enemy = new Enemy(type);
        enemy.setX(position);
        enemy.setY(height);
        enemy.getShip().setX(position);
        enemy.getShip().setY(height);
        if(enemy.getType().equals(enemylvls.get(0)))
        {
            enemy.setHealth(3);
            enemy.getType().setSize(150);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize() - 30);
        }
        if(enemy.getType().equals(enemylvls.get(1)))
        {
            enemy.setHealth(1);
            enemy.getType().setSize(70);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize());
        }
        enemies.add(enemy);
    }
}
