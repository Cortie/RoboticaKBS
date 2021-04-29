package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnemyType
{
    private Texture shipImg;
    private Sprite ship;
    private float size;
    private long shotSpeed;
    
    public EnemyType(Texture shipImg, long shotSpeed)
    {
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
}
