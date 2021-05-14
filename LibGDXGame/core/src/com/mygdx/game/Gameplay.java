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
    private Sound explode;
    private Sound pickup;
    private Array<EnemyType> enemylvls;
    private Array<Enemy> enemies;
    private long lastEnemySpawn;
    private Array<Bullet> bullets;
    private final MyGdxGame game;
    private Texture heart;
    private Sound Lazer;
    private Sound hit;
    private Texture forcefieldImg;
    
    
    public Gameplay(MyGdxGame game)
    {
        this.game = game;
    }
    
    @Override
    public void show()
    {
        heart = new Texture(Gdx.files.internal("1live.png"));
        forcefieldImg = new Texture(Gdx.files.internal("ForceField.png"));
        //local variables used during gameplay
        //hit sound effect for any collision between bullets and ships
        //2 bullet types to be use in the creation of the different enemy types
        //the 3 arrays of to be rendered objects
        explode = Gdx.audio.newSound(Gdx.files.internal("game_explosion.wav"));
        pickup = Gdx.audio.newSound(Gdx.files.internal("game_powerup.wav"));
        Lazer = Gdx.audio.newSound(Gdx.files.internal("game_lazer.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("game_hit.wav"));
        BulletType standardBullet = new BulletType(new Texture(Gdx.files.internal("4.png")), 3, 20, 60);
        BulletType bigBullet = new BulletType(new Texture(Gdx.files.internal("1.png")), 3, 30, 90);
        bullets = new Array<>();
        enemylvls = new Array<>();
        enemies = new Array<>();
        //due to movement being connected to the same function
        // as the menu screen selection player position must be put in a spawn point here
        MyGdxGame.player1.x = MyGdxGame.camera.viewportWidth /10 + MyGdxGame.player1.getArea().getWidth();
        if(game.getPlayercount() == 2)
        {
            MyGdxGame.player2.x = MyGdxGame.camera.viewportWidth /3 * 2 + MyGdxGame.player2.getArea().getWidth();
        }
        //this loop creates an array of different enemy types
        //that is later used for enemy creation and comparisons
        for(int i = 0; i < 5; i++)
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
                    break;
                case 3:
                    pointvalue = 200;
                    shotSpeed = 1200;
                    btype = bigBullet;
                    break;
                case 4:
                    pointvalue = 300;
                    shotSpeed = 1500;
                    btype = bigBullet;
                    break;
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
        if(MyGdxGame.player1.isForcefield())
        {
            game.batch.draw(forcefieldImg, MyGdxGame.player1.x, MyGdxGame.player1.y + 75);
        }
        if( game.getPlayercount() == 2)
        {
            game.batch.draw(MyGdxGame.player2.getShipImg(), MyGdxGame.player2.x, MyGdxGame.player2.y);
            if(MyGdxGame.player2.isForcefield())
            {
                game.batch.draw(forcefieldImg, MyGdxGame.player2.x - 20, MyGdxGame.player2.y + 75);
            }
        }
        for(Enemy enemy: enemies) {
            game.batch.draw(enemy.getType().getShipImg(), enemy.getShip().getX(), enemy.getShip().getY());
        }
        for(Bullet bullet: bullets)
        {
            game.batch.draw(bullet.getType().getBulletImg(), bullet.x, bullet.y);
        }
        game.getFont().draw(game.batch, game.getScoretext(),30,game.camera.viewportHeight-50);
        for(int i = 0; i < game.getPlayerlives(); i++)
        {
            game.batch.draw(heart, 20 + i*50, game.camera.viewportHeight-150);
        }
        for(PowerUp power: game.getPowers())
        {
            game.batch.draw(power.getTexture(), power.x, power.y);
        }
        game.batch.end();
        // Spawns random enemy types after a certain amount of time has passed
        // after a certain score is reached one of the 2 minibosses is spawned
        if(TimeUtils.millis() - lastEnemySpawn > 0)
        {
            float position;
            int rand = MathUtils.random(0,2);
            float screentop = MyGdxGame.camera.viewportHeight;
            if(rand == 0)
            {
                position = MathUtils.random(0, MyGdxGame.camera.viewportWidth - (enemylvls.get(rand).getSize()+150));
                spawnEnemy(position + 150, screentop + 150, enemylvls.get(rand));
                spawnEnemy(position - 150, screentop + 150, enemylvls.get(rand));
                lastEnemySpawn = TimeUtils.millis() + 5000;
            }
            if(rand == 1)
            {
                position = MathUtils.random(0, MyGdxGame.camera.viewportWidth - (enemylvls.get(rand).getSize() + 90));
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
                lastEnemySpawn = TimeUtils.millis() + 3500;
            }
            if(rand == 2)
            {
                position = MathUtils.random(0,MyGdxGame.camera.viewportWidth - 400);
                spawnEnemy(position , screentop, enemylvls.get(rand));
                spawnEnemy(position + 150, screentop + 150, enemylvls.get(rand));
                spawnEnemy(position - 150, screentop + 150, enemylvls.get(rand));
                lastEnemySpawn = TimeUtils.millis() + 5000;
            }
            //this will spawn one of the two minibosses and set the spawntimer to
            // 30 seconds to give the player time to fight the miniboss
            if(game.getBossScore()>= 2000)
            {
                int miniboss = MathUtils.random(3,4);
                position = MyGdxGame.camera.viewportWidth/2 - 250;
                spawnEnemy(position, screentop, enemylvls.get(miniboss));
                game.setBossScore(0);
                lastEnemySpawn = TimeUtils.millis() + 30000;
            }
        }
        // Spawns a bullet out of the player dependent on player shot speed
        if(TimeUtils.nanoTime() - MyGdxGame.player1.getLastShot() > MyGdxGame.player1.getShotSpeed())
        {
            Rectangle rec = new Rectangle(MyGdxGame.player1.x + (MyGdxGame.player1.getArea().getWidth()/2 + (MyGdxGame.player1.getType().getWidth()/2)), MyGdxGame.player1.y + 64, MyGdxGame.player1.getType().getWidth(), MyGdxGame.player1.getType().getHeight());
            Bullet bullet = new Bullet(1, MyGdxGame.player1.getType(), rec, MyGdxGame.player1.x + (MyGdxGame.player1.getArea().getWidth()/2), MyGdxGame.player1.y + 64);
            spawnBullet(bullet);
            if(MyGdxGame.player1.isTripleshot())
            {
                rec =  new Rectangle(rec.x + (MyGdxGame.player1.getType().getWidth() *2), MyGdxGame.player1.y + 64, MyGdxGame.player1.getType().getWidth(), MyGdxGame.player1.getType().getHeight());
                bullet = new Bullet(1, MyGdxGame.player1.getType(),rec, bullet.x + (MyGdxGame.player1.getType().getWidth() *2), MyGdxGame.player1.y + 64);
                spawnBullet(bullet);
                rec =  new Rectangle(rec.x - (MyGdxGame.player1.getType().getWidth() *4), MyGdxGame.player1.y + 64, MyGdxGame.player1.getType().getWidth(), MyGdxGame.player1.getType().getHeight());
                bullet = new Bullet(1, MyGdxGame.player1.getType(),rec, bullet.x - (MyGdxGame.player1.getType().getWidth() *4), MyGdxGame.player1.y + 64);
                spawnBullet(bullet);
            }
            MyGdxGame.playSound(Lazer);
            MyGdxGame.player1.setLastShot(TimeUtils.nanoTime());
        }
        if(game.getPlayercount() == 2)
        {
            if(TimeUtils.nanoTime() - MyGdxGame.player2.getLastShot() > MyGdxGame.player2.getShotSpeed())
            {
                Rectangle rec =  new Rectangle(MyGdxGame.player2.x + 26, MyGdxGame.player2.y + 64, MyGdxGame.player2.getType().getWidth(), MyGdxGame.player2.getType().getHeight());
                Bullet bullet = new Bullet(1, MyGdxGame.player2.getType(),rec, MyGdxGame.player2.x + 26, MyGdxGame.player2.y + 64);
                spawnBullet(bullet);
                if(MyGdxGame.player2.isTripleshot())
                {
                    rec =  new Rectangle(MyGdxGame.player2.x + 76, MyGdxGame.player2.y + 64, MyGdxGame.player2.getType().getWidth(), MyGdxGame.player2.getType().getHeight());
                    bullet = new Bullet(1, MyGdxGame.player2.getType(),rec, MyGdxGame.player2.x + 76, MyGdxGame.player2.y + 64);
                    spawnBullet(bullet);
                    rec =  new Rectangle(MyGdxGame.player2.x - 76, MyGdxGame.player2.y + 64, MyGdxGame.player2.getType().getWidth(), MyGdxGame.player2.getType().getHeight());
                    bullet = new Bullet(1, MyGdxGame.player2.getType(),rec, MyGdxGame.player2.x - 76, MyGdxGame.player2.y + 64);
                    spawnBullet(bullet);
                }
                MyGdxGame.playSound(Lazer);
                MyGdxGame.player2.setLastShot(TimeUtils.nanoTime());
            }
        }
        for(Iterator<PowerUp> iter = game.getPowers().iterator(); iter.hasNext();){
            PowerUp power = iter.next();
            
            if(power.getArea().overlaps(game.player1.getArea()))
            {
                long id = pickup.play(1.0f);
                pickup.setPitch(id, 1);
                pickup.setLooping(id, false);
                game.setScore(game.getScore() + 100);
                if(power.getType() == 2)
                {
                    game.setPlayerlives(game.getPlayerlives() +1);
                }
                if(power.getType() == 5)
                {
                    MyGdxGame.player1.setType(new BulletType(new Texture(Gdx.files.internal("3.png")), 1, 60, 125));
                }
                if(power.getType() == 1)
                {
                    MyGdxGame.player1.setForcefield(true);
                }
                if(power.getType() == 4)
                {
                    MyGdxGame.player1.setShotSpeed(MyGdxGame.player1.getShotSpeed() - 25000000);
                }
                if(power.getType() == 3)
                {
                    MyGdxGame.player1.setTripleshot(true);
                }
                iter.remove();
                break;
            }
            if(game.getPlayercount() == 2)
            {
                long id = pickup.play(1.0f);
                pickup.setPitch(id, 1);
                pickup.setLooping(id, false);
                if( power.getArea().overlaps(game.player2.getArea()))
                {
                    if(power.getType() == 2)
                    {
                        game.setPlayerlives(game.getPlayerlives() +1);
                    }
                    if(power.getType() == 5)
                    {
                        MyGdxGame.player2.setType(new BulletType(new Texture(Gdx.files.internal("2.png")), 2, 60, 125));
                    }
                    if(power.getType() == 1)
                    {
                        MyGdxGame.player2.setForcefield(true);
                    }
                    if(power.getType() == 4)
                    {
                        MyGdxGame.player2.setShotSpeed(MyGdxGame.player2.getShotSpeed() - 25000000);
                    }
                    if(power.getType() == 3)
                    {
                        MyGdxGame.player2.setTripleshot(true);
                    }
                    iter.remove();
                    break;
                }
            }
            power.setY(power.y - 250 * Gdx.graphics.getDeltaTime());
            if(power.y + 32 < 0){
                iter.remove();
                break;
            }
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
                if(enemy.getType().equals(enemylvls.get(0)))
                {
                    Rectangle bullethitbox = new Rectangle(enemy.getX(), enemy.getY()-25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                    Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX(), enemy.getY()-25);
                    spawnBullet(bullet);
                    bullethitbox =  new Rectangle(enemy.getX() + enemy.getShip().getWidth(), enemy.getY() -25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                    bullet = new Bullet(3, enemy.getType().getBtype(),bullethitbox, enemy.getX() + enemy.getShip().getWidth(), enemy.getY() -25);
                    spawnBullet(bullet);
                }
                if(enemy.getType().equals(enemylvls.get(1)))
                {
                    Rectangle bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2), enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                    Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + (enemy.getShip().getWidth()/2), enemy.getY() - 25);
                    spawnBullet(bullet);
                }
                if(enemy.getType().equals(enemylvls.get(2)))
                {
                    Rectangle bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2), enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                    Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox,enemy.getX() + (enemy.getShip().getWidth()/2), enemy.getY() - 25);
                    spawnBullet(bullet);
                }
                if(enemy.getType().equals(enemylvls.get(3)))
                {
                    if(enemy.isShot())
                    {
                        Rectangle bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth() / 2), enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + (enemy.getShip().getWidth() / 2), enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth() / 2) + 75, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + (enemy.getShip().getWidth() / 2) + 75, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth() / 2) - 75, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + (enemy.getShip().getWidth() / 2) - 75, enemy.getY() - 25);
                        spawnBullet(bullet);
                        enemy.setShot(false);
                    }
                    else
                    {
                        Rectangle bullethitbox = new Rectangle(enemy.getX(), enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX(), enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getX()  + 50, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + 50, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getX() + 100, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + 100, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getShip().getWidth() + enemy.getX(), enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getShip().getWidth() + enemy.getX(), enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getShip().getWidth()+ enemy.getX() - 50, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getShip().getWidth()+ enemy.getX() - 50, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox = new Rectangle(enemy.getShip().getWidth()+ enemy.getX() - 100, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getShip().getWidth()+ enemy.getX() - 100, enemy.getY() - 25);
                        spawnBullet(bullet);
                        enemy.setShot(true);
                    }
                }
                if(enemy.getType().equals(enemylvls.get(4)))
                {
                    if(enemy.getShotNumber() == 3)
                    {
                        spawnEnemy(enemy.getX() -50, enemy.getY() - 25, enemylvls.get(2));
                        spawnEnemy(enemy.getX() + enemy.getShip().getWidth() - 100, enemy.getY() - 25, enemylvls.get(2));
                        enemy.setShotNumber(0);
                    }
                    else
                    {
                        Rectangle bullethitbox =  new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2) + 25, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        Bullet bullet = new Bullet(3, enemy.getType().getBtype(),bullethitbox, enemy.getX() + (enemy.getShip().getWidth()/2) + 25, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox =  new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2) + 75, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(),bullethitbox, enemy.getX() + (enemy.getShip().getWidth()/2) + 75, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox =  new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2) - 125, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(),bullethitbox, enemy.getX() + (enemy.getShip().getWidth()/2) - 125, enemy.getY() - 25);
                        spawnBullet(bullet);
                        bullethitbox =  new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2) - 75, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
                        bullet = new Bullet(3, enemy.getType().getBtype(),bullethitbox, enemy.getX() + (enemy.getShip().getWidth()/2) - 75, enemy.getY() - 25);
                        spawnBullet(bullet);
                        enemy.setShotNumber(enemy.getShotNumber() + 1);
                    }
                }
                enemy.setLastShot(TimeUtils.millis());
            }
            //moves the enemy down speed dependent on framerate
            if(enemy.getType().equals(enemylvls.get(0)))
            {
                enemy.setY(enemy.getY() - 100 * Gdx.graphics.getDeltaTime());
            }
            if(enemy.getType().equals(enemylvls.get(1)))
            {
                enemy.setY(enemy.getY() - 175 * Gdx.graphics.getDeltaTime());
            }
            if(enemy.getType().equals(enemylvls.get(2)))
            {
                enemy.setY(enemy.getY() - 150 * Gdx.graphics.getDeltaTime());
            }
            if(enemy.getType().equals(enemylvls.get(3)))
            {
                if(enemy.getY() > MyGdxGame.camera.viewportHeight - 250)
                {
                    enemy.setY(enemy.getY() - 50 * Gdx.graphics.getDeltaTime());
                }
            }
            if(enemy.getType().equals(enemylvls.get(4)))
            {
                if(enemy.getY() > MyGdxGame.camera.viewportHeight - 250)
                {
                    enemy.setY(enemy.getY() - 50 * Gdx.graphics.getDeltaTime());
                }
            }
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
            if(enemy.getShip().getY() + 64 < 0) {
                iter.remove();
                break;
            }
            //plays the explosion sound as well as removes 1 player life and removes the enemy from the screen
            //when the enemy collides with the player
            if(MyGdxGame.player1.isForcefield())
            {
                if(enemy.getShip().overlaps(MyGdxGame.player1.getShieldBox()))
                {
                    long id = hit.play(1.0f);
                    hit.setPitch(id, 1);
                    hit.setLooping(id, false);
                    if(game.getPlayerlives() == 0)
                    {
                        gameOver();
                    }
                    MyGdxGame.player1.setForcefield(false);
                    iter.remove();
                    break;
                }
            }
            
            if(enemy.getShip().overlaps(MyGdxGame.player1.getArea())) {
                long id = hit.play(1.0f);
                hit.setPitch(id, 1);
                hit.setLooping(id, false);
                game.setPlayerlives(game.getPlayerlives() -1);
                if(game.getPlayerlives() == 0)
                {
                    gameOver();
                }
                iter.remove();
                break;
            }
            if(game.getPlayercount() == 2){
                if(MyGdxGame.player2.isForcefield())
                {
                    if(enemy.getShip().overlaps(MyGdxGame.player2.getShieldBox()))
                    {
                        long id = hit.play(1.0f);
                        hit.setPitch(id, 1);
                        hit.setLooping(id, false);
                        if(game.getPlayerlives() == 0)
                        {
                            gameOver();
                        }
                        MyGdxGame.player2.setForcefield(false);
                        iter.remove();
                        break;
                    }
                }
                if(enemy.getShip().overlaps(MyGdxGame.player2.getArea()))
                {
                    long id = hit.play(1.0f);
                    hit.setPitch(id, 1);
                    hit.setLooping(id, false);
                    game.setPlayerlives(game.getPlayerlives() -1);
                    if(game.getPlayerlives() == 0)
                    {
                        gameOver();
                    }
                    iter.remove();
                    break;
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
                bullet.y -= 300 * Gdx.graphics.getDeltaTime();
            }
            else{
                bullet.y += 375 * Gdx.graphics.getDeltaTime();
            }
            bullet.setHitbox(new Rectangle(bullet.x, bullet.y, bullet.getHitbox().width, bullet.getHitbox().height));
            for(Iterator<Enemy> iter2 = enemies.iterator(); iter2.hasNext();)
            {
                Enemy enemy = iter2.next();
                if(bullet.getType().getUser() == 3)
                {
                    if(MyGdxGame.player1.isForcefield())
                    {
                        if(bullet.getHitbox().overlaps(MyGdxGame.player1.getShieldBox()))
                        {
                            long id = explode.play(1.0f);
                            explode.setPitch(id, 1);
                            explode.setLooping(id, false);
                            if(game.getPlayerlives() == 0)
                            {
                                gameOver();
                            }
                            MyGdxGame.player1.setForcefield(false);
                            iter.remove();
                            break;
                        }
                    }
                    if(bullet.getHitbox().overlaps(MyGdxGame.player1.getArea()))
                    {
                        long id = explode.play(1.0f);
                        explode.setPitch(id, 1);
                        explode.setLooping(id, false);
                        game.setPlayerlives(game.getPlayerlives() -1);
                        if(game.getPlayerlives() == 0)
                        {
                            gameOver();
                        }
                        iter.remove();
                        break;
                    }
                    if(game.getPlayercount() == 2)
                    {
                        if(MyGdxGame.player2.isForcefield())
                        {
                            if(bullet.getHitbox().overlaps(MyGdxGame.player2.getShieldBox()))
                            {
                                long id = explode.play(1.0f);
                                explode.setPitch(id, 1);
                                explode.setLooping(id, false);
                                if(game.getPlayerlives() == 0)
                                {
                                    gameOver();
                                }
                                MyGdxGame.player2.setForcefield(false);
                                iter.remove();
                                break;
                            }
                        }
                        if(MyGdxGame.player1.isForcefield())
                        {
                            if(enemy.getShip().overlaps(MyGdxGame.player1.getShieldBox()))
                            {
                                long id = explode.play(1.0f);
                                explode.setPitch(id, 1);
                                explode.setLooping(id, false);
                                if(game.getPlayerlives() == 0)
                                {
                                    gameOver();
                                }
                                MyGdxGame.player1.setForcefield(false);
                                iter.remove();
                                break;
                            }
                        }
                        if(bullet.getHitbox().overlaps(MyGdxGame.player2.getArea()))
                        {
                            long id = explode.play(1.0f);
                            explode.setPitch(id, 1);
                            explode.setLooping(id, false);
                            game.setPlayerlives(game.getPlayerlives() -1);
                            if(game.getPlayerlives() == 0)
                            {
                                gameOver();
                            }
                            iter.remove();
                            break;
                        }
                    }
                }
                if(bullet.getType().getUser() == 1 || bullet.getType().getUser() ==2)
                {
                    if(enemy.getShip().overlaps(bullet.getHitbox()))
                    {
                        enemy.setHealth(enemy.getHealth() - 1);
                        long id = explode.play(1.0f);
                        explode.setPitch(id, 1);
                        explode.setLooping(id, false);
                        if(enemy.getHealth() == 0)
                        {
                            game.setScore(game.getScore()+enemy.getType().getPointValue());
                            game.setScoretext(String.valueOf(game.getScore()));
                            if(enemy.getType().equals(enemylvls.get(3)) || enemy.getType().equals(enemylvls.get(4)))
                            {
                                spawnPowerUp(2, enemy.getX() + (enemy.getShip().getWidth()/2), enemy.getY());
                            }
                            int rand = MathUtils.random(1,20);
                            {
                                if(rand == 1)
                                {
                                    int rand2 = MathUtils.random(1,5);
                                    {
                                        spawnPowerUp(rand2, enemy.getX(), enemy.getY());
                                    }
                                }
                            }
                            iter2.remove();
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
        explode.dispose();
        Lazer.dispose();
        pickup.dispose();
        MyGdxGame.player2.getShipImg().dispose();
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
        if(enemy.getType().equals(enemylvls.get(3)))
        {
            enemy.setHealth(15);
            enemy.getType().setSize(450);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize() - 100);
        }
        if(enemy.getType().equals(enemylvls.get(4)))
        {
            enemy.setHealth(15);
            enemy.getType().setSize(450);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize() - 125);
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
    private void spawnPowerUp(int rand, float x, float y)
    {
        PowerUp power = new PowerUp(rand, x, y);
        game.getPowers().add(power);
    }
}
