package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp
{
    private Texture texture;
    private Rectangle area;
    public float x;
    public float y;
    private int type;
    
    public Texture getTexture()
    {
        return texture;
    }
    
    public PowerUp(int rand, float x, float y)
    {
        this.x = x;
        this. y = y;
        this.area = new Rectangle(x, y, 75, 75);
        String text = "powerup" + rand;
        this.texture = new Texture(Gdx.files.internal(text));
        this.type = rand;
    }
    
    public int getType()
    {
        return type;
    }
    
    public Rectangle getArea()
    {
        return area;
    }
    
    public void setArea(Rectangle area)
    {
        this.area = area;
    }
    
}
