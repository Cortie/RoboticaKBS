package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Bullet
{
    private BulletType type;
    private Rectangle hitbox;
    private int user;
    public float x;
    public float y;
    
    public Bullet(int user, BulletType type, Rectangle hitbox, float x, float y)
    {
        this.x = x;
        this.y = y;
        this.user = user;
        this.type = type;
        setHitbox(hitbox);
    }
    public BulletType getType(){
        return type;
    }
    
    public Rectangle getHitbox()
    {
        return hitbox;
    }
    
    public void setHitbox(Rectangle hitbox)
    {
        this.hitbox = hitbox;
    }
}
