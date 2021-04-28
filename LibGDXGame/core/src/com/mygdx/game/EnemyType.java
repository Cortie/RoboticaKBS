package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnemyType
{
    private Texture shipImg;
    private Sprite ship;
    
    public EnemyType(Texture shipImg)
    {
        this.shipImg = shipImg;
        this.ship = new Sprite(shipImg);
    }
}
