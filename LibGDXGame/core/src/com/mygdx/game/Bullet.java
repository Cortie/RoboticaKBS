package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.MyGdxGame.player1;

public class Bullet
{
    private BulletType type;
    private static Rectangle hitbox;
    public float x;
    public float y;
    
    public Bullet(BulletType type)
    {
        this.type = type;
        
    }
    public BulletType getType(){
        return type;
    }
    public void spawnBullet(float x, float y)
    {
        hitbox = new Rectangle();
        this.x = x;
        this.y = y;
        hitbox.width = 20;
        hitbox.height = 60;
        player1.getBullets().add(this);
        //lastShot = TimeUtils.nanoTime();
    }
    
    public Rectangle getHitbox()
    {
        return hitbox;
    }
}
