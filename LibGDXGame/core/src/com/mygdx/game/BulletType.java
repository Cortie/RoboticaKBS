package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BulletType
{
    private Texture bulletImg;
    private Sprite bullet;
    
    public BulletType(Texture img)
    {
        this.bulletImg = img;
        this.bullet = new Sprite(img);
    }
    public Texture getBulletImg()
    {
        return bulletImg;
    }
}
