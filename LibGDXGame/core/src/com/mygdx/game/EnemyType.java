package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnemyType
{
    private final Texture shipImg;
    private Sprite ship;
    private float size;
    private final long shotSpeed;
    private BulletType btype;
    private final int pointValue;
    
    public EnemyType(BulletType btype, Texture shipImg, long shotSpeed, int pointValue)
    {
        this.pointValue = pointValue;
        setBtype(btype);
        this.shotSpeed = shotSpeed;
        this.shipImg = shipImg;
        this.ship = new Sprite(shipImg);
    }
    public float getSize()
    {
        return size;
    }
    public Texture getShipImg()
    {
        return shipImg;
    }
    public void setSize(float size)
    {
        this.size = size;
    }
    public long getShotSpeed()
    {
        return shotSpeed;
    }
    
    public BulletType getBtype()
    {
        return btype;
    }
    
    public int getPointValue()
    {
        return pointValue;
    }
    
    public void setBtype(BulletType btype)
    {
        this.btype = btype;
    }
}
