package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Gameplay implements Screen
{
    private Sound hit;
    private Array<EnemyType> enemylvls;
    private Array<Enemy> enemies;
    private BulletType bigBullet;
    private BulletType standardBullet;
    private long lastDropTime;
    private long lastShot;
    private Array<Bullet> bullets;
    private long lastEnemyShot;
    private int players = 1;
    private MyGdxGame game;
    
    public Gameplay(MyGdxGame game)
    {
        this.game = game;
    }
    
    @Override
    public void show()
    {
        bullets = new Array<Bullet>();
        enemylvls = new Array<EnemyType>();
        for(int i = 0; i < 4; i++)
        {
            standardBullet = new BulletType(new Texture(Gdx.files.internal("4.png")), 3, 20, 60);
            bigBullet = new BulletType(new Texture(Gdx.files.internal("1.png")), 3, 30, 90);
            String key = "";
            long shotSpeed = 0;
            int nummer = i + 2;
            BulletType btype = standardBullet;
            switch(i)
            {
                case 0:
                    key = "Big";
                    shotSpeed = 350000000;
                    btype = bigBullet;
                    break;
                case 1:
                    key = "Standard";
                    shotSpeed = 600000000;
                    btype = standardBullet;
                    break;
                case 2:
                    key = "Expert";
                    shotSpeed = 750000000;
                    btype = bigBullet;
            }
            enemylvls.add(new EnemyType(btype, new Texture(Gdx.files.internal("Spaceship_0" + nummer + "_RED.png")), shotSpeed));
        }
        enemies = new Array<Enemy>();
        hit = Gdx.audio.newSound(Gdx.files.internal("game_explosion.wav"));
    }
    
    @Override
    public void render(float delta)
    {

        // begin a new batch and draw all game components (player, bullets, enemies)
        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        game.batch.draw(game.player1.getShipImg(), game.player1.x, game.player1.y);
        if(players == 2)
        {
            game.batch.draw(game.player2.getShipImg(), game.player2.x, game.player2.y);
        }
        for(Enemy enemy: enemies) {
            game.batch.draw(enemy.getType().getShipImg(), enemy.getShip().getX(), enemy.getShip().getY());
        }
        for(Bullet bullet: bullets)
        {
            game.batch.draw(bullet.getType().getBulletImg(), bullet.x, bullet.y);
        }
        game.batch.end();

        // Spawns random enemy types after a certain amount of time has passed
        if(TimeUtils.millis() - lastDropTime > 0)
        {
            float position;
            int rand = MathUtils.random(0,2);
            float screentop = game.camera.viewportHeight;
            if(rand == 0)
            {
                position = game.camera.viewportWidth/2 - enemylvls.get(rand).getSize();
                spawnEnemy(position , screentop, enemylvls.get(rand), bigBullet);
                spawnEnemy(position + 150, screentop + 150, enemylvls.get(rand), bigBullet);
                spawnEnemy(position - 150, screentop + 150, enemylvls.get(rand), bigBullet);
                lastDropTime = TimeUtils.millis() + 5000;
            }
            if(rand == 1)
            {
                position = MathUtils.random(0, Gdx.graphics.getWidth() - 70);
                for(int i = 0; i < 5; i++)
                {
                    spawnEnemy(position, screentop, enemylvls.get(rand), standardBullet);
                    if(i == 0)
                    {
                        spawnEnemy(position + 90, screentop + 50, enemylvls.get(rand), standardBullet);
                        spawnEnemy(position - 90, screentop + 50, enemylvls.get(rand), standardBullet);
                    }
                    screentop += 75;
                }
                lastDropTime = TimeUtils.millis() + 3500;
            }
        }
        // Spawns a bullet out of the player dependent on player shot speed
        if(TimeUtils.nanoTime() - lastShot > game.player1.getShotSpeed())
        {
            Bullet bullet = new Bullet(1, game.player1.getType(), new Rectangle(game.player1.x + 26, game.player1.y + 64, game.player1.getType().getWidth(), game.player1.getType().getHeight()), game.player1.x + 26, game.player1.y + 64);
            spawnBullet(bullet);
            lastShot = TimeUtils.nanoTime();
        }
        // move the enemies, remove any that are beneath the bottom edge of
        // the screen or that hit the player. In the latter case we play back
        // a sound effect as well, and remove a life/shield from the player.
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();
            if(TimeUtils.nanoTime() - enemy.getLastShot() > enemy.getType().getShotSpeed())
            {
                if(enemy.getType().equals(enemylvls.get(0)))
                {
                
                }
            }
            enemy.getShip().setY(enemy.getShip().getY() - 150 * Gdx.graphics.getDeltaTime());
            if(enemy.getShip().getY() + 64 < 0) iter.remove();
            if(enemy.getShip().overlaps(game.player1.getArea())) {
                long id = hit.play(1.0f);
                hit.setPitch(id, 1);
                hit.setLooping(id, false);
                iter.remove();
            }
        }
        // moves player's bullets and checks for collision with enemies
        // when an enemy is hit we remove health from the enemy and if their health
        // is 0 we remove the enemy from the screen
        for(Iterator<Bullet> iter = bullets.iterator(); iter.hasNext();)
        {
            Bullet bullet = iter.next();
            bullet.y += 200 * Gdx.graphics.getDeltaTime();
            bullet.setHitbox(new Rectangle(bullet.x, bullet.y, bullet.getHitbox().width, bullet.getHitbox().height));
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
                    iter.remove();
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
        for(Bullet bullet: game.player1.getBullets())
        {
            bullet.getType().getBulletImg().dispose();
        }
        for(Enemy enemy: enemies)
        {
            enemy.getType().getShipImg().dispose();
        }
        game.player1.getShipImg().dispose();
        hit.dispose();
        game.batch.dispose();
    }
    private void spawnEnemy(float position, float height, EnemyType type, BulletType btype)
    {
        Enemy enemy = new Enemy(type, btype);
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
    public void spawnBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }
}