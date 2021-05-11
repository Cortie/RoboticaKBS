package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private long lastDropTime;
    private Array<Bullet> bullets;
    private final MyGdxGame game;
    private int score;
    private BitmapFont font;
    private String Scoretest;
    private Texture oneheart;
    private Texture twoheart;
    private Texture threeheart;
    private Texture hearts;
    
    public Gameplay(MyGdxGame game)
    {
        this.game = game;
    }
    
    @Override
    public void show()
    {
        oneheart = new Texture(Gdx.files.internal("1live.png"));
        twoheart = new Texture(Gdx.files.internal("2lives.png"));
        threeheart = new Texture(Gdx.files.internal("3lives.png"));
        hearts = threeheart;
        font = new BitmapFont(Gdx.files.internal("Scorefont.fnt"));
        Scoretest = String.valueOf(score);
        //local variables used during gameplay
        //hit sound effect for any collision between bullets and ships
        //2 bullet types to be use in the creation of the different enemy types
        //the 3 arrays of to be rendered objects
        hit = Gdx.audio.newSound(Gdx.files.internal("game_explosion.wav"));
        BulletType standardBullet = new BulletType(new Texture(Gdx.files.internal("4.png")), 3, 20, 60);
        BulletType bigBullet = new BulletType(new Texture(Gdx.files.internal("1.png")), 3, 30, 90);
        bullets = new Array<>();
        enemylvls = new Array<>();
        enemies = new Array<>();
        //due to movement being connected to the same function
        // as the menu screen selection player position must be put in a spawn point here
        MyGdxGame.player1.x = MyGdxGame.camera.viewportWidth /10 + MyGdxGame.player1.getArea().getWidth();
        MyGdxGame.player2.x = MyGdxGame.camera.viewportWidth /3 * 2 + MyGdxGame.player2.getArea().getWidth();
        //this loop creates an array of different enemy types
        //that is later used for enemy creation and comparisons
        for(int i = 0; i < 4; i++)
        {
            long shotSpeed = 0;
            int nummer = i + 2;
            int pointvalue = 0;
            BulletType btype = standardBullet;
            switch(i)
            {
                case 0:
                    pointvalue = 50;
                    shotSpeed = 2500;
                    btype = bigBullet;
                    break;
                case 1:
                    shotSpeed = 2000;
                    pointvalue = 25;
                    btype = standardBullet;
                    break;
                case 2:
                    pointvalue = 75;
                    shotSpeed = 2000;
                    btype = bigBullet;
            }
            enemylvls.add(new EnemyType(btype, new Texture(Gdx.files.internal("Spaceship_0" + nummer + "_RED.png")), shotSpeed, pointvalue));
        }
    }
    
    @Override
    public void render(float delta)
    {
        // begin a new batch and draw all game components (player, bullets, enemies)
        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        game.batch.draw(MyGdxGame.player1.getShipImg(), MyGdxGame.player1.x, MyGdxGame.player1.y);
        if( game.getPlayers() == 2)
        {
            game.batch.draw(MyGdxGame.player2.getShipImg(), MyGdxGame.player2.x, MyGdxGame.player2.y);
        }
        for(Enemy enemy: enemies) {
            game.batch.draw(enemy.getType().getShipImg(), enemy.getShip().getX(), enemy.getShip().getY());
        }
        for(Bullet bullet: bullets)
        {
            game.batch.draw(bullet.getType().getBulletImg(), bullet.x, bullet.y);
        }
        font.draw(game.batch, Scoretest,30,game.camera.viewportHeight-50);
        game.batch.draw(hearts, 20, game.camera.viewportHeight-150);
        game.batch.end();
        // Spawns random enemy types after a certain amount of time has passed
        if(TimeUtils.millis() - lastDropTime > 0)
        {
            float position;
            int rand = MathUtils.random(0,2);
            float screentop = MyGdxGame.camera.viewportHeight;
            if(rand == 0)
            {
                position = MyGdxGame.camera.viewportWidth/2 - enemylvls.get(rand).getSize();
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
            if(rand == 2)
            {
                position = MyGdxGame.camera.viewportWidth/2 - enemylvls.get(rand).getSize();
                spawnEnemy(position , screentop, enemylvls.get(rand));
                spawnEnemy(position + 150, screentop + 150, enemylvls.get(rand));
                spawnEnemy(position - 150, screentop + 150, enemylvls.get(rand));
                lastDropTime = TimeUtils.millis() + 5000;
            }
        }
        // Spawns a bullet out of the player dependent on player shot speed
        if(TimeUtils.nanoTime() - MyGdxGame.player1.getLastShot() > MyGdxGame.player1.getShotSpeed())
        {
            Bullet bullet = new Bullet(1, MyGdxGame.player1.getType(), new Rectangle(MyGdxGame.player1.x + 26, MyGdxGame.player1.y + 64, MyGdxGame.player1.getType().getWidth(), MyGdxGame.player1.getType().getHeight()), MyGdxGame.player1.x + 26, MyGdxGame.player1.y + 64);
            spawnBullet(bullet);
            MyGdxGame.player1.setLastShot(TimeUtils.nanoTime());
        }
        // move the enemies, remove any that are beneath the bottom edge of
        // the screen or that hit the player. In the latter case we play back
        // a sound effect as well, and remove a life/shield from the player.
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();
            //checks if the lastshot of the enemy has surpassed shotspeed
            if(TimeUtils.millis() - enemy.getLastShot() > enemy.getType().getShotSpeed())
            {
                //creates a bullet depending on enemy type and then adds the bullet
                //to the bullet array to be drawn in the batch
                Bullet bullet = new Bullet(3, enemy.getType().getBtype(), new Rectangle(enemy.getX(), enemy.getY(), enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight()), enemy.getX(), enemy.getY());
                if(enemy.getType().equals(enemylvls.get(0)))
                {
                    spawnBullet(bullet);
                    bullet = new Bullet(3, enemy.getType().getBtype(), new Rectangle(enemy.getX() + 100, enemy.getY(), enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight()), enemy.getX() + 100, enemy.getY());
                    spawnBullet(bullet);
                }else
                {
                    spawnBullet(bullet);
                }
                enemy.setLastShot(TimeUtils.millis());
            }
            //moves the enemy down speed dependent on framrate
            enemy.setY(enemy.getY() - 150 * Gdx.graphics.getDeltaTime());
            /*if(enemy.getType().equals(enemylvls.get(1)))
            {
                    int randMovement = MathUtils.random(0,1);
                    if(randMovement == 0)
                    {
                        enemy.setX(enemy.getX() + 75 * Gdx.graphics.getDeltaTime());
                    }
                    if(randMovement == 1)
                    {
                        enemy.setX(enemy.getX() - 75 * Gdx.graphics.getDeltaTime());
                    }
            }*/
            //removes the enemy from the batch when it has reached the bottom of the screen
            if(enemy.getShip().getY() + 64 < 0) iter.remove();
            //plays the explosion sound as well as removes 1 player life and removes the enemy from the screen
            //when the enemy collides with the player
            if(enemy.getShip().overlaps(MyGdxGame.player1.getArea())) {
                long id = hit.play(1.0f);
                hit.setPitch(id, 1);
                hit.setLooping(id, false);
                iter.remove();
                game.setPlayerlives(game.getPlayerlives() -1);
                if(game.getPlayerlives() == 2)
                {
                    hearts = twoheart;
                }
                if(game.getPlayerlives() == 1)
                {
                    hearts = oneheart;
                }
                if(game.getPlayerlives() == 0)
                {
                    gameOver();
                }
            }
        }
        // moves bullets and checks for collision
        // when an enemy is hit we remove health from the enemy and if their health
        // is 0 we remove the enemy from the screen
        // when a player is hit by an enemy bullet they will lose a life
        for(Iterator<Bullet> iter = bullets.iterator(); iter.hasNext();)
        {
            Bullet bullet = iter.next();
            if(bullet.getType().getUser() == 3)
            {
                bullet.y -= 275 * Gdx.graphics.getDeltaTime();
            }
            else{
                bullet.y += 200 * Gdx.graphics.getDeltaTime();
            }
            bullet.setHitbox(new Rectangle(bullet.x, bullet.y, bullet.getHitbox().width, bullet.getHitbox().height));
            for(Iterator<Enemy> iter2 = enemies.iterator(); iter2.hasNext();)
            {
                Enemy enemy = iter2.next();
                if(bullet.getType().getUser() == 3)
                {
                    if(bullet.getHitbox().overlaps(MyGdxGame.player1.getArea()))
                    {
                        long id = hit.play(1.0f);
                        hit.setPitch(id, 1);
                        hit.setLooping(id, false);
                        iter.remove();
                        game.setPlayerlives(game.getPlayerlives() -1);
                        if(game.getPlayerlives() == 2)
                        {
                            hearts = twoheart;
                        }
                        if(game.getPlayerlives() == 1)
                        {
                            hearts = oneheart;
                        }
                        if(game.getPlayerlives() == 0)
                        {
                            gameOver();
                        }
                        break;
                    }
                }
                if(bullet.getType().getUser() == 1 || bullet.getType().getUser() ==2)
                {
                    if(enemy.getShip().overlaps(bullet.getHitbox()))
                    {
                        enemy.setHealth(enemy.getHealth() - 1);
                        long id = hit.play(1.0f);
                        hit.setPitch(id, 1);
                        hit.setLooping(id, false);
                        if(enemy.getHealth() == 0)
                        {
                            score += enemy.getType().getPointValue() / game.getPlayers();
                            iter2.remove();
                            Scoretest = String.valueOf(score);
                        }
                        iter.remove();
                        break;
                    }
                }
            }
            if(bullet.y + 64 > Gdx.graphics.getHeight() || bullet.y <= 0)
            {
                iter.remove();
                break;
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
        for(Bullet bullet: bullets)
        {
            bullet.getType().getBulletImg().dispose();
        }
        for(Enemy enemy: enemies)
        {
            enemy.getType().getShipImg().dispose();
        }
        MyGdxGame.player1.getShipImg().dispose();
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
            enemy.setHealth(5);
            enemy.getType().setSize(150);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize() - 30);
        }
        if(enemy.getType().equals(enemylvls.get(1)))
        {
            enemy.setHealth(1);
            enemy.getType().setSize(70);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize());
        }
        if(enemy.getType().equals(enemylvls.get(2)))
        {
            enemy.setHealth(3);
            enemy.getType().setSize(100);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize());
        }
        enemies.add(enemy);
    }
    public void spawnBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }
    private void gameOver()
    {
        SerialListener.Click = false;
        MyGdxGame.GameOverActive = true;
        game.setPlayerlives(3);
        game.setScreen(new GameOver(game));
    }
}
