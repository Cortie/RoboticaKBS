package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BulletType
{
    private final Texture bulletImg;
    private Sprite bullet;
    private int width;
    private int height;
    private final int user;
    
    public BulletType(Texture img, int user, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.user = user;
        this.bulletImg = img;
        this.bullet = new Sprite(img);
    }
    public Texture getBulletImg()
    {
        return bulletImg;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    public int getUser()
    {
        return user;
    }

}
