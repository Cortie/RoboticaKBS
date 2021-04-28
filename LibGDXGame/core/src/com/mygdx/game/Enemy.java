package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Enemy
{
    private float x;
    private float y;
    private EnemyType type;
    private Rectangle ship;
    
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
    }
    
    public float getY()
    {
        return y;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
}
