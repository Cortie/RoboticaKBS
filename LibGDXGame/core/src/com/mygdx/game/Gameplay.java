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
    private int bossScore = 1500;
    
    
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
        createEnemyTypes();
    }
    
    @Override
    public void render(float delta)
    {
        //draws everything onto the screen
        draw();
        // Spawns random enemy types after a certain amount of time has passed
        // after a certain score is reached one of the 2 minibosses is spawned
        if(TimeUtils.millis() - lastEnemySpawn > 0)
        {
            spawnRandomEnemy();
        }
        // Spawns a bullet out of the player dependent on player shot speed
        if(TimeUtils.nanoTime() - MyGdxGame.player1.getLastShot() > MyGdxGame.player1.getShotSpeed())
        {
            shoot(MyGdxGame.player1);
        }
        //checks if 2 players is active then spawns bullet for player2 depending on their shot speed
        if(game.getPlayercount() == 2)
        {
            if(TimeUtils.nanoTime() - MyGdxGame.player2.getLastShot() > MyGdxGame.player2.getShotSpeed())
            {
                shoot(MyGdxGame.player2);
            }
        }
        //allows powerups to move through the screen aswell as for players to pick them up
        iteratePowerUps();
        // move the enemies, remove any that are beneath the bottom edge of
        // the screen or that hit the player. In the latter case we play back
        // a sound effect as well, and remove a life/shield from the player.
        iterateEnemies();
        // moves bullets and checks for collision
        // when an enemy is hit we remove health from the enemy and if their health
        // is 0 we remove the enemy from the screen
        // when a player is hit by an enemy bullet they will lose a life
        iterateBullets();
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
        for(PowerUp power: game.getPowers())
        {
            power.getTexture().dispose();
        }
        MyGdxGame.player1.getShipImg().dispose();
        explode.dispose();
        Lazer.dispose();
        hit.dispose();
        pickup.dispose();
        MyGdxGame.player2.getShipImg().dispose();
        game.batch.dispose();
    }
    
    private void pickUp(Player player, PowerUp power)
    {
        game.playSound(pickup);
        //extra life
        if(power.getType() == 2)
        {
            game.setPlayerlives(game.getPlayerlives() +1);
        }
        //bigger bullets
        if(power.getType() == 5)
        {
            player.setType(new BulletType(new Texture(Gdx.files.internal("3.png")), 1, 60, 125));
        }
        //forcefield
        if(power.getType() == 1)
        {
            player.setForcefield(true);
        }
        //faster shots
        if(power.getType() == 4)
        {
            player.setShotSpeed(player.getShotSpeed() - 25000000);
        }
        //triple shot
        if(power.getType() == 3)
        {
            player.setTripleshot(true);
        }
    }
    private void iterateBullets()
    {
        for(Iterator<Bullet> iter = bullets.iterator(); iter.hasNext();)
        {
            Bullet bullet = iter.next();
            //makes the bullets drop or rise depending on if it's an enemy bullet or player bullet
            if(bullet.getType().getUser() == 3)
            {
                bullet.y -= 300 * Gdx.graphics.getDeltaTime();
            }
            else{
                bullet.y += 375 * Gdx.graphics.getDeltaTime();
            }
            //resets the bullet hitbox to the new location
            bullet.setHitbox(new Rectangle(bullet.x, bullet.y, bullet.getHitbox().width, bullet.getHitbox().height));
            //checks if the bullet is shot form an enemy
            //then checks if it overlaps with a player or a player forcefield
            if(bullet.getType().getUser() == 3)
            {
                //player 1 forcefield check
                if(MyGdxGame.player1.isForcefield())
                {
                    if(bullet.getHitbox().overlaps(MyGdxGame.player1.getShieldBox()))
                    {
                        game.playSound(explode);
                        MyGdxGame.player1.setForcefield(false);
                        iter.remove();
                        break;
                    }
                }
                //player 1 ship check
                if(bullet.getHitbox().overlaps(MyGdxGame.player1.getArea()))
                {
                    game.playSound(explode);
                    game.setPlayerlives(game.getPlayerlives() -1);
                    if(game.getPlayerlives() == 0)
                    {
                        gameOver();
                    }
                    iter.remove();
                    break;
                }
                //check if player 2 is active
                if(game.getPlayercount() == 2)
                {
                    //player 2 forcefield check
                    if(MyGdxGame.player2.isForcefield())
                    {
                        if(bullet.getHitbox().overlaps(MyGdxGame.player2.getShieldBox()))
                        {
                            game.playSound(explode);
                            MyGdxGame.player2.setForcefield(false);
                            iter.remove();
                            break;
                        }
                    }
                    //player 2 ship check
                    if(bullet.getHitbox().overlaps(MyGdxGame.player2.getArea()))
                    {
                        game.playSound(explode);
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
            //checks if the bullet is from one of the players
            if(bullet.getType().getUser() == 1 || bullet.getType().getUser() ==2)
            {
                //iterates through all enemies and checks if one of the bullets hits their hitbox
                for(Iterator<Enemy> iter2 = enemies.iterator(); iter2.hasNext();)
                {
                Enemy enemy = iter2.next();
                    if(enemy.getShip().overlaps(bullet.getHitbox()))
                    {
                        enemy.setHealth(enemy.getHealth() - 1);
                        game.playSound(explode);
                        //kills the enemy and gives a chance to spawn a power up as well as gives the players score dependent on enemy type
                        if(enemy.getHealth() == 0)
                        {
                            if(enemy.getType().equals(enemylvls.get(4) ) || enemy.getType().equals(enemylvls.get(3)))
                            {
                                spawnPowerUp(2, enemy.getX() - 100, enemy.getY());
                                int rand = MathUtils.random(1,5);
                                {
                                    spawnPowerUp(rand, enemy.getX() + 100, enemy.getY() + 100);
                                }
                            }
                            game.setScore(game.getScore()+enemy.getType().getPointValue());
                            game.setScoretext(String.valueOf(game.getScore()));
                            int rand = MathUtils.random(1,10);
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
            if(bullet.y + bullet.getHitbox().getHeight() >= Gdx.graphics.getHeight() || bullet.y + bullet.getHitbox().getHeight() <= 0)
            {
                iter.remove();
                break;
            }
        }
    }
    private void spawnRandomEnemy()
    {
        float position;
        int rand = MathUtils.random(0,2);
        float screentop = MyGdxGame.camera.viewportHeight;
        //spawns the big enemy type
        if(rand == 0)
        {
            position = MathUtils.random(0, MyGdxGame.camera.viewportWidth - (enemylvls.get(rand).getSize()+150));
            spawnEnemy(position + 150, screentop + 150, enemylvls.get(rand));
            spawnEnemy(position - 150, screentop + 150, enemylvls.get(rand));
            lastEnemySpawn = TimeUtils.millis() + 5000;
        }
        //spawns normal enemy type
        if(rand == 1)
        {
            position = MathUtils.random(0, MyGdxGame.camera.viewportWidth - (enemylvls.get(rand).getSize() + 90));
            for(int i = 0; i < 5; i++)
            {
                if(i == 0)
                {
                    spawnEnemy(position, screentop + 50, enemylvls.get(rand));
                }
                if(i == 1)
                {
                    spawnEnemy(position + 50, screentop + 50, enemylvls.get(rand));
                    spawnEnemy(position - 50, screentop + 50, enemylvls.get(rand));
                }
                if(i == 2)
                {
                    spawnEnemy(position + 100, screentop + 50, enemylvls.get(rand));
                    spawnEnemy(position - 100, screentop + 50, enemylvls.get(rand));
                }
                if(i == 3)
                {
                    spawnEnemy(position + 150, screentop + 50, enemylvls.get(rand));
                    spawnEnemy(position - 150, screentop + 50, enemylvls.get(rand));
                }
                screentop += 75;
            }
            lastEnemySpawn = TimeUtils.millis() + 3500;
        }
        //spawns expert enemy type
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
        if(game.getScore() >= bossScore)
        {
            int miniboss = MathUtils.random(3,4);
            position = MyGdxGame.camera.viewportWidth/2 - 250;
            spawnEnemy(position, screentop, enemylvls.get(miniboss));
            lastEnemySpawn = TimeUtils.millis() + 30000;
            bossScore += 1500;
        }
    }
    private void spawnEnemy(float position, float height, EnemyType type)
    {
        //sets position of the enemy texture as well as the enemy hitbox
        Enemy enemy = new Enemy(type);
        enemy.setX(position);
        enemy.setY(height);
        enemy.getShip().setX(position);
        enemy.getShip().setY(height);
        //sets health, size of texture and size of the hitbox depending on the enemy type
        //big enemy
        if(enemy.getType().equals(enemylvls.get(0)))
        {
            enemy.setHealth(5);
            enemy.getType().setSize(150);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize() - 30);
        }
        //normal enemy
        if(enemy.getType().equals(enemylvls.get(1)))
        {
            enemy.setHealth(1);
            enemy.getType().setSize(70);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize());
        }
        //expert enemy
        if(enemy.getType().equals(enemylvls.get(2)))
        {
            enemy.setHealth(3);
            enemy.getType().setSize(100);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize());
        }
        //miniboss type 1 (double shot pattern)
        if(enemy.getType().equals(enemylvls.get(3)))
        {
            enemy.setHealth(15);
            enemy.getType().setSize(450);
            enemy.getShip().setSize(enemy.getType().getSize(), enemy.getType().getSize() - 100);
        }
        //miniboss type 2 (shot + spawn pattern)
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
        MyGdxGame.player1.setTripleshot(false);
        MyGdxGame.player1.setForcefield(false);
        MyGdxGame.player1.setShotSpeed(600000000);
        MyGdxGame.player1.setType(new BulletType(new Texture(Gdx.files.internal("6.png")), 1, 20, 60));
        MyGdxGame.player2.setTripleshot(false);
        MyGdxGame.player2.setForcefield(false);
        MyGdxGame.player2.setShotSpeed(600000000);
        MyGdxGame.player2.setType(new BulletType(new Texture(Gdx.files.internal("5.png")), 1, 20, 60));
        MyGdxGame.GameOverActive = true;
        game.setPlayerlives(3);
        game.setScreen(new GameOver(game));
    }
    private void spawnPowerUp(int rand, float x, float y)
    {
        PowerUp power = new PowerUp(rand, x, y);
        game.getPowers().add(power);
    }
    private void draw()
    {
        // begin a new batch and draw all game components (player, bullets, enemies)
        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        //draws player 1, als checks if player 1 has a forcefield and draws that aswell if that is the case
        game.batch.draw(MyGdxGame.player1.getShipImg(), MyGdxGame.player1.x, MyGdxGame.player1.y);
        if(MyGdxGame.player1.isForcefield())
        {
            game.batch.draw(forcefieldImg, MyGdxGame.player1.x, MyGdxGame.player1.y + 75);
        }
        //checks if the 2 player setting is active and if so draws player 2 and checks for forcefield
        if( game.getPlayercount() == 2)
        {
            game.batch.draw(MyGdxGame.player2.getShipImg(), MyGdxGame.player2.x, MyGdxGame.player2.y);
            if(MyGdxGame.player2.isForcefield())
            {
                game.batch.draw(forcefieldImg, MyGdxGame.player2.x - 20, MyGdxGame.player2.y + 75);
            }
        }
        //draws all the enemies spawned
        for(Enemy enemy: enemies) {
            game.batch.draw(enemy.getType().getShipImg(), enemy.getShip().getX(), enemy.getShip().getY());
        }
        //draws all bullets spawned
        for(Bullet bullet: bullets)
        {
            game.batch.draw(bullet.getType().getBulletImg(), bullet.x, bullet.y);
        }
        //draws the score in the top left of the screen
        game.getFont().draw(game.batch, game.getScoretext(),30,game.camera.viewportHeight-50);
        //for each life the player/players currently have draws a heart right below the score
        for(int i = 0; i < game.getPlayerlives(); i++)
        {
            game.batch.draw(heart, 20 + i*50, game.camera.viewportHeight-150);
        }
        //draws any spawned powerups
        for(PowerUp power: game.getPowers())
        {
            game.batch.draw(power.getTexture(), power.x, power.y);
        }
        game.batch.end();
    }
    
    private void createEnemyTypes()
    {
        BulletType standardBullet = new BulletType(new Texture(Gdx.files.internal("4.png")), 3, 20, 60);
        BulletType bigBullet = new BulletType(new Texture(Gdx.files.internal("1.png")), 3, 30, 90);
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
                //big enemy worth double points shoots 2 big bullets spawns with 2
                case 0:
                    pointvalue = 50;
                    shotSpeed = 2500;
                    btype = bigBullet;
                    break;
                //normal enemy shoots 1 normal bullet and spawns in a v formation
                case 1:
                    shotSpeed = 2000;
                    pointvalue = 25;
                    btype = standardBullet;
                    break;
                //expert enemy worth triple points spawns in a lesser pact v formation and shoots 1 big bullet more often than others
                case 2:
                    pointvalue = 75;
                    shotSpeed = 2000;
                    btype = bigBullet;
                    break;
                case 3:
                    //miniboss type 1 shoots 2 kinds of bullet patterns alternatively, easier and therefore worth 200
                    pointvalue = 200;
                    shotSpeed = 1500;
                    btype = bigBullet;
                    break;
                //miniboss type 2 shoots 1 bullet pattern alternatively with a enemy spawn of 2 expert enemies on his sides a little harder therefore worth 300 points
                case 4:
                    pointvalue = 300;
                    shotSpeed = 1500;
                    btype = bigBullet;
                    break;
            }
            //adds the enemy type to the array for comparisions in other functions later on
            enemylvls.add(new EnemyType(btype, new Texture(Gdx.files.internal("Spaceship_0" + nummer + "_RED.png")), shotSpeed, pointvalue));
        }
    }
    private void shoot(Player player)
    {
        Rectangle rec = new Rectangle(player.x + (player.getArea().getWidth()/2 + (player.getType().getWidth()/2)), player.y + 64, player.getType().getWidth(), player.getType().getHeight());
        Bullet bullet = new Bullet(1, player.getType(), rec, player.x + (player.getArea().getWidth()/2), player.y + 64);
        spawnBullet(bullet);
        //spawns 2 extra bullets if the player has picked up triple shot upgrade
        if(player.isTripleshot())
        {
            rec =  new Rectangle(rec.x + (player.getType().getWidth() *2), player.y + 64, player.getType().getWidth(), player.getType().getHeight());
            bullet = new Bullet(1, player.getType(),rec, bullet.x + (player.getType().getWidth() *2), player.y + 64);
            spawnBullet(bullet);
            rec =  new Rectangle(rec.x - (player.getType().getWidth() *4), player.y + 64, player.getType().getWidth(), player.getType().getHeight());
            bullet = new Bullet(1, player.getType(),rec, bullet.x - (player.getType().getWidth() *4), player.y + 64);
            spawnBullet(bullet);
        }
        game.playSound(Lazer);
        player.setLastShot(TimeUtils.nanoTime());
    }
    private void enemyShot(Enemy enemy)
    {
        if(enemy.getType().equals(enemylvls.get(0)))
        {
            Rectangle bullethitbox = new Rectangle(enemy.getX() + 10, enemy.getY()-50, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
            Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + 10, enemy.getY()-50);
            spawnBullet(bullet);
            bullethitbox =  new Rectangle(enemy.getX() + enemy.getShip().getWidth() - 50, enemy.getY() -50, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
            bullet = new Bullet(3, enemy.getType().getBtype(),bullethitbox, enemy.getX() + enemy.getShip().getWidth()- 50, enemy.getY() -50);
            spawnBullet(bullet);
        }
        if(enemy.getType().equals(enemylvls.get(1)))
        {
            Rectangle bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2) - 8, enemy.getY() - 25, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
            Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox, enemy.getX() + (enemy.getShip().getWidth()/2 - 8), enemy.getY() - 25);
            spawnBullet(bullet);
        }
        if(enemy.getType().equals(enemylvls.get(2)))
        {
            Rectangle bullethitbox = new Rectangle(enemy.getX() + (enemy.getShip().getWidth()/2)+ 13, enemy.getY() - 50, enemy.getType().getBtype().getWidth(), enemy.getType().getBtype().getHeight());
            Bullet bullet = new Bullet(3, enemy.getType().getBtype(), bullethitbox,enemy.getX() + (enemy.getShip().getWidth()/2) - 13, enemy.getY() - 50);
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
    private void move(Enemy enemy)
    {
        //moves the enemy down at a certain speed dependent on type, minibosses stop at a certain point and must be defeated
        //big enemy
        if(enemy.getType().equals(enemylvls.get(0)))
        {
            enemy.setY(enemy.getY() - 100 * Gdx.graphics.getDeltaTime());
        }
        //normal enemy
        if(enemy.getType().equals(enemylvls.get(1)))
        {
            enemy.setY(enemy.getY() - 175 * Gdx.graphics.getDeltaTime());
        }
        //expert enemy
        if(enemy.getType().equals(enemylvls.get(2)))
        {
            enemy.setY(enemy.getY() - 150 * Gdx.graphics.getDeltaTime());
        }
        //miniboss type 1 (double shot pattern)
        if(enemy.getType().equals(enemylvls.get(3)))
        {
            if(enemy.getY() > MyGdxGame.camera.viewportHeight - 250)
            {
                enemy.setY(enemy.getY() - 50 * Gdx.graphics.getDeltaTime());
            }
        }
        //miniboss type 2(shot + spawn pattern)
        if(enemy.getType().equals(enemylvls.get(4)))
        {
            if(enemy.getY() > MyGdxGame.camera.viewportHeight - 250)
            {
                enemy.setY(enemy.getY() - 50 * Gdx.graphics.getDeltaTime());
            }
        }
    }
    private void iterateEnemies()
    {
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();
            //checks if the lastshot of the enemy has surpassed shotspeed
            if(TimeUtils.millis() - enemy.getLastShot() > enemy.getType().getShotSpeed())
            {
                //creates a bullet depending on enemy type and then adds the bullet
                //to the bullet array to be drawn in the batch
                enemyShot(enemy);
            }
            //moves the enemy down speed dependent on framerate and enemy type
            move(enemy);
            //removes the enemy from the batch when it has reached the bottom of the screen
            if(enemy.getShip().getY() + 64 < 0) {
                iter.remove();
                break;
            }
            if(MyGdxGame.player1.isForcefield())
            {
                if(enemy.getShip().overlaps(MyGdxGame.player1.getShieldBox()))
                {
                    game.playSound(hit);
                    MyGdxGame.player1.setForcefield(false);
                    iter.remove();
                    break;
                }
            }
            //plays the explosion sound as well as removes 1 player life and removes the enemy from the screen
            //when the enemy collides with the player
            if(enemy.getShip().overlaps(MyGdxGame.player1.getArea())) {
                game.playSound(hit);
                game.setPlayerlives(game.getPlayerlives() -1);
                if(game.getPlayerlives() == 0)
                {
                    gameOver();
                }
                iter.remove();
                break;
            }
            //plays the explosion sound as well as removes 1 player life and removes the enemy from the screen
            //when the enemy collides with the player2 checks if player 2 exists before hand
            if(game.getPlayercount() == 2){
                if(MyGdxGame.player2.isForcefield())
                {
                    if(enemy.getShip().overlaps(MyGdxGame.player2.getShieldBox()))
                    {
                        game.playSound(hit);
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
                    game.playSound(hit);
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
    }
    private void iteratePowerUps()
    {
        for(Iterator<PowerUp> iter = game.getPowers().iterator(); iter.hasNext();){
            PowerUp power = iter.next();
            //checks if powerup overlaps with player 1 then gives the power up to that player and removes the pickup from the game
            if(power.getArea().overlaps(MyGdxGame.player1.getArea()))
            {
                pickUp(MyGdxGame.player1, power);
                iter.remove();
                break;
            }
            //checks if there is a 2nd player and then if that player overlaps with the power up does the same as with player 1
            if(game.getPlayercount() == 2)
            {
                if( power.getArea().overlaps(MyGdxGame.player2.getArea()))
                {
                    pickUp(MyGdxGame.player2, power);
                    iter.remove();
                    break;
                }
            }
            //moves the power up down at a fixed speed and removes it from the game when it reaches the bottom without being picked up
            power.setY(power.y - 250 * Gdx.graphics.getDeltaTime());
            if(power.y + 32 < 0){
                iter.remove();
                break;
            }
        }
    }
}