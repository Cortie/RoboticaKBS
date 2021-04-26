package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.*;

public class Player extends Rectangle
{
    public float x;
    public float y;
    private Texture shipImg;
    private Sprite ship;
    
    public Player(FileHandle img)
    {
        this.shipImg = new Texture(img);
        this.ship = new Sprite(shipImg);
        this.x = Gdx.graphics.getWidth()/ 2 - 64 / 2;
        this.y = 10;
        this.width = 64;
        this.height = 64;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    public Texture getShipImg()
    {
        return shipImg;
    }
    public Sprite getShip()
    {
        return ship;
    }
    
}
