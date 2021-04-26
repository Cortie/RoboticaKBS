package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;


public class Player
{
    public float x;
    public float y;
    private Texture shipImg;
    private Sprite ship;
    private Rectangle area;
    
    public Player(FileHandle img)
    {
        this.shipImg = new Texture(img);
        this.ship = new Sprite(shipImg);
        this.x = Gdx.graphics.getWidth()/ 2 - 64 / 2;
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
        if(!(this.x > MyGdxGame.camera.viewportWidth - 64))
        {
            this.setX(x + 300 * Gdx.graphics.getDeltaTime());
        }
    }
}
