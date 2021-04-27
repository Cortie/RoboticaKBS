package com.mygdx.game;


import com.badlogic.gdx.math.Ellipse;

import static com.mygdx.game.MyGdxGame.player1;

public class Bullet
{
    private BulletType type;
    private static Ellipse hitbox;
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
        hitbox = new Ellipse();
        this.x = x;
        this.y = y;
        hitbox.width = 64;
        hitbox.height = 64;
        player1.getBullets().add(this);
        //lastShot = TimeUtils.nanoTime();
    }
    
    public Ellipse getHitbox()
    {
        return hitbox;
    }
}
