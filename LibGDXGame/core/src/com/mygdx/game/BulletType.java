package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class BulletType
{
    private Texture bulletImg;
    
    public BulletType(Texture img)
    {
        this.bulletImg = img;
    }
    public Texture getBulletImg()
    {
        return bulletImg;
    }
}
