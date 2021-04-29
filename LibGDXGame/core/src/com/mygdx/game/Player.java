package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player
{
    public float x;
    public float y;
    private Texture shipImg;
    private Sprite ship;
    private Rectangle area;
    private long shotSpeed = 600000000;
    private long lastShot;
    private BulletType type;
    private static Array<Bullet> bullets;
    
    public Player(FileHandle img, BulletType bullet, float x)
    {
        this.setType(bullet);
        bullets = new Array<Bullet>();
        this.shipImg = new Texture(img);
        this.ship = new Sprite(shipImg);
        this.x = x;
        this.y = 10;
        this.area = new Rectangle();
        this.area.width = 64;
        this.area.height = 64;
        this.area.x = this.x;
        this.area.y = this.y;
    }
    public void setX(float x)
    {
        this.x = x;
        this.area.x = x;
    }
    
    public void setY(float y)
    {
        this.y = y;
        this.area.y = y;
    }
    public Texture getShipImg()
    {
        return shipImg;
    }
    public Sprite getShip()
    {
        return ship;
    }
    public Rectangle getArea()
    {
        return area;
    }
    public void moveLeft()
    {
        if(!(this.x < 0))
        {
            this.setX(x - 300 * Gdx.graphics.getDeltaTime());
        }
    }

    public void moveRight()
    {
        if(!(this.x > Gameplay.camera.viewportWidth - 64))
        {
            this.setX(x + 300 * Gdx.graphics.getDeltaTime());
        }
    }
    public Array<Bullet> getBullets()
    {
        return bullets;
    }
    public long getShotSpeed()
    {
        return shotSpeed;
    }
    
    public BulletType getType()
    {
        return type;
    }
    public void setType(BulletType type)
    {
        this.type = type;
    }
}
