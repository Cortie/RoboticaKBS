package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Enemy
{
    private float x;
    private float y;
    private int health;
    private final EnemyType type;
    private final Rectangle ship;
    private long lastShot;
    private long lastmovement;
    private long movementspeed = 50;
    private int moved;
    private boolean shot = false;
    
    public void setLastmovement(long lastmovement)
    {
        this.lastmovement = lastmovement;
    }
    
    public Enemy(EnemyType type)
    {
        this.type = type;
        this.ship = new Rectangle(x, y, type.getSize(), type.getSize());
    }
    public EnemyType getType()
    {
        return type;
    }
    public Rectangle getShip()
    {
        return ship;
    }
    
    public float getX()
    {
        return x;
    }
    
    public void setX(float x)
    {
        this.x = x;
        this.ship.x = x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public long getLastmovement()
    {
        return lastmovement;
    }
    
    public long getMovementspeed()
    {
        return movementspeed;
    }
    
    public void setY(float y)
    {
        this.y = y;
        this.ship.y = y;
    }
    public void setHealth(int health)
    {
        this.health = health;
    }
    public int getHealth()
    {
        return health;
    }
    
    
    public long getLastShot()
    {
        return lastShot;
    }
    
    public void setLastShot(long lastShot)
    {
        this.lastShot = lastShot;
    }
    
    public int isMoved()
    {
        return moved;
    }
    
    public void setMoved(int number)
    {
        this.moved = number;
    }
    
    public boolean isShot()
    {
        return shot;
    }
    
    public void setShot(boolean shot)
    {
        this.shot = shot;
    }
}
